package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.ProductComments;
import com.Nhom19.shopQuanAo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProductCommentRepo extends JpaRepository<ProductComments, Integer> {
    Set<ProductComments> getByMaBl(Integer id);
    List<ProductComments> findByProducts(Products product);
}
