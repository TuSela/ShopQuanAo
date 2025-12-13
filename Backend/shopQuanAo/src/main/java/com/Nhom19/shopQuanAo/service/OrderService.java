package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Response.MyOrderItemResponse;
import com.Nhom19.shopQuanAo.DTO.Response.MyOrderResponse;
import com.Nhom19.shopQuanAo.DTO.Response.OrderProductDTO;
import com.Nhom19.shopQuanAo.DTO.Response.OrderResponseDTO;
import com.Nhom19.shopQuanAo.entity.Orders;
import com.Nhom19.shopQuanAo.entity.ProductImages;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.entity.Products;
import com.Nhom19.shopQuanAo.repository.OrderItemRepo;
import com.Nhom19.shopQuanAo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

}
