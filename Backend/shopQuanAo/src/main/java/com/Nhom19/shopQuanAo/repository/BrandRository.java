package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRository extends JpaRepository<Brands, Integer> {
}
