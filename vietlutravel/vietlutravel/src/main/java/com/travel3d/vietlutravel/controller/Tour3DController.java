package com.travel3d.vietlutravel.controller;

import com.travel3d.vietlutravel.model.Food;
import com.travel3d.vietlutravel.service.FoodService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class Tour3DController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/tour3d")
    public String tour3dHome() {
        return "tour3d/index";
    }

    @GetMapping("/tour3d/tour")
    public String tour360() {
        return "tour3d/tour";
    }

    @GetMapping("/tour3d/food")
    public String food3d(Model model) {
        model.addAttribute("foods", foodService.getAllFoods());
        return "tour3d/food";
    }

    @GetMapping("/tour3d/food/{id}")
    public String foodDetail(@PathVariable Long id, Model model) {
        Food food = foodService.getFoodById(id);
        if (food == null) {
            return "redirect:/tour3d/food";
        }
        model.addAttribute("food", food);

        // Get 3 random related foods (excluding current one)
        List<Food> allFoods = foodService.getAllFoods();
        List<Food> relatedFoods = allFoods.stream()
                .filter(f -> !f.getId().equals(id))
                .sorted((f1, f2) -> Math.random() > 0.5 ? 1 : -1)
                .limit(3)
                .toList();
        model.addAttribute("relatedFoods", relatedFoods);

        return "tour3d/food-detail";
    }

    @GetMapping("/tour3d/culture")
    public String culture360() {
        return "tour3d/culture";
    }
}