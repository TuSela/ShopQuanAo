package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.ProductImages;
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

    List<String> findUrlImageByProducts(Products products);

Set<ProductImages> findByProducts(Products products);
    @Query("""
    SELECT pi
    FROM ProductImages pi
    WHERE pi.products IN :products
      AND pi.daiDien = true
""")
    List<ProductImages> findDaiDienByProducts(@Param("products") List<Products> products);

    @Query("""
    SELECT pi.urlImage
    FROM ProductImages pi
    WHERE pi.products = :product
    ORDER BY pi.daiDien DESC
""")
    List<String> findImageUrlsByProduct(@Param("product") Products product);

    Optional<ProductImages> findFirstByProductsAndDaiDienTrue(Products products);



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

    @Query("""
    SELECT pi
    FROM ProductImages pi
    WHERE pi.products.maSp = :maSp
      AND pi.daiDien = true
    ORDER BY pi.daiDienMau DESC
""")
    Optional<ProductImages> getDaiDienByProducts(@Param("maSp") Integer maSp);
}
