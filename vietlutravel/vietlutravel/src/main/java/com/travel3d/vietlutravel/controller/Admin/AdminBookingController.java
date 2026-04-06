package com.travel3d.vietlutravel.controller.Admin;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    // ─────────────────────────────────────────────────────────────────────────
    // HELPER: kiểm tra admin
    // ─────────────────────────────────────────────────────────────────────────

    private boolean isAdmin(HttpSession session) {
        Customer user = (Customer) session.getAttribute("user");
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // HELPER: lấy tất cả booking cùng nhóm với booking đã cho.
    //
    // "Cùng nhóm" = cùng tourID + cùng tourGuideId + cùng travelDate.
    // Điều kiện này chỉ áp dụng khi booking ĐÃ được gom (có đủ 3 trường trên).
    // Booking hiện tại luôn nằm trong kết quả trả về.
    // ─────────────────────────────────────────────────────────────────────────

    private List<Booking> getSameGroupBookings(Booking target) {
        // Nếu chưa đủ điều kiện nhóm thì trả về đúng 1 phần tử (chính nó)
        if (target.getTourGuide() == null || target.getTravelDate() == null) {
            return List.of(target);
        }

        Integer targetTourId = target.getTourID();
        Integer targetGuideId = target.getTourGuide().getGuideId();
        LocalDate targetDate = target.getTravelDate();

        return bookingService.getAllBookings().stream()
                .filter(b -> b.getTourGuide() != null
                        && b.getTravelDate() != null
                        && targetTourId != null && targetTourId.equals(b.getTourID())
                        && targetGuideId != null && targetGuideId.equals(b.getTourGuide().getGuideId())
                        && targetDate.equals(b.getTravelDate()))
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /admin/bookings — Danh sách tất cả booking
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/bookings")
    public String listBookings(Model model, HttpSession session) {
        if (!isAdmin(session))
            return "redirect:/login";

        List<Booking> bookings = bookingService.getAllBookings();

        double depositRevenue = 0;
        double totalRevenue = 0;
        for (Booking b : bookings) {
            if ("Paid 70% Deposit".equalsIgnoreCase(b.getStatus())) {
                depositRevenue += b.getTotalPrice() * 0.7;
            } else if ("Paid 100%".equalsIgnoreCase(b.getStatus())) {
                totalRevenue += b.getTotalPrice();
            }
        }

        model.addAttribute("depositRevenue", depositRevenue);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("bookings", bookings);
        model.addAttribute("guides", tourGuideService.getAllGuides());
        model.addAttribute("pageTitle", "Quản lý Booking");

        return "admin/bookings";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /admin/bookings/{id} — Chi tiết một booking
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/bookings/{id}")
    public String viewBookingDetail(
            @PathVariable("id") int id,
            Model model,
            HttpSession session) {

        if (!isAdmin(session))
            return "redirect:/login";

        Booking booking = bookingService.getBookingById(id);
        if (booking == null)
            return "redirect:/admin/bookings";

        model.addAttribute("booking", booking);
        model.addAttribute("pageTitle", "Chi tiết Booking #" + id);

        return "admin/booking-detail";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /admin/bookings/{id}/update-status
    //
    // ★ KEY FIX: Khi đổi trạng thái 1 booking, tất cả booking cùng nhóm
    // (cùng tour + cùng HDV + cùng ngày khởi hành) cũng đổi theo.
    //
    // Quy tắc đồng bộ nhóm:
    // - "Cancelled" → CHỈ huỷ booking hiện tại (không kéo cả nhóm).
    // Lý do: mỗi khách có quyền huỷ độc lập.
    // - Mọi trạng thái khác → đồng bộ toàn nhóm, ngoại trừ:
    // • Booking đã "Paid 100%" trong nhóm → không thay đổi.
    // • Booking đang "Cancelled" trong nhóm → không kéo theo.
    // ─────────────────────────────────────────────────────────────────────────

    @PostMapping("/bookings/{id}/update-status")
    public String updateBookingStatus(
            @PathVariable("id") int id,
            @RequestParam("status") String status,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (!isAdmin(session))
            return "redirect:/login";

        try {
            List<String> validStatuses = List.of(
                    "Pending", "Confirmed", "Cancelled",
                    "Paid 70% Deposit", "Paid 100%", "Payment Failed");
            if (!validStatuses.contains(status)) {
                throw new IllegalArgumentException("Trạng thái không hợp lệ: " + status);
            }

            Booking target = bookingService.getBookingById(id);
            if (target == null) {
                throw new IllegalArgumentException("Booking không tồn tại");
            }

            // Booking Paid 100% không được chỉnh sửa
            if ("Paid 100%".equalsIgnoreCase(target.getStatus())
                    && !status.equalsIgnoreCase(target.getStatus())) {
                throw new IllegalArgumentException(
                        "Tour đã hoàn thành (Paid 100%) và không được chỉnh sửa!");
            }

            // ── Xác định danh sách booking sẽ cập nhật ──────────────────────
            // Cancelled → chỉ 1 booking hiện tại
            // Các trạng thái khác → toàn bộ nhóm
            boolean cancelOnly = "Cancelled".equalsIgnoreCase(status);
            List<Booking> bookingsToUpdate = cancelOnly
                    ? List.of(target)
                    : getSameGroupBookings(target);

            int updatedCount = 0;

            for (Booking b : bookingsToUpdate) {
                // Không bao giờ chạm vào booking Paid 100%
                if ("Paid 100%".equalsIgnoreCase(b.getStatus()))
                    continue;

                // Booking đã Cancelled trong nhóm → không kéo theo
                // (trừ khi chính nó là booking đang được chỉnh - cancelOnly case)
                if ("Cancelled".equalsIgnoreCase(b.getStatus())
                        && b.getBookingID() != id)
                    continue;

                String oldStatus = b.getStatus();
                bookingService.updateBookingStatus(b.getBookingID(), status);
                updatedCount++;

                // Gửi hợp đồng khi chuyển sang Confirmed và đã có HDV
                if ("Confirmed".equals(status)
                        && !"Confirmed".equals(oldStatus)
                        && b.getTourGuide() != null) {
                    try {
                        emailService.sendContract(b);
                    } catch (Exception e) {
                        System.err.println("Lỗi gửi hợp đồng booking #"
                                + b.getBookingID() + ": " + e.getMessage());
                    }
                }
            }

            String msg = updatedCount > 1
                    ? "Đã cập nhật trạng thái \"" + status + "\" cho "
                            + updatedCount + " booking cùng nhóm!"
                    : "Cập nhật trạng thái thành công!";
            redirectAttributes.addFlashAttribute("successMessage", msg);

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Lỗi cập nhật trạng thái: " + e.getMessage());
        }

        return "redirect:/admin/bookings";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /admin/bookings/{id}/delete — Xóa booking
    // ─────────────────────────────────────────────────────────────────────────

    @PostMapping("/bookings/{id}/delete")
    public String deleteBooking(
            @PathVariable("id") int id,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (!isAdmin(session))
            return "redirect:/login";

        try {
            Booking booking = bookingService.getBookingById(id);
            if (booking != null && "Paid 100%".equalsIgnoreCase(booking.getStatus())) {
                throw new IllegalArgumentException(
                        "Không thể xóa: Tour đã hoàn thành (Paid 100%).");
            }
            bookingService.deleteBooking(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa booking thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Lỗi khi xóa booking: " + e.getMessage());
        }

        return "redirect:/admin/bookings";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /admin/bookings/bulk-confirm — Confirm hàng loạt
    // ─────────────────────────────────────────────────────────────────────────

    @PostMapping("/bookings/bulk-confirm")
    public String bulkConfirm(
            @RequestParam(value = "bookingIds", required = false) List<Integer> bookingIds,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (!isAdmin(session))
            return "redirect:/login";

        if (bookingIds == null || bookingIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Chưa chọn booking nào!");
            return "redirect:/admin/bookings";
        }

        int count = 0;
        for (Integer bId : bookingIds) {
            Booking b = bookingService.getBookingById(bId);
            if (b == null
                    || "Paid 100%".equalsIgnoreCase(b.getStatus())
                    || "Cancelled".equalsIgnoreCase(b.getStatus()))
                continue;
            bookingService.updateBookingStatus(bId, "Confirmed");
            count++;
        }

        redirectAttributes.addFlashAttribute("successMessage",
                "Đã Confirm " + count + " booking!");
        return "redirect:/admin/bookings";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /admin/bookings/bulk-cancel — Huỷ hàng loạt
    // ─────────────────────────────────────────────────────────────────────────

    @PostMapping("/bookings/bulk-cancel")
    public String bulkCancel(
            @RequestParam(value = "bookingIds", required = false) List<Integer> bookingIds,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (!isAdmin(session))
            return "redirect:/login";

        if (bookingIds == null || bookingIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Chưa chọn booking nào!");
            return "redirect:/admin/bookings";
        }

        int count = 0;
        for (Integer bId : bookingIds) {
            Booking b = bookingService.getBookingById(bId);
            if (b == null || "Paid 100%".equalsIgnoreCase(b.getStatus()))
                continue;
            bookingService.updateBookingStatus(bId, "Cancelled");
            count++;
        }

        redirectAttributes.addFlashAttribute("successMessage",
                "Đã huỷ " + count + " booking!");
        return "redirect:/admin/bookings";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /admin/bookings/assign-guide — Phân công HDV & gom khách
    //
    // Logic tách biệt:
    // • Phân công riêng lẻ (1 bookingId): bỏ qua validate gom tour,
    // cho phép booking >= 5 người lớn.
    // • Gom tour (nhiều bookingId): validate đủ điều kiện:
    // - Từng booking phải < 5 người lớn.
    // - Tất cả phải cùng 1 tour.
    // - Ngày khởi hành lệch nhau tối đa 10 ngày.
    // ─────────────────────────────────────────────────────────────────────────

    @PostMapping("/bookings/assign-guide")
    public String assignGuideAndGroup(
            @RequestParam(value = "bookingIds", required = false) List<Integer> bookingIds,
            @RequestParam(value = "guideId", required = false) Integer guideId,
            @RequestParam(value = "travelDate", required = false) String travelDateStr,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (!isAdmin(session))
            return "redirect:/login";

        if (bookingIds == null || bookingIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Vui lòng chọn ít nhất một Booking để phân công!");
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

            boolean isSingleAssign = bookingIds.size() == 1;

            // ── Validate gom tour (chỉ khi > 1 booking) ────────────────────
            if (!isSingleAssign) {
                Integer commonTourId = null;
                LocalDate minAllowedDate = null;
                LocalDate maxAllowedDate = null;

                for (Integer bId : bookingIds) {
                    Booking b = bookingService.getBookingById(bId);
                    if (b == null
                            || "Cancelled".equalsIgnoreCase(b.getStatus())
                            || "Paid 100%".equalsIgnoreCase(b.getStatus()))
                        continue;

                    if (b.getNumberOfPeople() >= 5) {
                        redirectAttributes.addFlashAttribute("errorMessage",
                                "Không thể gom: Booking #" + bId + " có từ 5 người lớn trở lên. "
                                        + "Vui lòng phân công HDV riêng lẻ từ nút Chi tiết.");
                        return "redirect:/admin/bookings";
                    }

                    if (commonTourId == null) {
                        commonTourId = b.getTourID();
                    } else if (!commonTourId.equals(b.getTourID())) {
                        redirectAttributes.addFlashAttribute("errorMessage",
                                "Không thể gom: Các Booking được chọn phải thuộc về cùng 1 Tour!");
                        return "redirect:/admin/bookings";
                    }

                    if (b.getTravelDate() != null) {
                        LocalDate tMin = b.getTravelDate().minusDays(10);
                        LocalDate tMax = b.getTravelDate().plusDays(10);
                        if (minAllowedDate == null) {
                            minAllowedDate = tMin;
                            maxAllowedDate = tMax;
                        } else {
                            if (tMin.isAfter(minAllowedDate))
                                minAllowedDate = tMin;
                            if (tMax.isBefore(maxAllowedDate))
                                maxAllowedDate = tMax;
                        }
                    }
                }

                if (minAllowedDate != null && maxAllowedDate != null
                        && minAllowedDate.isAfter(maxAllowedDate)) {
                    redirectAttributes.addFlashAttribute("errorMessage",
                            "Các Booking có ngày khởi hành lệch nhau quá 10 ngày, không thể gom chung!");
                    return "redirect:/admin/bookings";
                }

                if (newDate != null && minAllowedDate != null && maxAllowedDate != null) {
                    if (newDate.isBefore(minAllowedDate) || newDate.isAfter(maxAllowedDate)) {
                        redirectAttributes.addFlashAttribute("errorMessage",
                                "Ngày khởi hành mới phải nằm trong khoảng lệch 10 ngày "
                                        + "so với tất cả các Booking được chọn!");
                        return "redirect:/admin/bookings";
                    }
                }
            }

            // ── Cập nhật từng booking ────────────────────────────────────────
            for (Integer bId : bookingIds) {
                Booking b = bookingService.getBookingById(bId);
                if (b == null
                        || "Cancelled".equalsIgnoreCase(b.getStatus())
                        || "Paid 100%".equalsIgnoreCase(b.getStatus()))
                    continue;

                LocalDate targetDate = newDate != null ? newDate : b.getTravelDate();
                if (targetDate == null && b.getTour() != null) {
                    targetDate = b.getTour().getStartDate();
                }

                // Kiểm tra lịch HDV
                if (guide != null && targetDate != null) {
                    int duration = (b.getTour() != null && b.getTour().getDurationDays() != null)
                            ? b.getTour().getDurationDays()
                            : 1;
                    boolean isAvail = tourGuideService.isGuideAvailable(
                            guideId, targetDate, targetDate.plusDays(duration - 1), bookingIds);

                    if (!isAvail) {
                        redirectAttributes.addFlashAttribute("errorMessage",
                                "Hướng dẫn viên " + guide.getFullName()
                                        + " bị trùng lịch! Dừng lại ở Booking #" + bId);
                        return "redirect:/admin/bookings";
                    }
                }

                if (newDate != null)
                    b.setTravelDate(newDate);
                b.setTourGuide(guide);

                if (guide != null && !"Cancelled".equalsIgnoreCase(b.getStatus())) {
                    b.setStatus("Confirmed");
                }

                bookingService.saveBooking(b);

                // Gửi hợp đồng cho khách
                if (guide != null && "Confirmed".equalsIgnoreCase(b.getStatus())) {
                    try {
                        emailService.sendContract(b);
                    } catch (Exception e) {
                        System.err.println("Lỗi gửi hợp đồng booking #"
                                + b.getBookingID() + ": " + e.getMessage());
                    }
                }

                // Gửi thông tin tour cho HDV
                if (guide != null) {
                    try {
                        emailService.sendTourInfoToHDV(b);
                    } catch (Exception e) {
                        System.err.println("Lỗi gửi thông tin tour cho HDV: " + e.getMessage());
                    }
                }
            }

            redirectAttributes.addFlashAttribute("successMessage",
                    "Cập nhật thành công cho " + bookingIds.size() + " Booking được chọn!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Lỗi khi phân công: " + e.getMessage());
        }

        return "redirect:/admin/bookings";
    }
}