package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.ProductTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    //lọc id loại theo 3 điều kiện
    @Query("""
    SELECT p FROM ProductTypes p
    WHERE (:doiTuong IS NULL OR p.doiTuong = :doiTuong)
      AND (:tenLoai IS NULL OR p.tenLoai = :tenLoai)
      AND (:chiTietLoai IS NULL OR p.chiTietLoai = :chiTietLoai)
""")
    List<ProductTypes> search(
            @Param("doiTuong") String doiTuong,
            @Param("tenLoai") String tenLoai,
            @Param("chiTietLoai") String chiTietLoai
    );}
