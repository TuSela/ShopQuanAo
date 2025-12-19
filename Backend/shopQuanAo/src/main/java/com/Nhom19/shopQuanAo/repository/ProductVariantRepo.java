package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductVariantRepo extends JpaRepository<ProductVariants, Integer> {
    public Set<ProductVariants> findByProducts(Products products);

    @Query("""
    SELECT pv 
    FROM ProductVariants pv
    WHERE pv.products.maSp = :maSp
      AND pv.colors.maMs = :maMs
""")
    List<ProductVariants> getSizesByProductAndColor(Integer maSp, Integer maMs);

    @Query(value = """
    SELECT pv.*
    FROM product_variants pv
    WHERE pv.ma_sp = :maSp
      AND pv.ma_ms = :maMs
      AND pv.ma_kc = :maKc
""", nativeQuery = true)
    Optional<ProductVariants> findByProductAndColorAndSize(
            @Param("maSp") Integer maSp,
            @Param("maMs") Integer maMs,
            @Param("maKc") Integer maKc
    );

}
