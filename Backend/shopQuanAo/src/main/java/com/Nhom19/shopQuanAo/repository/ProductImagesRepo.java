package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.ProductColors;
import com.Nhom19.shopQuanAo.entity.ProductImages;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductImagesRepo extends JpaRepository<ProductImages, Integer> {
//    Optional<ProductImages> findByVariants(ProductVariants variants);
    @Query("""
    SELECT pi
    FROM ProductImages pi
    WHERE pi.products.maSp = :maSp
      AND pi.productColor.maMs = :maMs
    ORDER BY pi.daiDienMau DESC
""")
List<ProductImages> getImagesByProductAndColor(Integer maSp, Integer maMs);

Set<ProductImages> findByProducts(Products products);
    @Query("""
    SELECT pi
    FROM ProductImages pi
    WHERE pi.products IN :products
      AND pi.daiDien = true
""")
    List<ProductImages> findDaiDienByProducts(@Param("products") List<Products> products);

    List<ProductImages> findByProductsOrderByDaiDienDesc(Products products);

    @Query("""
        SELECT pi
        FROM ProductImages pi
        WHERE pi.products.maSp = :maSp
          AND pi.productColor.maMs = :maMs
        ORDER BY pi.daiDienMau DESC
    """)
    List<ProductImages> getImagesByProductAndColorOrderByDaiDien(
            @Param("maSp") Integer maSp,
            @Param("maMs") Integer maMs
    );
}
