package com.Nhom19.shopQuanAo.DTO.Response.Customer.Categories;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV.ChiTietLoaiResponse;

import java.util.List;

public record PageResponse<T>(
        String danhMuc,
        List<T> items,
        List<ChiTietLoaiResponse> chiTietLoai,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
