package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.UserResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductVariantResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.SPNamResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductCommentResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetailResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.mapper.ProductMapper;
import com.Nhom19.shopQuanAo.mapper.UserMapper;
import com.Nhom19.shopQuanAo.repository.ProductCommentRepo;
import com.Nhom19.shopQuanAo.repository.ProductImagesRepo;
import com.Nhom19.shopQuanAo.repository.ProductRepository;
import com.Nhom19.shopQuanAo.repository.ProductVariantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductVariantRepo productVariantsRepo;
    @Autowired
    private ProductImagesRepo productImagesRepo;
    @Autowired
    private ProductCommentRepo productCommentRepo;
    @Autowired
    UserMapper userMapper;
    public List<ProductResponse> getProducts(){
       return   productRepository.findAll().stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    public List<ProductBestSellerResponse>getTopBestSeller(){
        return productRepository.getProductsForHome();
    }
//    public List<ProductBestSellerResponse> getAllProducts(){
//        return productRepository.getSanPhamTheoMau();
//    }
    public ProductDetailResponse getProductDetail(int id) {
        Products products = productRepository.getById(id);
        Set<ProductVariants> productVariant = productVariantsRepo.findByProducts(products);

        ProductDetailResponse productDetailResponse = productMapper.toDTO2(products);

        // Lấy danh sách comment
        Set<ProductComments> productComments = productCommentRepo.getByMaBl(id);

        // Set số lượng đánh giá
        productDetailResponse.setSoLuongDanhGia(productComments.size());

        // Map comments
        productDetailResponse.setProductComments(
                productComments.stream()
                        .map(pc -> {
                            Users users = pc.getUsers();
                            UserResponse userResponse = userMapper.toUserResponse(users);

                            ProductCommentResponse res = new ProductCommentResponse();
                            res.setMaBl(pc.getMaBl());
                            res.setNgayTao(pc.getNgayTao());
                            res.setNoiDung(pc.getNoiDung());
                            res.setDiemDanhGia(pc.getDiemDanhGia());
                            res.setUsers(userResponse);

                            return res;
                        })
                        .collect(Collectors.toSet())
        );

        // Map variant
        productDetailResponse.setProductVariants(
                productVariant.stream()
                        .map(variant -> {
                            ProductVariantResponse res = new ProductVariantResponse();
                            ProductImages img = productImagesRepo.findByVariants(variant).orElse(null);

                            res.setMaBienThe(variant.getMaBienThe());
                            res.setSoluong(variant.getSoluong());
                            res.setTrangThai(variant.isTrangThai());
                            res.setMaKc(variant.getSizes());
                            res.setMaMs(variant.getColors());

                            if(img != null){
                                res.setUrlImage(img.getUrlImage());
                                res.setDaiDien(img.getDaiDien());
                            } else {
                                res.setUrlImage("Chưa có ảnh sp");
                            }

                            return res;
                        })
                        .collect(Collectors.toSet())
        );

        return productDetailResponse;
    }

    public List<SPNamResponse>getSpNam(){
        return productRepository.findTopSellingByDoiTuongNative("Nam",10);
    }
    public List<ProductBestSellerResponse> getProduct10(){
        return productRepository.findAnyTenProductsNative();
    }

}
