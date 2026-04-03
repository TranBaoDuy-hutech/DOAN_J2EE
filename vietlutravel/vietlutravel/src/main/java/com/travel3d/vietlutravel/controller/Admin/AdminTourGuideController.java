package com.travel3d.vietlutravel.controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travel3d.vietlutravel.model.TourGuide;
import com.travel3d.vietlutravel.service.TourGuideService;

@Controller
@RequestMapping("/admin/tour-guides")
public class AdminTourGuideController {

    @Autowired
    private TourGuideService tourGuideService;

    // =============================
    // List
    // =============================
    @GetMapping
    public String index(Model model) {
        model.addAttribute("guides", tourGuideService.getAllGuides());
        return "admin/tour-guides/index";
    }

    // =============================
    // Create
    // =============================
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("guide", new TourGuide());
        return "admin/tour-guides/form";
    }

    // =============================
    // Edit
    // =============================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra) {

        TourGuide guide = tourGuideService.getGuideById(id);

        if (guide == null) {
            ra.addFlashAttribute("errorMessage", "Không tìm thấy hướng dẫn viên!");
            return "redirect:/admin/tour-guides";
        }

        model.addAttribute("guide", guide);
        return "admin/tour-guides/form";
    }

    // =============================
    // Detail (fix lỗi 404)
    // =============================
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model, RedirectAttributes ra) {

        TourGuide guide = tourGuideService.getGuideById(id);

        if (guide == null) {
            ra.addFlashAttribute("errorMessage", "Không tìm thấy hướng dẫn viên!");
            return "redirect:/admin/tour-guides";
        }

        model.addAttribute("guide", guide);
        return "admin/tour-guides/detail";
    }

    // =============================
    // Save (CREATE + UPDATE)
    // =============================
    @PostMapping("/save")
    public String save(@ModelAttribute TourGuide guide, RedirectAttributes ra) {

        // ===== VALIDATE =====
        if (guide.getFullName() == null || guide.getFullName().trim().isEmpty()) {
            ra.addFlashAttribute("errorMessage", "Họ và tên không được để trống!");
            return "redirect:/admin/tour-guides";
        }

        if (guide.getPhone() == null || !guide.getPhone().matches("^0\\d{9}$")) {
            ra.addFlashAttribute("errorMessage", "Số điện thoại không hợp lệ!");
            return "redirect:/admin/tour-guides";
        }

        if (guide.getEmail() == null || guide.getEmail().trim().isEmpty()) {
            ra.addFlashAttribute("errorMessage", "Email không được để trống!");
            return "redirect:/admin/tour-guides";
        }

        if (!guide.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            ra.addFlashAttribute("errorMessage", "Email không hợp lệ!");
            return "redirect:/admin/tour-guides";
        }

        // ===== CHECK EMAIL UNIQUE =====
        boolean isNew = guide.getGuideId() == null || guide.getGuideId() == 0;
        if (!isNew) {
            // Edit: check không trùng với guide khác
            TourGuide existing = tourGuideService.getGuideById(guide.getGuideId());
            if (existing != null && !existing.getEmail().equals(guide.getEmail())) {
                // Email thay đổi, check trùng
                for (TourGuide g : tourGuideService.getAllGuides()) {
                    if (!g.getGuideId().equals(guide.getGuideId()) && g.getEmail().equals(guide.getEmail())) {
                        ra.addFlashAttribute("errorMessage", "Email đã tồn tại!");
                        return "redirect:/admin/tour-guides";
                    }
                }
            }
        } else {
            // Create: check không trùng bất kỳ
            for (TourGuide g : tourGuideService.getAllGuides()) {
                if (g.getEmail().equals(guide.getEmail())) {
                    ra.addFlashAttribute("errorMessage", "Email đã tồn tại!");
                    return "redirect:/admin/tour-guides";
                }
            }
        }

        if (guide.getExperienceYears() != null && guide.getExperienceYears() < 0) {
            ra.addFlashAttribute("errorMessage", "Số năm kinh nghiệm không hợp lệ!");
            return "redirect:/admin/tour-guides";
        }

        if (guide.getStatus() == null || guide.getStatus().isEmpty()) {
            guide.setStatus("AVAILABLE");
        }

        // ===== SAVE =====
        try {
            tourGuideService.saveGuide(guide);
            ra.addFlashAttribute("successMessage", "Lưu thành công!");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("errorMessage", "Email đã tồn tại!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }

        return "redirect:/admin/tour-guides";
    }

    // =============================
    // DELETE (fix bảo mật)
    // =============================
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {

        try {
            tourGuideService.deleteGuide(id);
            ra.addFlashAttribute("successMessage", "Xóa thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Không thể xóa (có thể đã được phân công)!");
        }

        return "redirect:/admin/tour-guides";
    }
}