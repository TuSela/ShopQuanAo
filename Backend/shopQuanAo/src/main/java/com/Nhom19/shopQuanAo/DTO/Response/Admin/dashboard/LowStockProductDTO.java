package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;


public class LowStockProductDTO {
    private Integer maSp;
    private String tenSanPham;
    private String size;
    private String mau;
    private Integer tonKho;
    public LowStockProductDTO(
            Integer maSp,
            String tenSanPham,
            String size,
            String mau,
            Integer tonKho
    ) {
        this.maSp = maSp;
        this.tenSanPham = tenSanPham;
        this.size = size;
        this.mau = mau;
        this.tonKho = tonKho;
    }

    public Integer getMaSp() {
        return maSp;
    }

    public void setMaSp(Integer maSp) {
        this.maSp = maSp;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public Integer getTonKho() {
        return tonKho;
    }

    public void setTonKho(Integer tonKho) {
        this.tonKho = tonKho;
    }
}
