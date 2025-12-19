package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.ProductTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductTypeRepo extends JpaRepository<ProductTypes, Integer> {
    @Query(value = """
    SELECT *
    FROM product_types
    WHERE tinh_trang = 1
    ORDER BY
        doi_tuong COLLATE Vietnamese_CI_AI,
        ten_loai COLLATE Vietnamese_CI_AI,
        chi_tiet_loai COLLATE Vietnamese_CI_AI
""", nativeQuery = true)
    List<ProductTypes> findAllForMenuSorted();
}
