package com.travel3d.vietlutravel.controller.Admin;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travel3d.vietlutravel.model.Booking;
import com.travel3d.vietlutravel.model.Customer;
import com.travel3d.vietlutravel.model.TourGuide;
import com.travel3d.vietlutravel.service.BookingService;
import com.travel3d.vietlutravel.service.EmailService;
import com.travel3d.vietlutravel.service.TourGuideService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminBookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TourGuideService tourGuideService;

    @Autowired
    private EmailService emailService;

    /**
     * Kiểm tra xem người dùng có phải admin không
     */
    private boolean isAdmin(HttpSession session) {
        Customer user = (Customer) session.getAttribute("user");
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    /**
     * Danh sách tất cả booking (trang chính quản lý)
     */
    @GetMapping("/bookings")
    public String listBookings(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        model.addAttribute("guides", tourGuideService.getAllGuides());
        model.addAttribute("pageTitle", "Quản lý Booking");

        return "admin/bookings";  // templates/admin/bookings.html
    }

    /**
     * Xem chi tiết một booking
     */
    @GetMapping("/bookings/{id}")
    public String viewBookingDetail(
            @PathVariable("id") int id,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Booking booking = bookingService.getBookingById(id);
        if (booking == null) {
            model.addAttribute("errorMessage", "Không tìm thấy booking với ID: " + id);
            return "redirect:/admin/bookings";
        }

        model.addAttribute("booking", booking);
        model.addAttribute("pageTitle", "Chi tiết Booking #" + id);

        return "admin/booking-detail";  // templates/admin/booking-detail.html
    }

    /**
     * Cập nhật trạng thái booking (Pending / Confirmed / Cancelled)
     */
    @PostMapping("/bookings/{id}/update-status")
    public String updateBookingStatus(
            @PathVariable("id") int id,
            @RequestParam("status") String status,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {
            // Kiểm tra trạng thái hợp lệ
            if (!List.of("Pending", "Confirmed", "Cancelled").contains(status)) {
                throw new IllegalArgumentException("Trạng thái không hợp lệ: " + status);
            }

            Booking booking = bookingService.getBookingById(id);
            if (booking == null) {
                throw new IllegalArgumentException("Booking không tồn tại");
            }

            String oldStatus = booking.getStatus();
            bookingService.updateBookingStatus(id, status);

            // NEW: Nếu chuyển sang Confirmed và có HDV, gửi hợp đồng
            if ("Confirmed".equals(status) && !"Confirmed".equals(oldStatus) && booking.getTourGuide() != null) {
                try {
                    emailService.sendContract(booking);
                    System.out.println("Đã gửi hợp đồng cho booking #" + id);
                } catch (Exception e) {
                    System.err.println("Lỗi gửi hợp đồng: " + e.getMessage());
                    // Không throw, vẫn cho phép update status
                }
            }

            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi cập nhật trạng thái: " + e.getMessage());
        }

        return "redirect:/admin/bookings";
    }

    /**
     * Xóa booking (có confirm JS ở frontend)
     */
    @PostMapping("/bookings/{id}/delete")
    public String deleteBooking(
            @PathVariable("id") int id,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {
            bookingService.deleteBooking(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa booking thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa booking: " + e.getMessage());
        }

        return "redirect:/admin/bookings";
    }

    /**
     * Phân công Hướng dẫn viên và Đổi ngày đi (Gom khách)
     */
    @PostMapping("/bookings/assign-guide")
    public String assignGuideAndGroup(
            @RequestParam(value = "bookingIds", required = false) List<Integer> bookingIds,
            @RequestParam(value = "guideId", required = false) Integer guideId,
            @RequestParam(value = "travelDate", required = false) String travelDateStr,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        if (bookingIds == null || bookingIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn ít nhất một Booking để phân công!");
            return "redirect:/admin/bookings";
        }

        try {
            LocalDate newDate = null;
            if (travelDateStr != null && !travelDateStr.trim().isEmpty()) {
                newDate = LocalDate.parse(travelDateStr);
            }

            TourGuide guide = null;
            if (guideId != null && guideId > 0) {
                guide = tourGuideService.getGuideById(guideId);
            }

            Integer commonTourId = null;

            for (Integer bId : bookingIds) {
                Booking b = bookingService.getBookingById(bId);
                if (b == null || "Cancelled".equalsIgnoreCase(b.getStatus())) {
                    continue;
                }

                if (commonTourId == null) {
                    commonTourId = b.getTourID();
                } else if (!commonTourId.equals(b.getTourID())) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Không thể gom: Các Booking được chọn phải thuộc về cùng 1 Tour!");
                    return "redirect:/admin/bookings";
                }

                LocalDate targetDate = newDate != null ? newDate : b.getTravelDate();
                if (targetDate == null && b.getTour() != null) {
                    targetDate = b.getTour().getStartDate();
                }

                if (guide != null && targetDate != null) {
                    int duration = (b.getTour() != null && b.getTour().getDurationDays() != null) ? b.getTour().getDurationDays() : 1;
                    boolean isAvail = tourGuideService.isGuideAvailable(
                            guideId, targetDate, targetDate.plusDays(duration - 1), bookingIds);

                    if (!isAvail) {
                        redirectAttributes.addFlashAttribute("errorMessage", 
                                "Hướng dẫn viên " + guide.getFullName() + " bị trùng lịch! Việc phân công tự động dừng lại ở Booking #" + bId);
                        return "redirect:/admin/bookings";
                    }
                }

                if (newDate != null) {
                    b.setTravelDate(newDate);
                }
                b.setTourGuide(guide);

                // Nếu đã phân công HDV, chuyển trạng thái thành Confirmed và gửi hợp đồng
                if (guide != null && !"Cancelled".equalsIgnoreCase(b.getStatus())) {
                    b.setStatus("Confirmed");
                }

                bookingService.saveBooking(b);

                if (guide != null && "Confirmed".equalsIgnoreCase(b.getStatus())) {
                    try {
                        emailService.sendContract(b);
                    } catch (Exception e) {
                        // Gửi thất bại không dừng cả quá trình
                        System.err.println("Lỗi gửi hợp đồng sau phân công HDV: " + e.getMessage());
                    }
                }

                // Gửi thông tin tour cho HDV khi được phân công
                if (guide != null) {
                    try {
                        emailService.sendTourInfoToHDV(b);
                    } catch (Exception e) {
                        // Gửi thất bại không dừng cả quá trình
                        System.err.println("Lỗi gửi thông tin tour cho HDV: " + e.getMessage());
                    }
                }
            }

            redirectAttributes.addFlashAttribute("successMessage", 
                    "Cập nhật thành công cho " + bookingIds.size() + " Booking được chọn!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi phân công: " + e.getMessage());
        }
        return "redirect:/admin/bookings";
    }
}