package com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart.MyCartResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.AddressResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.PaymentResponse;
import lombok.Data;

import java.util.List;

@Data
public class CreatOrderResponse {

    private List<AddressResponse> address;
    private List<PaymentResponse> payment;
    private MyCartResponse myCartResponse;

}
