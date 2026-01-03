package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;

import java.math.BigDecimal;

public class TopCustomerDTO {
    private Integer maTk;
    private String tenKhachHang;
    private Long soDon;
    private BigDecimal tongChi;
    public TopCustomerDTO(Integer maTk,
            String tenKhachHang,
            Long soDon,
            BigDecimal tongChi
    ) {
        this.maTk = maTk;
        this.tenKhachHang = tenKhachHang;
        this.soDon = soDon;
        this.tongChi = tongChi;
    }

    public Integer getMaTk() {
        return maTk;
    }

    public void setMaTk(Integer maTk) {
        this.maTk = maTk;
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
