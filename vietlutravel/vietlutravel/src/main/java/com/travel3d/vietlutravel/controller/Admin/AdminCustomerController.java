package com.travel3d.vietlutravel.controller.Admin;

import com.travel3d.vietlutravel.model.Customer;
import com.travel3d.vietlutravel.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/customers")
public class AdminCustomerController {

    @Autowired
    private CustomerService customerService;

    // =============================
    // Kiểm tra admin
    // =============================
    private boolean isAdmin(HttpSession session) {
        Customer user = (Customer) session.getAttribute("user");
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    // =============================
    // Danh sách khách hàng
    // =============================
    @GetMapping
    public String listCustomers(Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        List<Customer> customers = customerService.getAllCustomers();

        model.addAttribute("customers", customers);
        model.addAttribute("pageTitle", "Quản lý người dùng");

        return "admin/customers";
    }

    // =============================
    // Form thêm
    // =============================
    @GetMapping("/create")
    public String createForm(Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("customer", new Customer());
        return "admin/customer-form";
    }

    // =============================
    // Form sửa
    // =============================
    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable int id, Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Customer customer = customerService.getCustomerById(id);

        if (customer == null) {
            return "redirect:/admin/customers";
        }

        model.addAttribute("customer", customer);
        return "admin/customer-form";
    }

    // =============================
    // SAVE (Thêm + Sửa)
    // =============================
    @PostMapping("/save")
    public String saveCustomer(
            @ModelAttribute Customer customer,
            RedirectAttributes ra,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {

            boolean isNew = customer.getCustomerID() == 0;

            customerService.saveCustomer(customer);

            if (isNew) {
                ra.addFlashAttribute("successMessage", "Thêm người dùng thành công!");
            } else {
                ra.addFlashAttribute("successMessage", "Cập nhật thành công!");
            }

        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }

        return "redirect:/admin/customers";
    }

    // =============================
    // Xóa
    // =============================
    @PostMapping("/delete/{id}")
    public String deleteCustomer(
            @PathVariable int id,
            RedirectAttributes ra,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {
            customerService.deleteCustomer(id);
            ra.addFlashAttribute("successMessage", "Xóa thành công!");

        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/customers";
    }
}
