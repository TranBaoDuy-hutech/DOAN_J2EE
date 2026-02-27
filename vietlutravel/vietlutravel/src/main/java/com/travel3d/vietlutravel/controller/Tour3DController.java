package com.travel3d.vietlutravel.controller;

import com.travel3d.vietlutravel.model.Food;
import com.travel3d.vietlutravel.model.VanHoa;
import com.travel3d.vietlutravel.service.FoodService;
import com.travel3d.vietlutravel.service.VanHoaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@Controller
public class Tour3DController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private VanHoaService vanHoaService;  // ← THÊM

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
    public String culture360(Model model) {  // ← THÊM Model
        model.addAttribute("danhSachVanHoa", vanHoaService.getAll());  // ← THÊM
        return "tour3d/culture";
    }
    @GetMapping("/tour3d/culture/{id}")
    public String cultureDetail(@PathVariable Long id, Model model) {
        Optional<VanHoa> vanHoa = vanHoaService.getById(id);
        if (vanHoa.isEmpty()) return "redirect:/tour3d/culture";

        model.addAttribute("vanHoa", vanHoa.get());

        List<VanHoa> related = vanHoaService.getAll().stream()
                .filter(v -> !v.getId().equals(id))
                .sorted((a, b) -> Math.random() > 0.5 ? 1 : -1)
                .limit(3)
                .toList();
        model.addAttribute("relatedVanHoa", related);

        return "tour3d/culture-detail";
    }
}