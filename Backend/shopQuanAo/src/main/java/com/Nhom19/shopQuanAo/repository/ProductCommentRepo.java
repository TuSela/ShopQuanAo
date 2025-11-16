package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.ProductComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCommentRepo extends JpaRepository<ProductComments, Integer> {
}
