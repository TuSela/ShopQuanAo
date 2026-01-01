package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.OrderRequest.CreatOrderRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart.CreatCartResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder.*;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.AddressResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.OrderDetailResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.OrderItemResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.PaymentResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.entityCompositeKey.OrderItemId;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.AddressMapper;
import com.Nhom19.shopQuanAo.mapper.PaymentMapper;
import com.Nhom19.shopQuanAo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

//    @PreAuthorize("hasAuthority('ORDER_MANAGE')")
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
    //Hủy đơn hàng
    public Boolean CancelOrder(Integer orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        orders.setOrderStatus("Đã hủy");
        orderRepository.save(orders);
        return true;
    }

    @Autowired
    ProductImagesRepo productImagesRepo;
    public List<MyOrderResponse> getALLMyOrders(Integer maTk) {

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
                                item.setMaBienThe(pv.getMaBienThe());
                                item.setDaDanhGia(oi.getDaDanhGia());
                                List<ProductImages> images=
                                        productImagesRepo.getImagesByProductAndColor(oi.getProductVariants()
                                                .getProducts().getMaSp(), oi.getProductVariants().getColors().getMaMs());

                                String url = images.stream()
                                        .filter(img -> Boolean.TRUE.equals(img.getDaiDienMau()))
                                        .map(ProductImages::getUrlImage)
                                        .findFirst()
                                        .orElse(images.isEmpty() ? null : images.get(0).getUrlImage());

                                item.setAnh(url);

                                return item;
                            })
                            .toList();

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

    // xem chi tiết đơn hàng
    public OrderDetailResponse getOrderDetail(Integer maDdh) {

        Orders order = orderRepository.findById(maDdh)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

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

        response.setItems(
                orderItemRepo.findOrderItems(order.getMaDdh())

        );
        List<OrderItemResponse> items =
                orderItemRepo.findByOrders(order)
                        .stream()
                        .map(oi -> {

                            ProductVariants pv = oi.getProductVariants();
                            Products p = pv.getProducts();

                            OrderItemResponse item = new OrderItemResponse();
                            item.setTenSp(p.getTenSp());
                            item.setSoLuong(oi.getSoLuong());
                            item.setGia(oi.getTongTien());
                            item.setTenMau(pv.getColors().getTenMs());
                            item.setTenKc(pv.getSizes().getTenKc());
                            item.setTongTien(oi.getTongTien());
                            item.setMaDdh(order.getMaDdh());
                            List<ProductImages> images=
                                    productImagesRepo.getImagesByProductAndColor(oi.getProductVariants()
                                            .getProducts().getMaSp(), oi.getProductVariants().getColors().getMaMs());

                            String url = images.stream()
                                    .filter(img -> Boolean.TRUE.equals(img.getDaiDienMau()))
                                    .map(ProductImages::getUrlImage)
                                    .findFirst()
                                    .orElse(images.isEmpty() ? null : images.get(0).getUrlImage());

                            item.setUrlImage(url);

                            return item;
                        })
                        .toList();

        response.setItems(items);
        response.setTotalAmount(order.getTongTien());
        return response;
    }

    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentMethodRepo paymentMethodRepository;

    // lấy ra thông tin Thanh toán để tạo đơn
    public CreatOrderResponse PaymentOrder() {
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

    //Tạo đơn hàng
    @Transactional
    public CreatCartResponse createOrder(CreatOrderRequest request) {
        Orders order = new Orders();
        addresses addresses = addressRepository.findById(request.getMaDiaChi()).orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_EXISTED));
        PaymentMethods paymentMethods = paymentMethodRepo.findById(request.getMaPt()).orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));
        Cart cart= cartRepository.findById(request.getMaGh()).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

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
        Orders order1 = orderRepository.save(order);
        List<CartItems> cartItemsList = cart.getCartItems();

        cartItemsList.forEach(cartItem -> {
            OrderItems orderItems = new OrderItems();
            orderItems.setSoLuong(cartItem.getSoluong());
            orderItems.setProductVariants(cartItem.getProductVariants());
            orderItems.setTongTien(cartItem.getTongTien());
            OrderItemId id = new OrderItemId(
                    order1.getMaDdh(),
                    orderItems.getProductVariants().getMaBienThe()
            );
            orderItems.setOrders(order1);

            orderItems.setId(id);
            orderItemRepo.save(orderItems);
        });

        cartRepository.delete(cart);

        CreatCartResponse creatCartResponse = new CreatCartResponse();
        creatCartResponse.setToken(authenticationService.generateTokenUsers(users));
        creatCartResponse.setSuccess(Boolean.TRUE);
        return creatCartResponse;

    }

    //Lấy ra sản phẩm chưa đánh giá!
    public List<MyOrderResponse> getOrdersNotReviewed(Integer maTk) {
        List<Orders> orders = orderRepository.findCompletedOrdersNotReviewed(maTk);
        return orders.stream().map(order -> {
            MyOrderResponse res = new MyOrderResponse();
            res.setMaDonHang(order.getMaDdh());
            res.setNgayDat(order.getNgayThanhToan());
            res.setTongTien(order.getTongTien());
            res.setTrangThai(order.getOrderStatus());

            List<MyOrderItemResponse> items = order.getItems()
                    .stream()
                    .map(this::mapItem)
                    .toList();

            res.setItems(items);
            return res;

        }).toList();
    }

    private MyOrderItemResponse mapItem(OrderItems item) {

        ProductVariants pv = item.getProductVariants();
        Products p = pv.getProducts();

        MyOrderItemResponse res = new MyOrderItemResponse();
        res.setTenSanPham(p.getTenSp());
        res.setMaBienThe(pv.getMaBienThe());
        res.setSoLuong(item.getSoLuong());
        res.setGia(item.getTongTien());

        res.setMau(pv.getColors().getTenMs());
        res.setSize(pv.getSizes().getTenKc());

        // Lấy ảnh đại diện theo màu
        String anh = p.getImages().stream()
                .filter(img -> Boolean.TRUE.equals(img.getDaiDienMau()))
                .map(ProductImages::getUrlImage)
                .findFirst()
                .orElse(p.getImages().isEmpty() ? null : p.getImages().get(0).getUrlImage());

        res.setAnh(anh);

        return res;
    }

}

