package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.CartItems;
import com.Nhom19.shopQuanAo.entity.Cart;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItems, CartItems> {
    List<CartItems> findByCart(Cart cart);
    CartItems findByCartAndProductVariants(Cart cart, ProductVariants productVariants);
    Boolean existsByCartAndProductVariants(Cart cart, ProductVariants productVariants);
    @Query(value = """
    SELECT COUNT(*)
    FROM cart_items ci
    JOIN cart c ON ci.ma_gh = c.ma_gh
    WHERE c.ma_tk = :maTk
""", nativeQuery = true)
    Integer countCartItemByUser(@Param("maTk") Integer maTk);

    void deleteByCartAndProductVariants(Cart cart, ProductVariants productVariants);
    Boolean existsByCart(Cart cart);
}
