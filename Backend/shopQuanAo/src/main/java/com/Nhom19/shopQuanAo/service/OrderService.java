package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Response.MyOrder.MyOrderItemResponse;
import com.Nhom19.shopQuanAo.DTO.Response.MyOrder.MyOrderResponse;
import com.Nhom19.shopQuanAo.DTO.Response.MyOrder.OrderProductDTO;
import com.Nhom19.shopQuanAo.DTO.Response.MyOrder.OrderResponseDTO;
import com.Nhom19.shopQuanAo.DTO.Response.OrderDetailRes.AddressResponse;
import com.Nhom19.shopQuanAo.DTO.Response.OrderDetailRes.OrderDetailResponse;
import com.Nhom19.shopQuanAo.DTO.Response.OrderDetailRes.PaymentResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.mapper.AddressMapper;
import com.Nhom19.shopQuanAo.mapper.PaymentMapper;
import com.Nhom19.shopQuanAo.repository.AddressRepository;
import com.Nhom19.shopQuanAo.repository.OrderItemRepo;
import com.Nhom19.shopQuanAo.repository.OrderRepository;
import com.Nhom19.shopQuanAo.repository.PaymentMethodRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepo orderItemRepo;
    public List<OrderResponseDTO> getAllOrdersWithProducts() {

        List<Orders> orders = orderRepository.findAllOrdersWithItems();

        return orders.stream().map(order -> {
            List<OrderProductDTO> productDTOS = order.getItems().stream().map(item ->
                    new OrderProductDTO(
                            item.getProductVariants().getMaBienThe(),
                            item.getProductVariants().getProducts().getTenSp(),
                            item.getSoLuong(),
                            item.getTongTien().doubleValue()
                    )
            ).toList();

            return new OrderResponseDTO(
                    order.getMaDdh(),
                    order.getUsers().getHoten(),
                    (double) order.getTongTien(),
                    productDTOS
            );
        }).toList();
    }
    public List<MyOrderResponse> getMyOrders(Integer maTk) {

        List<Orders> ordersList =
                orderRepository.findByUsers_MaTkOrderByNgayThanhToanDesc(maTk);

        return ordersList.stream().map(order -> {

            MyOrderResponse orderRes = new MyOrderResponse();
            orderRes.setMaDonHang(order.getMaDdh());
            orderRes.setNgayDat(order.getNgayThanhToan());
            orderRes.setTongTien(BigDecimal.valueOf(order.getTongTien()));
            orderRes.setTrangThai(order.getOrderStatus());

            List<MyOrderItemResponse> items =
                    orderItemRepo.findByOrders(order)
                            .stream()
                            .map(oi -> {

                                ProductVariants pv = oi.getProductVariants();
                                Products p = pv.getProducts();

                                MyOrderItemResponse item = new MyOrderItemResponse();
                                item.setTenSanPham(p.getTenSp());
                                item.setSoLuong(oi.getSoLuong());
                                item.setGia(oi.getTongTien());

                                item.setMau(pv.getColors().getTenMs());
                                item.setSize(pv.getSizes().getTenKc());

                                // ảnh đại diện
                                item.setAnh(
                                        p.getImages()
                                                .stream()
                                                .filter(ProductImages::getDaiDien)
                                                .findFirst()
                                                .map(ProductImages::getUrlImage)
                                                .orElse(null)
                                );

                                return item;
                            }).toList();

            orderRes.setItems(items);
            return orderRes;

        }).toList();
    }
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private PaymentMethodRepo paymentMethodRepo;
    @Autowired
    private PaymentMapper paymentMapper;
    public OrderDetailResponse getOrderDetail(Integer maDdh) {

        Orders order = orderRepository.findById(maDdh)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderDetailResponse response = new OrderDetailResponse();

        response.setMaDdh(order.getMaDdh());
        response.setOrderStatus(order.getOrderStatus());
        response.setNgayThanhToan(order.getNgayThanhToan());
        response.setShippedAt(order.getShippedAt());

        // Address
        Optional<addresses> addresses = addressRepository.findById(order.getAddresses().getMaDiaChi());
        AddressResponse addressRes = addressMapper.ToDTO(addresses.orElse(null));
        response.setAddress(addressRes);

        // Payment
        Optional<PaymentMethods> paymentMethods= paymentMethodRepo.findById(order.getPaymentMethods().getMaPt());
        PaymentResponse paymentResponse = paymentMapper.toDTO(paymentMethods.orElse(null));
        response.setPayment(paymentResponse);

        // Items

        response.setItems(orderItemRepo.findOrderItems(order.getMaDdh()));

        response.setTotalAmount(BigDecimal.valueOf(order.getTongTien()));

        return response;
    }

}
