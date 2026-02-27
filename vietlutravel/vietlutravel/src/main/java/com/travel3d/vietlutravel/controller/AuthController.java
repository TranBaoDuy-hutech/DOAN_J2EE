package com.travel3d.vietlutravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travel3d.vietlutravel.model.Customer;
import com.travel3d.vietlutravel.service.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    // Thời gian sống OTP = 3 phút
    private static final long OTP_EXPIRATION_TIME = 3 * 60 * 1000;

    // ================= REGISTER =================
    @GetMapping("/register")
    public String registerForm(Model model) {
        if (!model.containsAttribute("customer")) {
            model.addAttribute("customer", new Customer());
        }
        return "register";
    }

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
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("customer", customer);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }

    // ================= LOGIN =================
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng nhập email và mật khẩu");
            return "redirect:/login";
        }

        Customer customer = authService.login(email.trim(), password.trim());

        if (customer == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email hoặc mật khẩu không đúng");
            return "redirect:/login";
        }

        session.setAttribute("user", customer);

        switch (customer.getRole().toUpperCase()) {
            case "ADMIN": return "redirect:/admin";
            case "STAFF": return "redirect:/staff/chat";
            default:      return "redirect:/";
        }
    }

    // ================= LOGOUT =================
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "Bạn đã đăng xuất");
        return "redirect:/";
    }

    // ================= FORGOT PASSWORD =================
    @GetMapping("/forgot-password")
    public String forgotPasswordForm(Model model) {
        model.addAttribute("otpSent", false);
        return "forgot-password";
    }

    // ================= GỬI OTP =================
    @PostMapping(value = "/forgot-password", params = "action=sendOtp")
    public String sendOtp(
            @RequestParam("email") String email,
            HttpSession session,
            Model model) {

        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("otpSent", false);
            model.addAttribute("errorMessage", "Vui lòng nhập email");
            return "forgot-password";
        }

        String otp = authService.generateOTP(email.trim());

        if (otp == null) {
            model.addAttribute("otpSent", false);
            model.addAttribute("errorMessage", "Email không tồn tại trong hệ thống");
            return "forgot-password";
        }

        // Lưu OTP + thời gian tạo
        long otpTime = System.currentTimeMillis();
        long otpExpiresAt = otpTime + OTP_EXPIRATION_TIME;

        session.setAttribute("otp", otp);
        session.setAttribute("resetEmail", email.trim());
        session.setAttribute("otpTime", otpTime);

        model.addAttribute("otpSent", true);
        model.addAttribute("email", email.trim());
        model.addAttribute("otpExpiresAt", otpExpiresAt);  // <-- truyền xuống HTML
        model.addAttribute("successMessage", "Mã OTP đã được gửi tới " + email.trim());

        return "forgot-password";
    }

    // ================= RESET PASSWORD =================
    @PostMapping(value = "/forgot-password", params = "action=resetPassword")
    public String resetPassword(
            @RequestParam("otp") String otp,
            @RequestParam("newPassword") String newPassword,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        String sessionOtp = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("resetEmail");
        Long otpTime = (Long) session.getAttribute("otpTime");

        // Session hết hạn
        if (sessionOtp == null || email == null || otpTime == null) {
            model.addAttribute("otpSent", false);
            model.addAttribute("errorMessage", "Phiên làm việc hết hạn, vui lòng nhập lại email");
            return "forgot-password";
        }

        // Kiểm tra OTP hết hạn (3 phút)
        long currentTime = System.currentTimeMillis();
        if (currentTime - otpTime > OTP_EXPIRATION_TIME) {
            session.removeAttribute("otp");
            session.removeAttribute("resetEmail");
            session.removeAttribute("otpTime");

            model.addAttribute("otpSent", false);
            model.addAttribute("errorMessage", "OTP đã hết hạn (3 phút). Vui lòng gửi lại.");
            return "forgot-password";
        }

        // OTP sai — giữ nguyên bước 2, truyền lại otpExpiresAt để đồng hồ không reset
        if (!sessionOtp.equals(otp.trim())) {
            long otpExpiresAt = otpTime + OTP_EXPIRATION_TIME;  // <-- tính lại từ session
            model.addAttribute("otpSent", true);
            model.addAttribute("email", email);
            model.addAttribute("otpExpiresAt", otpExpiresAt);   // <-- truyền xuống HTML
            model.addAttribute("errorMessage", "Mã OTP không đúng, vui lòng kiểm tra lại");
            return "forgot-password";
        }

        // Kiểm tra mật khẩu
        if (newPassword == null || newPassword.trim().length() < 6) {
            long otpExpiresAt = otpTime + OTP_EXPIRATION_TIME;
            model.addAttribute("otpSent", true);
            model.addAttribute("email", email);
            model.addAttribute("otpExpiresAt", otpExpiresAt);
            model.addAttribute("errorMessage", "Mật khẩu phải có ít nhất 6 ký tự");
            return "forgot-password";
        }

        boolean updated = authService.updatePasswordByEmail(email, newPassword.trim());

        if (!updated) {
            long otpExpiresAt = otpTime + OTP_EXPIRATION_TIME;
            model.addAttribute("otpSent", true);
            model.addAttribute("email", email);
            model.addAttribute("otpExpiresAt", otpExpiresAt);
            model.addAttribute("errorMessage", "Mật khẩu không hợp lệ (cần chữ hoa, số, ký tự đặc biệt)");
            return "forgot-password";
        }

        // Xóa OTP sau khi dùng
        session.removeAttribute("otp");
        session.removeAttribute("resetEmail");
        session.removeAttribute("otpTime");

        redirectAttributes.addFlashAttribute("successMessage", "Đổi mật khẩu thành công! Vui lòng đăng nhập lại.");
        return "redirect:/login";
    }
}