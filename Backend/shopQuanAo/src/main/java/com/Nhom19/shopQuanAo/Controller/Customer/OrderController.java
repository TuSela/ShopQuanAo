package com.Nhom19.shopQuanAo.Controller.Customer;

import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder.CreatOrderResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder.OrderResponseDTO;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.OrderDetailResponse;
import com.Nhom19.shopQuanAo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
