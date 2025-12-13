package com.Nhom19.shopQuanAo.DTO.Response.OrderDetailRes;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    private Integer maDdh;
    private String tenSp;
    private String urlImage;

    private String tenMau;
    private String tenKc;

    private BigDecimal gia;
    private Integer soLuong;
    private BigDecimal tongTien;

    public OrderItemResponse() {
    }

    public OrderItemResponse(Integer maDdh, String tenSp, String urlImage, String tenMau, String tenKc, BigDecimal gia, Integer soLuong, BigDecimal tongTien) {
        this.maDdh = maDdh;
        this.tenSp = tenSp;
        this.urlImage = urlImage;
        this.tenMau = tenMau;
        this.tenKc = tenKc;
        this.gia = gia;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public Integer getMaDdh() {
        return maDdh;
    }

    public void setMaDdh(Integer maDdh) {
        this.maDdh = maDdh;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public String getTenKc() {
        return tenKc;
    }

    public void setTenKc(String tenKc) {
        this.tenKc = tenKc;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }
}
