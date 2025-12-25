package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CommentRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCommentResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.CommentMapper;
import com.Nhom19.shopQuanAo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.Nhom19.shopQuanAo.exception.ErrorCode.*;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ProductCommentRepo productCommentRepo;
    @Autowired
    private UserRepository  userRepository;
    @Autowired
    private ProductVariantRepo productVariantRepo;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepo orderItemRepo;
    public Boolean CreateComment(CommentRequest request,Integer Id){

        Users users = userRepository.findById(Id).orElseThrow(()->new AppException(USER_NOT_EXISTED));
        Orders orders = orderRepository.findById(request.getMaDdh()).orElseThrow(()->new AppException(ORDER_NOT_FOUND));
        if(!orders.getOrderStatus().equals("Đã giao")){
            throw new AppException(ORDER_NOT_COMPLETED);
        }
        ProductVariants productVariants = productVariantRepo.findById(request.getMaBienThe()).orElseThrow(()-> new AppException(PRODUCT_VARIANT_NOT_EXISTED));
        if(productCommentRepo.existsByUsersAndOrdersAndProductVariants(users,orders,productVariants)){
            throw new AppException(COMMENT_ALREADY_EXISTS);
        }
        ProductComments productComments = commentMapper.ToProductComments(request);
        productComments.setProducts(productVariants.getProducts());
        productComments.setNgayTao(LocalDateTime.now());
        productComments.setUsers(users);
        productComments.setProductVariants(productVariants);
        productComments.setOrders(orders);
        productComments.setTrangThai("Show");
        productCommentRepo.save(productComments);
        OrderItems orderItems = orderItemRepo.findByOrdersAndProductVariants(orders,productVariants).orElseThrow(()->new AppException(ErrorCode.ORDER_NOT_FOUND));
        orderItems.setDaDanhGia(true);
        orderItemRepo.save(orderItems);
        return true;
    }

    public List<MyCommentResponse> getMyComments(Integer maTk) {

        return productCommentRepo.findMyComments(maTk)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    private MyCommentResponse mapToResponse(ProductComments pc) {

        ProductVariants pv = pc.getProductVariants();
        Products p = pv.getProducts();

        MyCommentResponse res = new MyCommentResponse();

        res.setMaDanhGia(pc.getMaBl());
        res.setMaDonHang(pc.getOrders().getMaDdh());

        res.setMaSp(p.getMaSp());
        res.setTenSanPham(p.getTenSp());

        res.setMaBienThe(pv.getMaBienThe());
        res.setMau(pv.getColors().getTenMs());
        res.setSize(pv.getSizes().getTenKc());

        res.setNoiDung(pc.getNoiDung());
        res.setDiemDanhGia(pc.getDiemDanhGia());
        res.setTrangThai(pc.getTrangThai());
        res.setNgayTao(pc.getNgayTao());

        // ảnh theo màu
        String anh = p.getImages().stream()
                .filter(img -> Boolean.TRUE.equals(img.getDaiDienMau()))
                .map(ProductImages::getUrlImage)
                .findFirst()
                .orElse(p.getImages().isEmpty() ? null : p.getImages().get(0).getUrlImage());

        res.setAnh(anh);

        return res;
    }
}

