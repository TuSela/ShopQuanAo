package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.OrderItems;
import com.Nhom19.shopQuanAo.entity.Orders;
import com.Nhom19.shopQuanAo.entityCompositeKey.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepo extends JpaRepository<OrderItems, OrderItemId> {

    List<OrderItems> findByOrders(Orders orders);
}
