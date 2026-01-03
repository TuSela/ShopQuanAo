package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;


import java.math.BigDecimal;

public class CustomerStatisticDTO {
    private String tenKhachHang;
    private Long soDon;
    private BigDecimal tongChi;
    public CustomerStatisticDTO(
            String tenKhachHang,
            Long soDon,
            BigDecimal tongChi
    ) {
        this.tenKhachHang = tenKhachHang;
        this.soDon = soDon;
        this.tongChi = tongChi;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public Long getSoDon() {
        return soDon;
    }

    public void setSoDon(Long soDon) {
        this.soDon = soDon;
    }

    public BigDecimal getTongChi() {
        return tongChi;
    }

    public void setTongChi(BigDecimal tongChi) {
        this.tongChi = tongChi;
    }
}
