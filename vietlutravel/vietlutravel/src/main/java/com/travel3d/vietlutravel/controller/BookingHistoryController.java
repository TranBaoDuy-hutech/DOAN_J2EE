package com.travel3d.vietlutravel.controller;

import com.travel3d.vietlutravel.model.Booking;
import com.travel3d.vietlutravel.model.Customer;
import com.travel3d.vietlutravel.service.BookingService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BookingHistoryController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookingService bookingService;

    // ================= LỊCH SỬ BOOKING =================
    @GetMapping("/booking-history")
    public String bookingHistory(HttpSession session, Model model) {

        Customer customer = (Customer) session.getAttribute("user");

        if (customer == null) {
            return "redirect:/login";
        }

        List<Booking> bookings = entityManager.createQuery(
                        "FROM Booking b WHERE b.customerID = :cid", Booking.class)
                .setParameter("cid", customer.getCustomerID())
                .getResultList();

        model.addAttribute("bookings", bookings);
        model.addAttribute("today", LocalDate.now());

        return "booking-history";
    }

    // ================= HỦY TOUR =================
    @PostMapping("/booking/cancel/{id}")
    public String cancelBooking(
            @PathVariable int id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Customer customer = (Customer) session.getAttribute("user");
        if (customer == null) {
            return "redirect:/login";
        }
        boolean success = bookingService.cancelBooking(id, customer.getCustomerID());

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Hủy tour thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Không thể hủy: booking không tồn tại, không thuộc bạn, đã xác nhận hoặc còn dưới 7 ngày.");
        }

        return "redirect:/booking-history";
    }
}