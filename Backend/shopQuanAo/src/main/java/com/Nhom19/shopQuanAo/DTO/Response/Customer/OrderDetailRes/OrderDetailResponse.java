package com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDetailResponse {
    private Integer maDdh;
    private String orderStatus;
    private LocalDateTime ngayThanhToan;
    private LocalDateTime shippedAt;

    private AddressResponse address;
    private PaymentResponse payment;

    private List<OrderItemResponse> items;

    private BigDecimal totalAmount;
}
