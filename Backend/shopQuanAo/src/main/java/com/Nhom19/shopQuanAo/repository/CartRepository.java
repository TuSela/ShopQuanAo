package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.Cart;
import com.Nhom19.shopQuanAo.entity.Orders;
import com.Nhom19.shopQuanAo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("""
       SELECT DISTINCT o FROM Orders o
       LEFT JOIN FETCH o.items oi
       LEFT JOIN FETCH oi.productVariants v
       LEFT JOIN FETCH v.products p
       """)
    List<Cart> findAllCartWithItems();

    boolean existsByUsers(Users user);



    Optional<Cart> findByUsers(Users users);

}
