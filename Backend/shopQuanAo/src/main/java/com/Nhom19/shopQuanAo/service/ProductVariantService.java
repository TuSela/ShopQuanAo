package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreateVariantRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreationProductRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Admin.UpdateVariantRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ColorRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ImageRequest;
import com.Nhom19.shopQuanAo.DTO.Request.SizeRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductVariantResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.VariantMapper;
import com.Nhom19.shopQuanAo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductVariantService {
    @Autowired
    private ProductVariantRepo productVariantRepo;
    @Autowired
    private ProductImagesRepo productImagesRepo;
    @Autowired
    private VariantMapper variantMapper;
    @Autowired
    private ProductRepository productRepo;
    public List<ProductVariantResponse> getVariantsByMaSp(Integer maSp) {

        List<ProductVariants> variants =
                productVariantRepo.findByMaSp(maSp);

        return variants.stream().map(pv -> {

            ProductVariantResponse res = new ProductVariantResponse();
//            variantMapper.toDTO(pv);

            res.setMaBienThe(pv.getMaBienThe());
            res.setSoluong(pv.getSoluong());
            res.setTrangThai(pv.isTrangThai());

            // entity
            res.setProduct(pv.getProducts());
            res.setColors(pv.getColors());
            res.setSizes(pv.getSizes());

            // ảnh đại diện theo màu (nếu có)
            List<ProductImages> images = productImagesRepo.getImagesByProductAndColorOrderByDaiDien(pv.getProducts().getMaSp(),pv.getColors().getMaMs());
                String url = images.stream()
                        .filter(img -> Boolean.TRUE.equals(img.getDaiDienMau()))
                        .map(ProductImages::getUrlImage)
                        .findFirst()
                        .orElse(images.isEmpty() ? null : images.get(0).getUrlImage());
            res.setUrlImage(url);

            return res;
        }).toList();
    }
//    public List<ColorRequest> getColorByMaSp(Integer maSp) {
//        List<ProductVariants> productVariants = productVariantRepo.findByMaSp(maSp);
//
//    }
    public Boolean UpdateVariant(UpdateVariantRequest request,Integer maBienThe) {
        ProductVariants productVariants = productVariantRepo.findById(maBienThe).orElseThrow(()->new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED));
        productVariants.setSoluong(request.getSoluong());
        productVariants.setTrangThai(request.isTrangThai());
        productVariantRepo.save(productVariants);
        return true;
    }
    @Transactional
    public Boolean DeleteVariant(Integer maBienThe) {
        productVariantRepo.deleteById(maBienThe);
        return true;
    }
    @Autowired
    private ProductColorRepo productColorRepo;
    @Autowired
    private ProductSizeRepo productSizeRepo;

    @Transactional
    public List<ProductVariantResponse> createVariants(CreateVariantRequest request,Integer maSp) {
        Products products =productRepo.findById(maSp).orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        List<ProductVariantResponse> productVariantResponses = new ArrayList<>();
        List<ColorRequest> colors = request.getColors();
        colors.forEach(color -> {
            ProductColors colorsX = productColorRepo.findById(color.getMaMs()).orElseThrow(() -> new RuntimeException("Không tìm thấy màu"));

            List<ImageRequest> imageRequests = color.getUrlImages();
            imageRequests.forEach(imageRequest -> {
                ProductImages productImages2 = new ProductImages();
                productImages2.setUrlImage(imageRequest.getUrl());
                productImages2.setProducts(products);
                productImages2.setProductColor(colorsX);
                productImages2.setDaiDien(imageRequest.getDaiDien());
                productImages2.setDaiDienMau(imageRequest.getDaiDienMau());
                productImagesRepo.save(productImages2);
            });

            List<SizeRequest> size = color.getSizes();

            size.forEach(sizeRequest -> {
                ProductSizes sizes = productSizeRepo.findById(sizeRequest.getMaKc()).orElseThrow(() -> new RuntimeException("Không tìm thấy size"));
                ProductVariants productVariants = new ProductVariants();
                productVariants.setProducts(products);
                productVariants.setColors(colorsX);
                productVariants.setSizes(sizes);
                productVariants.setSoluong(sizeRequest.getSoluong());
                ProductVariants productVariants1 = productVariantRepo.save(productVariants);
                ProductVariantResponse productVariantResponse = variantMapper.toDTO(productVariants1);
                productVariantResponses.add(productVariantResponse);
            });
        });
        return productVariantResponses;
    }

    //    @PreAuthorize("hasAuthority('PRODUCT_MANAGE')")
    @Transactional
    public void enableProductVariant(int maBienThe, int maSp) {
        var variant = productVariantRepo.findById(maBienThe).orElseThrow(
                ()->new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED));

        if (!variant.getProducts().getMaSp().equals(maSp)) {
            throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED);
        }

        variant.setTrangThai(true);
        productVariantRepo.save(variant);
    }

    //    @PreAuthorize("hasAuthority('PRODUCT_MANAGE')")
    @Transactional
    public void disableProductVariant(int maBienThe, int maSp) {
        var variant = productVariantRepo.findById(maBienThe).orElseThrow(
                ()->new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED));

        if (!variant.getProducts().getMaSp().equals(maSp)) {
            throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED);
        }

        variant.setTrangThai(false);
        productVariantRepo.save(variant);
    }
}
