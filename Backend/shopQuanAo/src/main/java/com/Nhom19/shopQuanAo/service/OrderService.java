package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.OrderRequest.CreatOrderRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart.CreatCartResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder.*;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.AddressResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.OrderDetailResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.PaymentResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.entityCompositeKey.CartItemId;
import com.Nhom19.shopQuanAo.entityCompositeKey.OrderItemId;
import com.Nhom19.shopQuanAo.mapper.AddressMapper;
import com.Nhom19.shopQuanAo.mapper.PaymentMapper;
import com.Nhom19.shopQuanAo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
                    order.getTongTien(),
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
            orderRes.setTongTien(order.getTongTien());
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

        response.setTotalAmount(order.getTongTien());

        return response;
    }
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentMethodRepo paymentMethodRepository;

    public CreatOrderResponse Order() {

        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepository.findBySdt(sdt);
        List<addresses> addressesList = addressRepository.findByUsers(users);
        CreatOrderResponse creatOrderResponse = new CreatOrderResponse();
        List<AddressResponse> addressResponseList = new ArrayList<>();
        addressesList.forEach(address -> {
            AddressResponse addressRes = addressMapper.ToDTO(address);
            addressResponseList.add(addressRes);
        });
        List<PaymentMethods> paymentMethodsList = paymentMethodRepository.findAll();
        List<PaymentResponse> paymentResponseList = new ArrayList<>();
        paymentMethodsList.forEach(paymentMethod -> {
            PaymentResponse paymentResponse = paymentMapper.toDTO(paymentMethod);
            paymentResponseList.add(paymentResponse);
        });

        creatOrderResponse.setAddress(addressResponseList);
        creatOrderResponse.setPayment(paymentResponseList);
        creatOrderResponse.setMyCartResponse(cartService.getAllMyCart());

        return creatOrderResponse;
    }
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    AuthenticationService authenticationService;
    @Transactional
    public CreatCartResponse createOrder(CreatOrderRequest request) {

        Orders order = new Orders();
        addresses addresses = addressRepository.findById(request.getMaDiaChi()).orElseThrow(() -> new RuntimeException("Address not found"));
        PaymentMethods paymentMethods = paymentMethodRepo.findById(request.getMaPt()).orElseThrow(() -> new RuntimeException("Payment method not found"));

        Cart cart= cartRepository.findById(request.getMaGh()).orElseThrow(() -> new RuntimeException("Cart not found"));

        order.setAddresses(addresses);
        order.setPaymentMethods(paymentMethods);
        order.setOrderStatus("Đang xử lý");
        order.setPaymentStatus("Thanh toán khi nhận hàng");
        if (order.getPaymentMethods().getMaPt()!= 1){
            order.setPaymentStatus("Đã thanh toán");
            order.setNgayThanhToan(LocalDateTime.now());
        }

        order.setNgayThanhToan(LocalDateTime.now());
        order.setTongTien(cart.getTongTien());
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepository.findBySdt(sdt);
        order.setUsers(users);
        orderRepository.save(order);

        List<CartItems> cartItemsList = cart.getCartItems();
        cartItemsList.forEach(cartItem -> {
            OrderItems orderItems = new OrderItems();
            orderItems.setSoLuong(cartItem.getSoluong());
            orderItems.setProductVariants(cartItem.getProductVariants());
            orderItems.setTongTien(cartItem.getTongTien());
            OrderItemId id = new OrderItemId(
                    cart.getMaGh(),
                    cartItem.getProductVariants().getMaBienThe()
            );
            orderItems.setId(id);
            orderRepository.save(order);
            cartItemRepo.deleteByCartAndProductVariants(cart,cartItem.getProductVariants());
        });

        cartRepository.delete(cart);

        CreatCartResponse creatCartResponse = new CreatCartResponse();
        creatCartResponse.setToken(authenticationService.generateToken(users));
        creatCartResponse.setSuccess(Boolean.TRUE);
        return creatCartResponse;

    }
}
