package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CommentRequest;
import com.Nhom19.shopQuanAo.entity.Orders;
import com.Nhom19.shopQuanAo.entity.ProductComments;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.mapper.CommentMapper;
import com.Nhom19.shopQuanAo.repository.OrderRepository;
import com.Nhom19.shopQuanAo.repository.ProductCommentRepo;
import com.Nhom19.shopQuanAo.repository.ProductVariantRepo;
import com.Nhom19.shopQuanAo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    OrderRepository orderRepository;
    public Boolean CreateComment(CommentRequest request,Integer Id){

        Users users = userRepository.findById(Id).orElseThrow(()->new RuntimeException("User not found"));
        Orders orders = orderRepository.findById(request.getMaDdh()).orElseThrow(()->new RuntimeException("Order not found"));
        if(!orders.getOrderStatus().equals("Hoàn thành")){
            throw new RuntimeException("Đơn hàng chưa hoàn thành!");
        }
        ProductVariants productVariants = productVariantRepo.findById(request.getMaBienThe()).orElseThrow(()-> new RuntimeException("không tìm thấy biến thể"));
        if(productCommentRepo.existsByUsersAndOrdersAndProductVariants(users,orders,productVariants)){
            throw new RuntimeException("Đơn hàng đã được đánh giá");
        }

        ProductComments productComments = commentMapper.ToProductComments(request);
        productComments.setProducts(productVariants.getProducts());
        productComments.setNgayTao(LocalDateTime.now());
        productComments.setUsers(users);
        productComments.setProductVariants(productVariants);
        productComments.setOrders(orders);
        productComments.setTrangThai("Show");
        productCommentRepo.save(productComments);

        return true;
    }


}
