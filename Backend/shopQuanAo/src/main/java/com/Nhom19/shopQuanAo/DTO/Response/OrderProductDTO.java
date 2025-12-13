package com.Nhom19.shopQuanAo.DTO.Response;

public record OrderProductDTO(
        Integer variantId,
        String tenSanPham,
        Integer soLuong,
        Double thanhTien
) {}