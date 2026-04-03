package com.travel3d.vietlutravel.controller.Admin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travel3d.vietlutravel.model.Customer;
import com.travel3d.vietlutravel.model.VanHoa;
import com.travel3d.vietlutravel.service.VanHoaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/vanhoa3d")
public class AdminVanHoa3DController {

    @Autowired
    private VanHoaService vanHoaService;

    // =============================
    // Kiểm tra admin
    // =============================
    private boolean isAdmin(HttpSession session) {
        Customer user = (Customer) session.getAttribute("user");
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    // =============================
    // Danh sách văn hóa 3D
    // =============================
    @GetMapping
    public String listVanHoa(Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        List<VanHoa> vanHoas = vanHoaService.getAll();
        model.addAttribute("vanHoas", vanHoas);
        model.addAttribute("pageTitle", "Quản lý Văn hóa 3D");

        return "admin/vanhoa3d/index";
    }

    // =============================
    // Form thêm mới
    // =============================
    @GetMapping("/create")
    public String createForm(Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("vanHoa", new VanHoa());
        model.addAttribute("pageTitle", "Thêm mới Văn hóa");

        return "admin/vanhoa3d/form";
    }

    // =============================
    // Form sửa
    // =============================
    @GetMapping("/edit/{id}")
    public String editVanHoa(@PathVariable Long id, Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        java.util.Optional<VanHoa> vanHoa = vanHoaService.getById(id);

        if (vanHoa.isEmpty()) {
            return "redirect:/admin/vanhoa3d";
        }

        model.addAttribute("vanHoa", vanHoa.get());
        model.addAttribute("pageTitle", "Sửa Văn hóa");

        return "admin/vanhoa3d/form";
    }

    // =============================
    // SAVE (Thêm + Sửa)
    // =============================
    @PostMapping("/save")
    public String saveVanHoa(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("tieuDe") String tieuDe,
            @RequestParam(value = "moTa", required = false) String moTa,
            @RequestParam(value = "danhMuc", required = false) String danhMuc,
            @RequestParam(value = "videoUrl", required = false) String videoUrl,
            @RequestParam(value = "hinhAnhUrl", required = false) String hinhAnhUrl,
            @RequestParam(value = "hinhAnhFile", required = false) MultipartFile hinhAnhFile,
            RedirectAttributes ra,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {
            if (tieuDe == null || tieuDe.trim().isEmpty()) {
                ra.addFlashAttribute("errorMessage", "Tiêu đề không được để trống!");
                return "redirect:/admin/vanhoa3d/create";
            }

            // Tạo hoặc lấy VanHoa object
            VanHoa vanHoa;
            boolean isNew = id == null;
            if (isNew) {
                vanHoa = new VanHoa();
            } else {
                vanHoa = vanHoaService.getById(id).orElseThrow(() ->
                    new RuntimeException("Không tìm thấy VanHoa với id: " + id));
            }

            // Set các fields
            vanHoa.setTieuDe(tieuDe.trim());
            if (moTa != null) vanHoa.setMoTa(moTa.trim());
            if (danhMuc != null) vanHoa.setDanhMuc(danhMuc.trim());
            if (videoUrl != null && !videoUrl.trim().isEmpty()) {
                vanHoa.setVideoUrl(convertToEmbedUrl(videoUrl.trim()));
            }

            // Xử lý hình ảnh - Chỉ set khi có dữ liệu mới
            if (hinhAnhFile != null && !hinhAnhFile.isEmpty()) {
                // Upload file từ máy tính
                String fileName = saveUploadedFile(hinhAnhFile);
                vanHoa.setHinhAnh("/image/" + fileName);
            } else if (hinhAnhUrl != null && !hinhAnhUrl.trim().isEmpty()) {
                // Sử dụng URL mới (chuyển đổi nếu cần)
                vanHoa.setHinhAnh(resolveImageUrl(hinhAnhUrl.trim()));
            }
            // Nếu không có gì mới thì giữ nguyên giá trị cũ (quan trọng cho edit mode)

            vanHoaService.save(vanHoa);

            if (isNew) {
                ra.addFlashAttribute("successMessage", "Thêm văn hóa thành công!");
            } else {
                ra.addFlashAttribute("successMessage", "Cập nhật văn hóa thành công!");
            }

        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }

        return "redirect:/admin/vanhoa3d";
    }

    // =============================
    // Xóa
    // =============================
    @PostMapping("/{id}/delete")
    public String deleteVanHoa(
            @PathVariable Long id,
            RedirectAttributes ra,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {
            vanHoaService.delete(id);
            ra.addFlashAttribute("successMessage", "Xóa văn hóa thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Lỗi khi xóa: " + e.getMessage());
        }

        return "redirect:/admin/vanhoa3d";
    }

    // =============================
    // Xem chi tiết
    // =============================
    @GetMapping("/{id}")
    public String viewVanHoa(
            @PathVariable Long id,
            Model model,
            HttpSession session,
            RedirectAttributes ra) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        java.util.Optional<VanHoa> vanHoa = vanHoaService.getById(id);

        if (vanHoa.isEmpty()) {
            ra.addFlashAttribute("errorMessage", "Không tìm thấy văn hóa!");
            return "redirect:/admin/vanhoa3d";
        }

        model.addAttribute("vanHoa", vanHoa.get());
        model.addAttribute("pageTitle", "Chi tiết Văn hóa - " + vanHoa.get().getTieuDe());

        return "admin/vanhoa3d/detail";
    }

