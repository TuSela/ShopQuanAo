package com.Nhom19.shopQuanAo.DTO.Response.Customer;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MyCommentResponse {
        private Integer maDanhGia;
        private Integer maDonHang;

        private Integer maSp;
        private String tenSanPham;

        private Integer maBienThe;
        private String mau;
        private String size;
        private String anh;

        private BigDecimal diemDanhGia;
        private String noiDung;
        private String trangThai;
        private LocalDateTime ngayTao;
    }


