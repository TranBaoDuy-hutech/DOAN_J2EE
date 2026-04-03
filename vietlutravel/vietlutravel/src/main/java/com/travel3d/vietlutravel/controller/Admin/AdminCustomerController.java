package com.travel3d.vietlutravel.controller.Admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travel3d.vietlutravel.model.Customer;
import com.travel3d.vietlutravel.service.CustomerService;

import jakarta.servlet.http.HttpSession;

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
        // Lọc bỏ admin khỏi danh sách
        customers = customers.stream()
                .filter(c -> !"ADMIN".equalsIgnoreCase(c.getRole()))
                .collect(Collectors.toList());

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

        // Không cho edit admin
        if ("ADMIN".equalsIgnoreCase(customer.getRole())) {
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

            // Nếu đang edit, kiểm tra role hiện tại
            if (!isNew) {
                Customer existing = customerService.getCustomerById(customer.getCustomerID());
                if (existing != null && "ADMIN".equalsIgnoreCase(existing.getRole())) {
                    // Giữ nguyên role ADMIN, không cho thay đổi
                    customer.setRole("ADMIN");
                }
            }

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
    @PostMapping("/{id}/delete")
    public String deleteCustomer(
            @PathVariable int id,
            RedirectAttributes ra,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {
            Customer customer = customerService.getCustomerById(id);
            if (customer != null && "ADMIN".equalsIgnoreCase(customer.getRole())) {
                ra.addFlashAttribute("errorMessage", "Không thể xóa tài khoản admin!");
                return "redirect:/admin/customers";
            }

            customerService.deleteCustomer(id);
            ra.addFlashAttribute("successMessage", "Xóa thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/customers";
    }

    @GetMapping("/{id}")
    public String viewCustomer(
            @PathVariable int id,
            Model model,
            HttpSession session,
            RedirectAttributes ra) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Customer customer = customerService.getCustomerById(id);

        if (customer == null) {
            ra.addFlashAttribute("errorMessage", "Không tìm thấy người dùng!");
            return "redirect:/admin/customers";
        }

        // Không cho xem admin
        if ("ADMIN".equalsIgnoreCase(customer.getRole())) {
            ra.addFlashAttribute("errorMessage", "Không thể xem chi tiết admin!");
            return "redirect:/admin/customers";
        }

        model.addAttribute("customer", customer);
        return "admin/customer-detail"; // tạo file này
    }
}