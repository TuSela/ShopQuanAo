package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductVariantRepo extends JpaRepository<ProductVariants, Integer> {
    public Set<ProductVariants> findByProducts(Products products);
}
