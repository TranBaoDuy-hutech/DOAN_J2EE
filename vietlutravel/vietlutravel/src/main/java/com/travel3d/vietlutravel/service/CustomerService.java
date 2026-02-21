package com.travel3d.vietlutravel.service;

import com.travel3d.vietlutravel.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    @PersistenceContext
    private EntityManager entityManager;

    // =========================
    // Lấy tất cả người dùng
    // =========================
    public List<Customer> getAllCustomers() {
        return entityManager.createQuery(
                "SELECT c FROM Customer c ORDER BY c.customerID DESC",
                Customer.class
        ).getResultList();
    }

    // =========================
    // Lấy theo ID
    // =========================
    public Customer getCustomerById(int id) {
        return entityManager.find(Customer.class, id);
    }

    // =========================
    // Thêm hoặc cập nhật user
    // =========================
    @Transactional
    public Customer saveCustomer(Customer customer) {

        if (customer.getCustomerID() == 0) {
            entityManager.persist(customer);
            return customer;
        } else {
            return entityManager.merge(customer);
        }
    }

    // =========================
    // Cập nhật role
    // =========================
    @Transactional
    public void updateCustomerRole(int id, String newRole) {

        Customer customer = entityManager.find(Customer.class, id);

        if (customer == null) {
            throw new IllegalArgumentException("Người dùng không tồn tại");
        }

        customer.setRole(newRole);
    }

    // =========================
    // Xóa user
    // =========================
    @Transactional
    public void deleteCustomer(int id) {

        Customer customer = entityManager.find(Customer.class, id);

        if (customer == null) {
            throw new IllegalArgumentException("Người dùng không tồn tại");
        }

        if ("ADMIN".equalsIgnoreCase(customer.getRole())) {
            throw new IllegalArgumentException("Không thể xóa ADMIN");
        }

        entityManager.remove(customer);
    }

    // =========================
    // Tìm kiếm user
    // =========================
    public List<Customer> searchCustomers(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCustomers();
        }

        return entityManager.createQuery(
                        "SELECT c FROM Customer c WHERE LOWER(c.userName) LIKE LOWER(:kw) OR LOWER(c.email) LIKE LOWER(:kw) ORDER BY c.customerID DESC",
                        Customer.class)
                .setParameter("kw", "%" + keyword.trim() + "%")
                .getResultList();
    }
}
