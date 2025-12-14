package com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder;


import java.util.List;

public record OrderResponseDTO(
        Integer maDdh,
        String tenKhachHang,
        Double tongTien,
        List<OrderProductDTO> sanPham
) {}