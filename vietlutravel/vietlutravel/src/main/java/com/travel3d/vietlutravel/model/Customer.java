package com.travel3d.vietlutravel.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private int customerID;

    @Column(name = "UserName", nullable = false, length = 50)
    private String userName;

    @Column(name = "Email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "Phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "Address", length = 200)
    private String address;

    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "Gender", length = 10)
    private String gender;

    @Column(name = "PasswordHash", nullable = true, length = 256)
    private String passwordHash;

    // Thêm field role để hỗ trợ phân quyền (USER / ADMIN)
    @Column(name = "Role", length = 20, nullable = false)
    private String role = "USER"; // mặc định là USER

    public Customer() {
    }

    // Constructor đầy đủ (tùy chọn, tiện khi test)
    public Customer(String userName, String email, String phone, String address,
            LocalDate dateOfBirth, String gender, String passwordHash, String role) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.passwordHash = passwordHash;
        this.role = (role != null && !role.isBlank()) ? role : "USER";
    }


    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // Getter & Setter cho role (quan trọng để AuthController dùng)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}