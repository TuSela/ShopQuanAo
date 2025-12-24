package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProductCommentRepo extends JpaRepository<ProductComments, Integer> {
    Set<ProductComments> getByMaBl(Integer id);
    List<ProductComments> findByProducts(Products product);
    boolean existsByUsersAndOrdersAndProductVariants(Users users,Orders orders, ProductVariants productVariants);

    // lấy ra danh sách comment của tôi
        @Query("""
        SELECT pc
        FROM ProductComments pc
        JOIN pc.orders o
        JOIN o.users u
        WHERE u.maTk = :maTk
        ORDER BY pc.ngayTao DESC
    """)
        List<ProductComments> findMyComments(@Param("maTk") Integer maTk);
}


