package com.travel3d.vietlutravel.controller.Admin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/admin")
    public String dashboard(Model model) {

        Long tourCount = entityManager
                .createQuery("SELECT COUNT(t) FROM Tours t", Long.class)
                .getSingleResult();

        Long bookingCount = entityManager
                .createQuery("SELECT COUNT(b) FROM Booking b", Long.class)
                .getSingleResult();

        Long customerCount = entityManager
                .createQuery("SELECT COUNT(c) FROM Customer c", Long.class)
                .getSingleResult();

        model.addAttribute("tourCount", tourCount);
        model.addAttribute("bookingCount", bookingCount);
        model.addAttribute("customerCount", customerCount);

        return "admin/dashboard";
    }
}
