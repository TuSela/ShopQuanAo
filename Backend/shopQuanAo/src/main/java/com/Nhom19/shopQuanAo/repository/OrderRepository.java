package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.Orders;
import com.Nhom19.shopQuanAo.entity.ProductColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
