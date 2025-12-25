package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.ColorRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductVariantResponse;
import com.Nhom19.shopQuanAo.entity.ProductImages;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.mapper.VariantMapper;
import com.Nhom19.shopQuanAo.repository.ProductImagesRepo;
import com.Nhom19.shopQuanAo.repository.ProductVariantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariantService {
    @Autowired
    private ProductVariantRepo productVariantRepo;
    @Autowired
    private ProductImagesRepo productImagesRepo;
    @Autowired
    private VariantMapper variantMapper;

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
}
