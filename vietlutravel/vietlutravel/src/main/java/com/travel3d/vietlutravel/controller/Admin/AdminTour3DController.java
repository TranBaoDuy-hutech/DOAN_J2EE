package com.travel3d.vietlutravel.controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/tour3d")
public class AdminTour3DController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pageTitle", "Tour 3D - Coming Soon");
        return "admin/tour3d-manage/coming-soon";
    }

    @GetMapping("/coming-soon")
    public String comingSoon(Model model) {
        model.addAttribute("pageTitle", "Coming Soon - Admin");
        return "admin/tour3d-manage/coming-soon";
    }
}
