package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.ProductImages;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImagesRepo extends JpaRepository<ProductImages, Integer> {
    Optional<ProductImages> findByVariants(ProductVariants variants);
}
