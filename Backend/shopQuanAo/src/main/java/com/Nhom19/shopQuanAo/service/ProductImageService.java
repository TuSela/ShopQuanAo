package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.Controller.Admin.CreationProductImageRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ImageRequest;
import com.Nhom19.shopQuanAo.entity.ProductColors;
import com.Nhom19.shopQuanAo.entity.ProductImages;
import com.Nhom19.shopQuanAo.entity.Products;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.repository.ProductColorRepo;
import com.Nhom19.shopQuanAo.repository.ProductImagesRepo;
import com.Nhom19.shopQuanAo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductImageService {
    @Autowired
    private ProductImagesRepo productImagesRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private ProductColorRepo productColorRepo;
//    @PreAuthorize("hasAuthority('PRODUCT_MANAGE')")
    public Boolean createProductImageRequest(CreationProductImageRequest request){
        Products products = productRepo.findById(request.getMaSp()).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        ProductColors colorsX = productColorRepo.findById(request.getMaSp()).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_COLOR_NOT_FOUND));
        request.getUrlImages().forEach(imageRequest -> {
        ProductImages productImages2 = new ProductImages();
        productImages2.setUrlImage(imageRequest.getUrl());
        productImages2.setProducts(products);
        productImages2.setProductColor(colorsX);
        productImages2.setDaiDien(imageRequest.getDaiDien());
        productImages2.setDaiDienMau(imageRequest.getDaiDienMau());
        productImagesRepo.save(productImages2);
    });
        return true;
    }
}
