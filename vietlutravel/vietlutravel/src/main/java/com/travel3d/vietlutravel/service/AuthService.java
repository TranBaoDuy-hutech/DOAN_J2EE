package com.travel3d.vietlutravel.service;

import com.travel3d.vietlutravel.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void register(Customer customer) {
        // Validate cơ bản
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (customer.getPasswordHash() == null || customer.getPasswordHash().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }

        // Validate mật khẩu phức tạp
        String password = customer.getPasswordHash().trim();
        if (password.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Mật khẩu phải chứa ít nhất 1 chữ cái in hoa");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Mật khẩu phải chứa ít nhất 1 chữ cái thường");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("Mật khẩu phải chứa ít nhất 1 chữ số");
        }
        if (!password.matches(".*[!@#$%^&*()_+-=\\[\\]{}|;:',.<>?/].*")) {
            throw new IllegalArgumentException("Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt (ví dụ: ! @ # $ % ...)");
        }

        // Kiểm tra email trùng
        try {
            entityManager.createQuery(
                            "SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email", customer.getEmail())
                    .getSingleResult();
            // Nếu đến đây → email đã tồn tại
            throw new IllegalArgumentException("Email '" + customer.getEmail() + "' đã được sử dụng!");
        } catch (NoResultException e) {
            // Email chưa tồn tại → tiếp tục
        }

        // Set role mặc định nếu chưa có
        if (customer.getRole() == null || customer.getRole().trim().isEmpty()) {
            customer.setRole("USER");
        }

        // Lưu vào DB (mật khẩu lưu plain text như yêu cầu test)
        entityManager.persist(customer);
    }

    public Customer login(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null;
        }

        try {
            Customer customer = entityManager.createQuery(
                            "SELECT c FROM Customer c WHERE c.email = :email AND c.passwordHash = :pass",
                            Customer.class)
                    .setParameter("email", email.trim())
                    .setParameter("pass", password.trim())  // plain text
                    .getSingleResult();

            return customer;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.err.println("Lỗi login: " + e.getMessage());
            return null;
        }
    }
    @Transactional
    public Customer processSocialLogin(String email, String name) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        try {
            // Check if user exists
            return entityManager.createQuery(
                            "SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Create new user
            Customer newCustomer = new Customer();
            newCustomer.setEmail(email);
            newCustomer.setUserName(name != null ? name : "Google User");
            newCustomer.setPasswordHash("GOOGLE_AUTH_NO_PASS"); // Dummy password to satisfy DB NOT NULL constraint
            newCustomer.setRole("USER");
            newCustomer.setPhone("0000000000"); // Dummy phone
            newCustomer.setAddress("N/A"); // Dummy address

            entityManager.persist(newCustomer);
            return newCustomer;
        }
    }
}