package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;

public class TopProductDTO {
    private Integer maSp;
    private String tenSp;
    private Long soLuongBan;

    // ⚠️ BẮT BUỘC constructor này phải tồn tại
    public TopProductDTO(Integer maSp,String tenSp, Long soLuongBan) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.soLuongBan = soLuongBan;
    }

    public void setSoLuongBan(Long soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public Integer getMaSp() {
        return maSp;
    }

    public void setMaSp(Integer maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public Long getSoLuongBan() {
        return soLuongBan;
    }
}
