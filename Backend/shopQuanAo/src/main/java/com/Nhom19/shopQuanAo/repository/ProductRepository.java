package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
//    Products getBytinhTrang(String tinhTrang);
}
