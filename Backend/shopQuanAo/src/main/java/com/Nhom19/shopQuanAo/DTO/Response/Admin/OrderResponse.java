package com.Nhom19.shopQuanAo.DTO.Response.Admin;

import com.Nhom19.shopQuanAo.entity.PaymentMethods;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.entity.addresses;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class OrderResponse {
    private int maDdh;
    @JsonIgnore
    private PaymentMethods paymentMethods;
    public Integer getMaPt() {
        return paymentMethods != null? paymentMethods.getMaPt() : null;
    }
    public String getTenPt() {
        return paymentMethods != null? paymentMethods.getTenPt() : null;
    }
    @JsonIgnore
    private Users users;
    public Integer getMaTk (){
        return users != null ? users.getMaTk() : null;}

    public String getHoten(){
        return users != null ? users.getHoten() : null;}

    private LocalDateTime ngayThanhToan;
    private LocalDateTime shippedAt;
    private String paymentStatus;
    private String orderStatus;

    @JsonIgnore
    private addresses addresses;

    public Integer getMaDiaChi (){
        return addresses != null ? addresses.getMaDiaChi() : null;}

    private BigDecimal tongTien;
}
