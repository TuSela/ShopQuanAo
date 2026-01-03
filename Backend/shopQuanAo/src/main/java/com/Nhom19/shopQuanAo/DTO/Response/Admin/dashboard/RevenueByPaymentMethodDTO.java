package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;

import lombok.Data;

import java.math.BigDecimal;

public class RevenueByPaymentMethodDTO {
    private String tenPhuongThuc;
    private BigDecimal doanhThu;
    public RevenueByPaymentMethodDTO(String tenPhuongThuc, BigDecimal doanhThu) {
        this.tenPhuongThuc = tenPhuongThuc;
        this.doanhThu = doanhThu;
    }

    public String getTenPhuongThuc() {
        return tenPhuongThuc;
    }

    public void setTenPhuongThuc(String tenPhuongThuc) {
        this.tenPhuongThuc = tenPhuongThuc;
    }

    public BigDecimal getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(BigDecimal doanhThu) {
        this.doanhThu = doanhThu;
    }
}
