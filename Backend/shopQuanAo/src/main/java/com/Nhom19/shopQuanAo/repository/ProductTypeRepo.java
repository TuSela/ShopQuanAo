package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.ProductTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductTypeRepo extends JpaRepository<ProductTypes, Integer> {
    @Query("""
    SELECT pt
    FROM ProductTypes pt
    WHERE pt.tinhTrang = '1'
    ORDER BY pt.doiTuong, pt.tenLoai, pt.chiTietLoai
""")
    List<ProductTypes> findAllForMenu();
}
