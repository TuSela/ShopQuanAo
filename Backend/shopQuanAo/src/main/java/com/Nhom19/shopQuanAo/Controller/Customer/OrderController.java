package com.Nhom19.shopQuanAo.Controller.Customer;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.OrderRequest.CreatOrderRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.OrderResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart.CreatCartResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder.CreatOrderResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder.OrderResponseDTO;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.OrderDetailResponse;
import com.Nhom19.shopQuanAo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/list")
    public List<OrderResponse> getALLOrders() {
        return orderService.getAllOrders();
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderDetail(id));
    }
    @GetMapping("/payments")
    public ApiResponse<CreatOrderResponse> getPayments() {
        ApiResponse<CreatOrderResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(orderService.PaymentOrder());
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
    @PutMapping("/delivering/{id}")
    public ApiResponse<Boolean> DeliveringOrder(@PathVariable Integer id) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.DangGiaoOrder(id));
        return apiResponse;
    }
    @PutMapping("/delivered/{id}")
    public ApiResponse<Boolean> DeliveredOrder(@PathVariable Integer id) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.DaGiaoOrder(id));
        return apiResponse;
    }
}


