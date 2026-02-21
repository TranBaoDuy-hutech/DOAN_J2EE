package com.travel3d.vietlutravel.controller;  // sửa package cho đúng (không cần .Admin riêng)

import com.travel3d.vietlutravel.model.Booking;
import com.travel3d.vietlutravel.model.Customer;
import com.travel3d.vietlutravel.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminBookingController {

    @Autowired
    private BookingService bookingService;

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

            bookingService.updateBookingStatus(id, status);
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
}