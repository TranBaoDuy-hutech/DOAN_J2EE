package com.travel3d.vietlutravel.service;

import com.travel3d.vietlutravel.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
public class AuthService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmailService emailService;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    // ================= REGISTER =================
    @Transactional
    public void register(Customer customer) {

        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }

        if (customer.getPasswordHash() == null || customer.getPasswordHash().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }

        String password = customer.getPasswordHash().trim();
        validatePassword(password);

        // Kiểm tra email trùng
        try {
            entityManager.createQuery(
                            "SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email", customer.getEmail())
                    .getSingleResult();
            throw new IllegalArgumentException("Email đã tồn tại!");
        } catch (NoResultException e) {
            // email hợp lệ
        }

        if (customer.getRole() == null || customer.getRole().isEmpty()) {
            customer.setRole("USER");
        }

        entityManager.persist(customer);
    }

    // ================= LOGIN =================
    public Customer login(String email, String password) {

        if (email == null || password == null) return null;

        try {
            return entityManager.createQuery(
                            "SELECT c FROM Customer c WHERE c.email = :email AND c.passwordHash = :pass",
                            Customer.class)
                    .setParameter("email", email.trim())
                    .setParameter("pass", password.trim())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // ================= SOCIAL LOGIN =================
    @Transactional
    public Customer processSocialLogin(String email, String name) {

        try {
            return entityManager.createQuery(
                            "SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            Customer newCustomer = new Customer();
            newCustomer.setEmail(email);
            newCustomer.setUserName(name != null ? name : "Google User");
            newCustomer.setPasswordHash("GOOGLE_LOGIN");
            newCustomer.setRole("USER");
            newCustomer.setPhone("0000000000");
            newCustomer.setAddress("N/A");
            entityManager.persist(newCustomer);
            return newCustomer;
        }
    }

    // ================= GENERATE & GỬI OTP =================
    public String generateOTP(String email) {

        try {
            entityManager.createQuery(
                            "SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();

            // Tạo OTP 6 số an toàn
            String otp = String.format("%06d", SECURE_RANDOM.nextInt(1_000_000));

            // Gửi email thật
            emailService.sendOtpEmail(email, otp);

            return otp;

        } catch (NoResultException e) {
            return null;
        }
    }

    // ================= UPDATE PASSWORD =================
    @Transactional
    public boolean updatePasswordByEmail(String email, String newPassword) {

        if (newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }

        try {
            validatePassword(newPassword.trim());
        } catch (IllegalArgumentException e) {
            return false;
        }

        try {
            Customer customer = entityManager.createQuery(
                            "SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();

            customer.setPasswordHash(newPassword.trim());
            entityManager.merge(customer);

            // Gửi mail xác nhận đổi mật khẩu thành công
            emailService.sendPasswordChangedEmail(email);

            return true;

        } catch (NoResultException e) {
            return false;
        }
    }

    // ================= VALIDATE PASSWORD =================
    private void validatePassword(String password) {

        if (password.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Mật khẩu phải chứa ít nhất 1 chữ hoa");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Mật khẩu phải chứa ít nhất 1 chữ thường");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("Mật khẩu phải chứa ít nhất 1 số");
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            throw new IllegalArgumentException("Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt");
        }
    }
}