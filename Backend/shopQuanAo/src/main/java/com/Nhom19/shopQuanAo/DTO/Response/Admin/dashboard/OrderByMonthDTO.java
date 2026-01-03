package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;

import lombok.Data;

public class OrderByMonthDTO {
    private Integer thang;
    private Integer nam;
    private Long soDon;
    public OrderByMonthDTO(Integer thang, Integer nam, Long soDon) {
        this.thang = thang;
        this.nam = nam;
        this.soDon = soDon;
    }

    public Integer getThang() {
        return thang;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }

    public Integer getNam() {
        return nam;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }

    public Long getSoDon() {
        return soDon;
    }

    public void setSoDon(Long soDon) {
        this.soDon = soDon;
    }
}
