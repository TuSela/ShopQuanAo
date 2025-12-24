package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.Orders;
import com.Nhom19.shopQuanAo.entity.ProductColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
          AND o.orderStatus = 'Hoàn thành'
          AND NOT EXISTS (
              SELECT 1
              FROM ProductComments pc
              WHERE pc.orders.maDdh = o.maDdh
          )
        ORDER BY o.ngayThanhToan DESC
    """)
    List<Orders> findCompletedOrdersNotReviewed(@Param("maTk") Integer maTk);
}

