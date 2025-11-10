package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.TaiKhoanKhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaiKhoanKhachHangRepo extends JpaRepository<TaiKhoanKhachHang, Integer> {
    // Derived query: Spring Data sẽ tìm property tenTK trong entity
    boolean existsByTenTK(String tenTK);
}
