package com.travel3d.vietlutravel.controller.Admin;

import com.travel3d.vietlutravel.model.Contact;
import com.travel3d.vietlutravel.model.Customer;
import com.travel3d.vietlutravel.service.ContactService;
import com.travel3d.vietlutravel.service.EmailService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private EmailService emailService;

    // kiểm tra admin
    private boolean isAdmin(HttpSession session) {
        Customer user = (Customer) session.getAttribute("user");
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    // ===============================
    // Danh sách tất cả liên hệ
    // ===============================
    @GetMapping("/contacts")
    public String listContacts(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        model.addAttribute("pageTitle", "Quản lý Nhận Liên Hệ");

        return "admin/contacts";
    }

    // ===============================
    // Gửi phản hồi qua email
    // ===============================
    @PostMapping("/contact/reply")
    public String replyContact(
            @RequestParam int contactId,
            @RequestParam String email,
            @RequestParam String messageReply,
            RedirectAttributes ra,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {
            String subject = "Phản hồi từ Việt Lữ Travel";

            emailService.sendReplyEmail(email, subject, messageReply);
            contactService.markReplied(contactId); // ← thêm dòng này

            ra.addFlashAttribute("successMessage", "Đã gửi email phản hồi cho khách!");

        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Gửi email thất bại: " + e.getMessage());
        }

        return "redirect:/admin/contacts";
    }

    // ===============================
    // Xóa liên hệ
    // ===============================
    @PostMapping("/contacts/{id}/delete")
    public String deleteContact(
            @PathVariable int id,
            RedirectAttributes ra,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {
            contactService.deleteContact(id);
            ra.addFlashAttribute("successMessage", "Xóa liên hệ thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Lỗi xóa: " + e.getMessage());
        }

        return "redirect:/admin/contacts";
    }
}