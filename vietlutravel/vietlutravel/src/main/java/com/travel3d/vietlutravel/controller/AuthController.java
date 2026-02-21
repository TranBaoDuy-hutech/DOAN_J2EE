package com.travel3d.vietlutravel.controller;

import com.travel3d.vietlutravel.model.Customer;
import com.travel3d.vietlutravel.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    // Trang đăng ký
    @GetMapping("/register")
    public String registerForm(Model model) {
        if (!model.containsAttribute("customer")) {
            model.addAttribute("customer", new Customer());
        }
        return "register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String register(
            @ModelAttribute Customer customer,
            RedirectAttributes redirectAttributes) {

        try {
            if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email không được để trống");
            }

            if (customer.getPasswordHash() == null || customer.getPasswordHash().trim().length() < 6) {
                throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự");
            }

            if (customer.getUserName() == null || customer.getUserName().trim().isEmpty()) {
                throw new IllegalArgumentException("Họ tên không được để trống");
            }

            authService.register(customer);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Đăng ký thành công! Vui lòng đăng nhập để tiếp tục.");
            return "redirect:/login";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("customer", customer);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Có lỗi xảy ra khi đăng ký. Vui lòng thử lại sau.");
            return "redirect:/register";
        }
    }

    // Trang login
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // Xử lý login
    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Vui lòng nhập đầy đủ email và mật khẩu");
            return "redirect:/login";
        }
        Customer customer = authService.login(email.trim(), password.trim());

        if (customer == null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Email hoặc mật khẩu không đúng");
            return "redirect:/login";
        }
        session.setAttribute("user", customer);

        String role = customer.getRole().toUpperCase();

        // PHÂN QUYỀN
        switch (role) {
            case "ADMIN":
                redirectAttributes.addFlashAttribute("successMessage",
                        "Đăng nhập Admin thành công!");
                return "redirect:/admin";

            case "STAFF":
                redirectAttributes.addFlashAttribute("successMessage",
                        "Đăng nhập Staff CSKH thành công!");
                return "redirect:/staff/chat";

            default:
                redirectAttributes.addFlashAttribute("successMessage",
                        "Đăng nhập thành công!");
                return "redirect:/";
        }
    }


    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session,
                         RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage",
                "Bạn đã đăng xuất thành công");
        return "redirect:/";
    }
}
