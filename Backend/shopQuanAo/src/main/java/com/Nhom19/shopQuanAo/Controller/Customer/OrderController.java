package com.Nhom19.shopQuanAo.Controller.Customer;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.CreateCartRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Customer.OrderRequest.CreatOrderRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart.CreatCartResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder.CreatOrderResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder.MyOrderResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder.OrderResponseDTO;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.OrderDetailResponse;
import com.Nhom19.shopQuanAo.service.JwtUtils;
import com.Nhom19.shopQuanAo.service.OrderService;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping
    public List<OrderResponseDTO> getOrders() {
        return orderService.getAllOrdersWithProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderDetail(id));
    }
    @GetMapping("/payments")
    public ApiResponse<CreatOrderResponse> getPayments() {
        ApiResponse<CreatOrderResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(orderService.Order());
        return apiResponse;
    }
    @PostMapping
    public ApiResponse<CreatCartResponse> CreateOrder(@RequestBody CreatOrderRequest request) {
        ApiResponse<CreatCartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.createOrder(request));
        return apiResponse;
    }
    @PutMapping("/cancel/{id}")
    public ApiResponse<Boolean> CancelOrder(@PathVariable Integer id) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.CancelOrder(id));
        return apiResponse;
    }
}


