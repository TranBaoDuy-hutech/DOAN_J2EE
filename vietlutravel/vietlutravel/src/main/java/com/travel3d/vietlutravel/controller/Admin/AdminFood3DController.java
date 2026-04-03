package com.travel3d.vietlutravel.controller.Admin;

import java.util.List;

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
import com.travel3d.vietlutravel.model.Food;
import com.travel3d.vietlutravel.service.FoodService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/food3d")
public class AdminFood3DController {

    @Autowired
    private FoodService foodService;

    // =============================
    // Kiểm tra admin
    // =============================
    private boolean isAdmin(HttpSession session) {
        Customer user = (Customer) session.getAttribute("user");
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    // =============================
    // Danh sách ẩm thực 3D
    // =============================
    @GetMapping
    public String listFoods(Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        List<Food> foods = foodService.getAllFoods();
        model.addAttribute("foods", foods);
        model.addAttribute("pageTitle", "Quản lý Ẩm thực 3D");

        return "admin/food3d/index";
    }

    // =============================
    // Form thêm mới
    // =============================
    @GetMapping("/create")
    public String createForm(Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("food", new Food());
        model.addAttribute("pageTitle", "Thêm mới Ẩm thực");

        return "admin/food3d/form";
    }

    // =============================
    // Form sửa
    // =============================
    @GetMapping("/edit/{id}")
    public String editFood(@PathVariable Long id, Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Food food = foodService.getFoodById(id);

        if (food == null) {
            return "redirect:/admin/food3d";
        }

        model.addAttribute("food", food);
        model.addAttribute("pageTitle", "Sửa Ẩm thực");

        return "admin/food3d/form";
    }

    // =============================
    // SAVE (Thêm + Sửa)
    // =============================
    @PostMapping("/save")
    public String saveFood(
            @ModelAttribute Food food,
            RedirectAttributes ra,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {
            if (food.getName() == null || food.getName().trim().isEmpty()) {
                ra.addFlashAttribute("errorMessage", "Tên ẩm thực không được để trống!");
                return "redirect:/admin/food3d/create";
            }

            boolean isNew = food.getId() == null;
            foodService.saveFood(food);

            if (isNew) {
                ra.addFlashAttribute("successMessage", "Thêm ẩm thực thành công!");
            } else {
                ra.addFlashAttribute("successMessage", "Cập nhật ẩm thực thành công!");
            }

        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }

        return "redirect:/admin/food3d";
    }

    // =============================
    // Xóa
    // =============================
    @PostMapping("/{id}/delete")
    public String deleteFood(
            @PathVariable Long id,
            RedirectAttributes ra,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {
            foodService.deleteFood(id);
            ra.addFlashAttribute("successMessage", "Xóa ẩm thực thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Lỗi khi xóa: " + e.getMessage());
        }

        return "redirect:/admin/food3d";
    }

    // =============================
    // Xem chi tiết
    // =============================
    @GetMapping("/{id}")
    public String viewFood(
            @PathVariable Long id,
            Model model,
            HttpSession session,
            RedirectAttributes ra) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Food food = foodService.getFoodById(id);

        if (food == null) {
            ra.addFlashAttribute("errorMessage", "Không tìm thấy ẩm thực!");
            return "redirect:/admin/food3d";
        }

        model.addAttribute("food", food);
        model.addAttribute("pageTitle", "Chi tiết Ẩm thực - " + food.getName());

        return "admin/food3d/detail";
    }
}
