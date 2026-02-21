package com.travel3d.vietlutravel.controller.Admin;

import com.travel3d.vietlutravel.model.Tours;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/tours")
public class AdminTourController {

    @PersistenceContext
    private EntityManager entityManager;

    // ===== Danh sách tour =====
    @GetMapping
    public String index(Model model) {
        List<Tours> tours = entityManager
                .createQuery("FROM Tours", Tours.class)
                .getResultList();

        model.addAttribute("tours", tours);
        return "admin/tours/index";
    }

    // ===== Form thêm =====
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("tour", new Tours());
        return "admin/tours/form";
    }

    // ===== Form sửa =====
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Tours tour = entityManager.find(Tours.class, id);
        model.addAttribute("tour", tour);
        return "admin/tours/form";
    }

    // ===== Lưu (thêm + sửa) =====
    @PostMapping("/save")
    @Transactional
    public String save(@ModelAttribute Tours tour) {

        if (tour.getTourID() == 0) {
            entityManager.persist(tour);
        } else {
            entityManager.merge(tour);
        }

        return "redirect:/admin/tours";
    }

    // ===== Xóa =====
    @GetMapping("/delete/{id}")
    @Transactional
    public String delete(@PathVariable int id) {
        Tours tour = entityManager.find(Tours.class, id);
        entityManager.remove(tour);
        return "redirect:/admin/tours";
    }
}
