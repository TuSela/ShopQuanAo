package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;

import lombok.Data;

public class OrderStatusStatisticDTO {
    private String trangThai;
    private Long soDon;
    public OrderStatusStatisticDTO(String trangThai, Long soDon) {
        this.trangThai = trangThai;
        this.soDon = soDon;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Long getSoDon() {
        return soDon;
    }

    public void setSoDon(Long soDon) {
        this.soDon = soDon;
    }
}
