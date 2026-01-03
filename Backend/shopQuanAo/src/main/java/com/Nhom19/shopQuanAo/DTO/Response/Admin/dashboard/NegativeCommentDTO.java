package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;

import lombok.Data;

public class NegativeCommentDTO {
    private String noiDung;
    private String nguoiDung;
    private String sanPham;
    private String trangThai;
    public NegativeCommentDTO(
            String noiDung,
            String nguoiDung,
            String sanPham,
            String trangThai
    ) {
        this.noiDung = noiDung;
        this.nguoiDung = nguoiDung;
        this.sanPham = sanPham;
        this.trangThai = trangThai;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(String nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public String getSanPham() {
        return sanPham;
    }

    public void setSanPham(String sanPham) {
        this.sanPham = sanPham;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
