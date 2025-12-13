package com.Nhom19.shopQuanAo.DTO.Response.MyOrder;

public record OrderProductDTO(
        Integer variantId,
        String tenSanPham,
        Integer soLuong,
        Double thanhTien
) {}