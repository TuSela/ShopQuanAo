package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductVariantResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.SPNamResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetailResponse;
import com.Nhom19.shopQuanAo.entity.ProductImages;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.entity.Products;
import com.Nhom19.shopQuanAo.mapper.ProductMapper;
import com.Nhom19.shopQuanAo.repository.ProductImagesRepo;
import com.Nhom19.shopQuanAo.repository.ProductRepository;
import com.Nhom19.shopQuanAo.repository.ProductVariantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
    public List<ProductResponse> getProducts(){
       return   productRepository.findAll().stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    public List<ProductBestSellerResponse>getTopBestSeller(){
        return productRepository.getTopBestSeller();
    }
    public ProductDetailResponse getProductDetail(int id){
        Products products= productRepository.getById(id);
        Set<ProductVariants> productVariant= productVariantsRepo.findByProducts(products);

        ProductDetailResponse productDetailResponse= productMapper.toDTO2(products);
        productDetailResponse.setProductVariants(
                productVariant.stream()
                        .map(productVariants -> {
                            ProductVariantResponse productVariantResponse = new ProductVariantResponse();
                            ProductImages productImages = productImagesRepo.findByVariants(productVariants).orElse(null);

                            productVariantResponse.setMaBienThe(productVariants.getMaBienThe());
                            productVariantResponse.setSoluong(productVariants.getSoluong());
                            productVariantResponse.setTrangThai(productVariants.isTrangThai());
                            productVariantResponse.setMaKc(productVariants.getSizes());
                            productVariantResponse.setMaMs(productVariants.getColors());
                            if(productImages!=null){
                                productVariantResponse.setUrlImage(productImages.getUrlImage());
                                productVariantResponse.setDaiDien(productImages.getDaiDien());
                            }
                            else productVariantResponse.setUrlImage("Chưa có ảnh sp");

                            return productVariantResponse;
                        })
                        .collect(Collectors.toSet())
        );

        return productDetailResponse;
    }
    public List<SPNamResponse>getSpNam(){
        return productRepository.findTopSellingByDoiTuongNative("Nam",10);
    }

}
