package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CommentRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCommentResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.CommentVariantResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.ProductCommentResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.UserCommentResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.CommentMapper;
import com.Nhom19.shopQuanAo.mapper.UserMapper;
import com.Nhom19.shopQuanAo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
//    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Boolean CreateComment(CommentRequest request,Integer Id){

        Users users = userRepository.findById(Id).orElseThrow(()->new AppException(USER_NOT_EXISTED));
        Orders orders = orderRepository.findById(request.getMaDdh()).orElseThrow(()->new AppException(ORDER_NOT_FOUND));
        if(!orders.getOrderStatus().equals("DA_GIAO")){
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

        // kiem tra noi dung binh luận tự động
        boolean isApproved = isApproved(request.getNoiDung());
        productComments.setTrangThai(isApproved ? "SHOW" : "HIDDEN");
        productCommentRepo.save(productComments);
        OrderItems orderItems = orderItemRepo.findByOrdersAndProductVariants(orders,productVariants).orElseThrow(()->new AppException(ErrorCode.ORDER_NOT_FOUND));
        orderItems.setDaDanhGia(true);
        orderItemRepo.save(orderItems);
        return true;
    }
//    @PreAuthorize("hasAuthority('SCOPE_USER')")
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
    @Autowired
    private UserMapper userMapper;
    @PreAuthorize("hasAuthority('COMMENT_MANAGE')")
    public List<ProductCommentResponse> mapToProductComment() {

        return productCommentRepo.findAllByOrderByMaBlDesc()
                .stream()
                .map(pc -> {

                    // Map user
                    Users users = pc.getUsers();
                    UserCommentResponse userResponse =
                            userMapper.toUserCommentResponse(users);

                    // Map product variant
                    OrderItems orderItems = orderItemRepo
                            .findByOrdersAndProductVariants(
                                    pc.getOrders(),
                                    pc.getProductVariants()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException("Sản phẩm trong đơn hàng không tồn tại"));

                    CommentVariantResponse variantResponse =
                            new CommentVariantResponse();

                    variantResponse.setMaBienThe(
                            pc.getProductVariants().getMaBienThe());
                    variantResponse.setSoLuongDat(
                            orderItems.getSoLuong());
                    variantResponse.setTenMs(
                            pc.getProductVariants().getColors().getTenMs());
                    variantResponse.setTenKc(
                            pc.getProductVariants().getSizes().getTenKc());

                    // Map comment
                    ProductCommentResponse res =
                            new ProductCommentResponse();

                    res.setMaBl(pc.getMaBl());
                    res.setNgayTao(pc.getNgayTao());
                    res.setNoiDung(pc.getNoiDung());
                    res.setDiemDanhGia(pc.getDiemDanhGia());
                    res.setUsers(userResponse);
                    res.setProductVariants(variantResponse);
                    res.setTrangThai(pc.getTrangThai());
                    res.setMaSp(pc.getProducts().getMaSp());
                    return res;
                })
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasAuthority('COMMENT_MANAGE')")
    @Transactional
    public void disableComment(Integer maBl) {
        ProductComments productComments = productCommentRepo.findById(maBl)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        productComments.setTrangThai("HIDDEN");
        productCommentRepo.save(productComments);
    }

    @PreAuthorize("hasAuthority('COMMENT_MANAGE')")
    public Boolean enableComment(Integer maBl) {
        ProductComments productComments = productCommentRepo.findById(maBl)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        productComments.setTrangThai("SHOW");
        productCommentRepo.save(productComments);
        return true;
    }
    public void deleteComment(Integer maBl) {
        productCommentRepo.deleteById(maBl);
    }

    private static final List<String> BANNED_WORDS = List.of(
            "đồ ngu", "vô học", "lừa đảo", "rác", "chửi", "dm", "vcl",
            "lừa đảo", "lừa gạt", "làm ăn gian dối",
            "treo đầu dê bán thịt chó",
            "bán hàng giả", "hàng fake",
            "lừa tiền", "ăn chặn",
            "lừa khách", "làm ăn bẩn","packy","namky","36","hai ngón","2 ngón"
    );

    public boolean isApproved(String content) {
        if (content == null || content.trim().isEmpty()) {
            return false;
        }

        // Quá ngắn hoặc quá dài
        if (content.length() < 2 || content.length() > 500) {
            return false;
        }

        String lower = content.toLowerCase();

        // Chứa từ cấm
        for (String word : BANNED_WORDS) {
            if (lower.contains(word)) {
                return false;
            }
        }

        // Spam ký tự (!!!!!, ?????)
        if (lower.matches(".*([!?\\.])\\1{3,}.*")) {
            return false;
        }
        return true;
    }
}

