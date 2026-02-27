package com.travel3d.vietlutravel.controller;

import com.travel3d.vietlutravel.model.Tours;
import com.travel3d.vietlutravel.model.Booking;
import com.travel3d.vietlutravel.model.Contact;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {

    @PersistenceContext
    private EntityManager entityManager;

    // Trang chủ
    @GetMapping("/")
    public String home(Model model) {
        List<Tours> tours = entityManager
                .createQuery("FROM Tours", Tours.class)
                .getResultList();
        model.addAttribute("tours", tours);
        return "index";
    }

    // Chi tiết tour
    @GetMapping("/tour/{id}")
    public String tourDetail(@PathVariable int id, Model model) {
        Tours tour = entityManager.find(Tours.class, id);
        model.addAttribute("tour", tour);
        return "tour-detail";
    }

    // Form đặt tour
    @GetMapping("/book/{id}")
    public String bookingForm(@PathVariable int id, Model model) {
        Tours tour = entityManager.find(Tours.class, id);
        model.addAttribute("tour", tour);
        model.addAttribute("booking", new Booking());
        return "booking-form";
    }

    // Trang giới thiệu
    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    // Trang liên hệ
    @GetMapping("/contact")
    public String contactPage(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    // Trang điều khoản
    @GetMapping("/terms")
    public String termsPage() {
        return "terms";
    }

    // Trang câu hỏi
    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }

    // Trang chính sách
    @GetMapping("/privacy")
    public String privacy() {
        return "privacy";
    }

    // Lưu liên hệ
    @PostMapping("/contact")
    @Transactional
    public String saveContact(@ModelAttribute Contact contact) {
        contact.setCreatedAt(LocalDateTime.now());
        entityManager.persist(contact);
        return "redirect:/contact?success";
    }
}
