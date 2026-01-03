package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;

import java.math.BigDecimal;

public class RevenueByMonthDTO {
    private Integer thang;
    private Integer nam;
    private BigDecimal doanhThu;
    public RevenueByMonthDTO(Integer thang, Integer nam, BigDecimal  doanhThu) {
        this.thang = thang;
        this.nam = nam;
        this.doanhThu = doanhThu;
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

    public BigDecimal getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(BigDecimal doanhThu) {
        this.doanhThu = doanhThu;
    }
}
