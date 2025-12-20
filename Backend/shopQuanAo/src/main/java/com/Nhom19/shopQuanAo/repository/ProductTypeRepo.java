package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.ProductTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepo extends JpaRepository<ProductTypes, Integer> {
    boolean existsByTenLoai(String tenLoai);
}