    // =============================
    // Helper: Save uploaded file
    // =============================
    private String saveUploadedFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("File không được để trống");
        }

        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new Exception("Chỉ chấp nhận file ảnh");
        }

        // Validate file size (max 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new Exception("File quá lớn (tối đa 5MB)");
        }

        // Tạo tên file unique
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String fileName = "vanhoa_" + System.currentTimeMillis() + extension;

        // Đường dẫn đến thư mục static/image
        Path uploadDir = Paths.get("src/main/resources/static/image");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Save file
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    // =============================
    // Helper: normalize YouTube URL for iframe
    // =============================
    private String convertToEmbedUrl(String rawUrl) {
        if (rawUrl == null || rawUrl.trim().isEmpty()) {
            return null;
        }
        rawUrl = rawUrl.trim();

        if (rawUrl.contains("youtube.com/embed/")) {
            return rawUrl;
        }

        try {
            if (rawUrl.contains("youtube.com/watch")) {
                String[] parts = rawUrl.split("[?&]");
                for (String part : parts) {
                    if (part.startsWith("v=")) {
                        String videoId = part.substring(2);
                        return "https://www.youtube.com/embed/" + videoId;
                    }
                }
            }

            if (rawUrl.contains("youtu.be/")) {
                int idx = rawUrl.indexOf("youtu.be/") + 9;
                String videoId = rawUrl.substring(idx);
                int q = videoId.indexOf('?');
                if (q > 0) {
                    videoId = videoId.substring(0, q);
                }
                return "https://www.youtube.com/embed/" + videoId;
            }
        } catch (Exception e) {
            // fallback giữ nguyên rawUrl
        }

        // Nếu không phải YouTube, giữ nguyên.
        return rawUrl;
    }

    private String resolveImageUrl(String hinhAnhUrl) {
        if (hinhAnhUrl == null || hinhAnhUrl.trim().isEmpty()) {
            return null;
        }
        hinhAnhUrl = hinhAnhUrl.trim();

        // Loại bỏ query params nếu có, ví dụ: /image/x.jpg? -> /image/x.jpg
        int queryIdx = hinhAnhUrl.indexOf('?');
        if (queryIdx >= 0) {
            hinhAnhUrl = hinhAnhUrl.substring(0, queryIdx);
        }

        // Nếu không bắt đầu bằng / hoặc http(s), tự thêm /image/
        if (hinhAnhUrl.startsWith("/") || hinhAnhUrl.startsWith("http://") || hinhAnhUrl.startsWith("https://")) {
            return hinhAnhUrl;
        }

        return "/image/" + hinhAnhUrl;
    }
}
