package com.Nhom19.shopQuanAo.repository;

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
//    Products getBytinhTrang(String tinhTrang);
//@Query(value =
//        "SELECT p.ma_sp AS maSp, p.ten_sp AS tenSp, SUM(oi.so_luong_dat) AS tongSoLuongBan " +
//                "FROM order_items oi " +
//                "JOIN orders o ON oi.ma_ddh = o.ma_ddh " +
//                "JOIN product_variants pv ON oi.ma_bien_the = pv.ma_bien_the " +
//                "JOIN products p ON pv.ma_sp = p.ma_sp " +
//                "WHERE o.ngay_thanh_toan BETWEEN :start AND :end " +
//                "GROUP BY p.ma_sp, p.ten_sp " +
//                "ORDER BY tongSoLuongBan DESC " +
//                "LIMIT 1",
//        nativeQuery = true)
//ProductBestSellerResponse findBestSellerByDate(
//        @Param("start") Date start,
//        @Param("end") Date end
//);
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
//List<ProductBestSellerResponse> getTopBestSeller(Pageable pageable);

}

