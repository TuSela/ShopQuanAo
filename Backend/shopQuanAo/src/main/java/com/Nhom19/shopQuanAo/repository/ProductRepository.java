package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.SPNamResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.entity.ProductImages;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import com.Nhom19.shopQuanAo.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
    List<Products> findAllByOrderByMaSpDesc();

    @Query("""
    SELECT p
    FROM Products p
    JOIN FETCH p.types
    WHERE p.maSp = :id
      AND p.trangThai = true
""")
    Optional<Products> findProductDetail(@Param("id") int id);


    @Query(
            value = """
        SELECT p.ma_sp AS maSp, p.ten_sp AS tenSp, p.gia AS gia, MAX(pi.url_hinh_anh) AS urlImage
        FROM products p
        JOIN product_types pt ON p.ma_loai = pt.ma_loai
        JOIN product_variants pv ON pv.ma_sp = p.ma_sp
        JOIN order_items oi ON oi.ma_bien_the = pv.ma_bien_the
        LEFT JOIN product_images pi ON pi.ma_bien_the = pv.ma_bien_the AND pi.dai_dien = 1
        WHERE pt.doi_tuong = :doiTuong
        GROUP BY p.ma_sp, p.ten_sp, p.gia
        ORDER BY SUM(oi.so_luong_dat) DESC
        OFFSET 0 ROWS
        FETCH NEXT :limit ROWS ONLY
        """,
            nativeQuery = true
    )
    List<SPNamResponse> findTopSellingByDoiTuongNative(@Param("doiTuong") String dt, @Param("limit") int limit);

        @Query(value = """
        SELECT p.ma_sp AS maSp, p.ten_sp AS tenSp, p.gia AS gia, 
               MAX(pi.url_hinh_anh) AS urlImage,p.danh_gia AS danhGia
        FROM products p
        LEFT JOIN product_variants pv ON pv.ma_sp = p.ma_sp
        LEFT JOIN product_images pi ON pi.ma_bien_the = pv.ma_bien_the AND pi.dai_dien = 1
        GROUP BY p.ma_sp, p.ten_sp, p.gia
        ORDER BY p.ma_sp
        OFFSET 0 ROWS
        FETCH NEXT 10 ROWS ONLY
        """, nativeQuery = true)
        List<ProductBestSellerResponse> findAnyTenProductsNative();

    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductBestSellerResponse(
        p.maSp,
        p.tenSp,
        p.gia,
        img.urlImage,
        p.danhGia
    )
    FROM OrderItems oi
    JOIN oi.productVariants pv
    JOIN pv.products p
    LEFT JOIN p.images img
    WHERE p.trangThai = true AND (img.daiDien = true OR img IS NULL)
    GROUP BY p.maSp, p.tenSp, p.gia, img.urlImage, p.danhGia
    ORDER BY SUM(oi.soLuong) DESC
""")
    List<ProductBestSellerResponse> findBestSellerProducts(Pageable pageable);

    @Query("""
    SELECT p
    FROM OrderItems oi
    JOIN oi.productVariants pv
    JOIN pv.products p
    GROUP BY p
    ORDER BY SUM(oi.soLuong) DESC
""")
    Page<Products> findBestSellerProducts3(Pageable pageable);
    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductBestSellerResponse(
        p.maSp,
        p.tenSp,
        p.gia,
        img.urlImage,
        p.danhGia
    )
    FROM Products p
    LEFT JOIN p.images img
    WHERE (img.daiDien = true OR img IS NULL)
      AND (p.maSp NOT IN :ids) AND p.trangThai = true
    ORDER BY function('RAND')
""")
    List<ProductBestSellerResponse> findRandomProductsExclude(
            @Param("ids") List<Integer> ids,
            Pageable pageable
    );
//    OR LOWER(p.chiTiet) LIKE LOWER(CONCAT('%', :keyword, '%'))
    @Query("""
        SELECT p FROM Products p
        WHERE LOWER(p.tenSp) LIKE LOWER(CONCAT('%', :keyword, '%')) AND p.trangThai = true
    """)
    Page<Products> searchByKeyword(@Param("keyword") String keyword,Pageable pageable);

    @Query("""
    SELECT p
    FROM Products p
    JOIN p.types pt
    WHERE pt.doiTuong = :doiTuong
""")
    List<Products> findByDoiTuong(
            @Param("doiTuong") String doiTuong,
            Pageable pageable
    );

    Page<Products> findByTypesIn(
            List<ProductTypes> types,
            Pageable pageable
    );
}

