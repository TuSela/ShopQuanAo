package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.OrderByMonthDTO;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.OrderStatusStatisticDTO;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.RevenueByMonthDTO;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.RevenueByPaymentMethodDTO;
import com.Nhom19.shopQuanAo.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("""
       SELECT DISTINCT o FROM Orders o
       LEFT JOIN FETCH o.items oi
       LEFT JOIN FETCH oi.productVariants v
       LEFT JOIN FETCH v.products p
       """)
    List<Orders> findAllOrdersWithItems();
    List<Orders> findByUsers_MaTkOrderByNgayThanhToanDesc(Integer maTk);

    @Query("""
        SELECT o
        FROM Orders o
        WHERE o.users.maTk = :maTk
          AND o.orderStatus = 'Đã giao'
          AND NOT EXISTS (
              SELECT 1
              FROM ProductComments pc
              WHERE pc.orders.maDdh = o.maDdh
          )
        ORDER BY o.ngayThanhToan DESC
    """)
    List<Orders> findCompletedOrdersNotReviewed(@Param("maTk") Integer maTk);

    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.OrderStatusStatisticDTO(
        o.orderStatus,
        COUNT(o.id)
    )
    FROM Orders o
    GROUP BY o.orderStatus
""")
    List<OrderStatusStatisticDTO> countOrdersByStatus();

    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.OrderByMonthDTO(
        MONTH(o.shippedAt),
        YEAR(o.shippedAt),
        COUNT(o.maDdh)
    )
    FROM Orders o
    WHERE o.orderStatus = 'DA_GIAO'
      AND o.shippedAt IS NOT NULL
      AND (:fromDate IS NULL OR o.shippedAt >= :fromDate)
      AND (:toDate IS NULL OR o.shippedAt <= :toDate)
    GROUP BY YEAR(o.shippedAt), MONTH(o.shippedAt)
    ORDER BY YEAR(o.shippedAt), MONTH(o.shippedAt)
""")
    List<OrderByMonthDTO> countCompletedOrdersByMonth(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.RevenueByMonthDTO(
        MONTH(o.ngayThanhToan),
        YEAR(o.ngayThanhToan),
        COALESCE(SUM(o.tongTien), 0)
    )
    FROM Orders o
    WHERE o.orderStatus = 'DA_GIAO'
      AND o.paymentStatus = 'DA_THANH_TOAN'
      AND (:fromDate IS NULL OR o.ngayThanhToan >= :fromDate)
      AND (:toDate IS NULL OR o.ngayThanhToan <= :toDate)
    GROUP BY YEAR(o.ngayThanhToan), MONTH(o.ngayThanhToan)
    ORDER BY YEAR(o.ngayThanhToan), MONTH(o.ngayThanhToan)
""")
    List<RevenueByMonthDTO> doanhThuTheoThang(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );
    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.RevenueByPaymentMethodDTO(
        pm.tenPt,
        SUM(o.tongTien)
    )
    FROM Orders o
    JOIN o.paymentMethods pm
    WHERE o.orderStatus = 'DA_GIAO'
      AND o.paymentStatus = 'DA_THANH_TOAN'
      AND (:fromDate IS NULL OR o.ngayThanhToan >= :fromDate)
      AND (:toDate IS NULL OR o.ngayThanhToan <= :toDate)
    GROUP BY pm.tenPt
""")
    List<RevenueByPaymentMethodDTO> doanhThuTheoPhuongThuc(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );


}


