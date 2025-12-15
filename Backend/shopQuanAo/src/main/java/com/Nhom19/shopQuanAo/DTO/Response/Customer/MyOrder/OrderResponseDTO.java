package com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder;


import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
        Integer maDdh,
        String tenKhachHang,
        BigDecimal tongTien,
        List<OrderProductDTO> sanPham
) {}