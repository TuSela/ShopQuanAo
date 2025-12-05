package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.SPNamResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.entity.Products;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

//@Query("""
//    SELECT new com.Nhom19.shopQuanAo.DTO.Response.ProductBestSellerResponse(
//        p.MaSp,
//        p.tenSp,
//        p.gia,
//        (
//            SELECT MAX(pi.urlImage)
//            FROM ProductImages pi
//            WHERE pi.variants = pv
//              AND pi.DaiDien = true
//        ),
//        SUM(oi.quantity)
//    )
//    FROM Products p
//    JOIN p.ProductVariants pv
//    JOIN pv.ordersItems oi
//    GROUP BY p.MaSp, p.tenSp, p.gia, pv.MaBienThe
//    ORDER BY SUM(oi.quantity) DESC
//    """)
//public List<ProductBestSellerResponse> getTopBestSeller(Pageable pageable);
//@Query(value = """
//SELECT TOP (10)
//    p.ma_sp AS maSp,
//    p.ten_sp AS tenSp,
//    p.gia AS gia,
//    (
//        SELECT TOP 1 pi.url_hinh_anh
//        FROM product_images pi
//        WHERE pi.ma_bien_the = pv.ma_bien_the
//          AND pi.dai_dien = 1
//    ) AS urlImage
//FROM products p
//JOIN product_variants pv ON pv.ma_sp = p.ma_sp
//JOIN order_items oi ON oi.ma_bien_the = pv.ma_bien_the
//GROUP BY p.ma_sp, p.ten_sp, p.gia
//ORDER BY SUM(oi.so_luong_dat) DESC
//    """,
//        nativeQuery = true)
//List<ProductBestSellerResponse> getTopBestSeller();
@Query(value = """
SELECT TOP (10)
    p.ma_sp AS maSp,
    p.ten_sp AS tenSp,
    p.gia AS gia,
    pi.url_hinh_anh AS urlImage
FROM products p
JOIN product_variants pv ON pv.ma_sp = p.ma_sp
JOIN order_items oi ON oi.ma_bien_the = pv.ma_bien_the
OUTER APPLY (
    SELECT TOP 1 url_hinh_anh
    FROM product_images
    WHERE ma_bien_the = pv.ma_bien_the AND dai_dien = 1
) pi
GROUP BY p.ma_sp, p.ten_sp, p.gia, pi.url_hinh_anh
ORDER BY SUM(oi.so_luong_dat) DESC
""", nativeQuery = true)
List<ProductBestSellerResponse> getTopBestSeller();

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
}

