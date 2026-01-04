package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.LowStockProductDTO;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepo extends JpaRepository<ProductVariants, Integer> {
    @Query("""
    SELECT pv
    FROM ProductVariants pv
    JOIN pv.products p
    JOIN p.images pi
    WHERE p = :products
    ORDER BY pi.daiDien DESC
""")
    List<ProductVariants> findByProductOrderByImageDaiDien(@Param("products") Products products);

    List<ProductVariants> findByProducts(Products products);

    @Query("""
    SELECT pv
    FROM ProductVariants pv
    WHERE pv.products.maSp = :maSp
      AND pv.colors.maMs = :maMs
      AND pv.trangThai = true
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

    @Query("""
        SELECT pv
        FROM ProductVariants pv
        JOIN FETCH pv.products p
        JOIN FETCH pv.colors c
        JOIN FETCH pv.sizes s
        WHERE p.maSp = :maSp
    """)
    List<ProductVariants> findByMaSp(@Param("maSp") Integer maSp);

    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.LowStockProductDTO(
        p.maSp,
        p.tenSp,
        s.tenKc,
        c.tenMs,
        pv.soluong
    )
    FROM ProductVariants pv
    JOIN pv.products p
    JOIN pv.sizes s
    JOIN pv.colors c
    WHERE pv.soluong <= :nguong
""")
    List<LowStockProductDTO> sanPhamTonKhoThap(@Param("nguong") int nguong);

    @Query("""
    SELECT pv
    FROM ProductVariants pv
    JOIN FETCH pv.colors
    JOIN FETCH pv.sizes
    WHERE pv.products = :product
      AND pv.trangThai = true
""")
    List<ProductVariants> findVariantsForDetail(@Param("product") Products product);

}
