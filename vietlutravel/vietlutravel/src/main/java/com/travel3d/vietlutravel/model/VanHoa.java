package com.travel3d.vietlutravel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vanhoa")
public class VanHoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "TieuDe", nullable = false, length = 255)
    private String tieuDe;

    @Column(name = "MoTa", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "VideoUrl", length = 500)
    private String videoUrl;

    @Column(name = "HinhAnh", length = 500)
    private String hinhAnh;

    @Column(name = "DanhMuc", length = 50)
    private String danhMuc;

    // Constructor mặc định (bắt buộc cho JPA)
    public VanHoa() {
    }

    // Constructor tiện lợi khi tạo mới (không cần id)
    public VanHoa(String tieuDe, String moTa, String videoUrl, String hinhAnh, String danhMuc) {
        this.tieuDe = tieuDe;
        this.moTa = moTa;
        this.videoUrl = videoUrl;
        this.hinhAnh = hinhAnh;
        this.danhMuc = danhMuc;
    }

    // Constructor đầy đủ (dùng khi map từ DB hoặc test)
    public VanHoa(Long id, String tieuDe, String moTa, String videoUrl, String hinhAnh) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.moTa = moTa;
        this.videoUrl = videoUrl;
        this.hinhAnh = hinhAnh;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public String getDanhMuc() {
        return danhMuc; }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }


    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc; }

    // toString() hữu ích khi debug/log
    @Override
    public String toString() {
        return "VanHoa{" +
                "id=" + id +
                ", tieuDe='" + tieuDe + '\'' +
                ", moTa='" + (moTa != null ? moTa.substring(0, Math.min(50, moTa.length())) + "..." : null) + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                '}';
    }
}