package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.TopProductDTO;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.OrderItemResponse;
import com.Nhom19.shopQuanAo.entity.OrderItems;
import com.Nhom19.shopQuanAo.entity.Orders;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.entityCompositeKey.OrderItemId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItems, OrderItemId> {

    List<OrderItems> findByOrders(Orders orders);

    @Query(value = """
    SELECT
        oi.ma_ddh            AS maDdh,
        p.ten_sp             AS tenSp,
        img.url_hinh_anh     AS urlImage,
        ms.ten_ms            AS tenMau,
        kc.ten_kc            AS tenKc,
        p.gia                AS gia,
        oi.so_luong_dat      AS soLuong,
        oi.tong_tien         AS tongTien
    FROM order_items oi
    JOIN product_variants pv ON oi.ma_bien_the = pv.ma_bien_the
    JOIN products p ON pv.ma_sp = p.ma_sp
    JOIN product_colors ms ON pv.ma_ms = ms.ma_ms
    JOIN product_sizes kc ON pv.ma_kc = kc.ma_kc
    LEFT JOIN product_images img
           ON img.ma_sp = p.ma_sp AND img.dai_dien = 1
    WHERE oi.ma_ddh = :maDdh
    """, nativeQuery = true)
    List<OrderItemResponse> findOrderItems(@Param("maDdh") Integer maDdh);

   Optional<OrderItems> findByOrdersAndProductVariants(Orders orders, ProductVariants productVariants);

    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.TopProductDTO(
        p.maSp,
        p.tenSp,
        SUM(oi.soLuong)
    )
    FROM OrderItems oi
    JOIN oi.orders o
    JOIN oi.productVariants pv
    JOIN pv.products p
    WHERE o.orderStatus = 'DA_GIAO'
    GROUP BY p.tenSp,p.maSp
    ORDER BY SUM(oi.soLuong) DESC
""")
    List<TopProductDTO> topSanPhamBanChay(Pageable pageable);
}
