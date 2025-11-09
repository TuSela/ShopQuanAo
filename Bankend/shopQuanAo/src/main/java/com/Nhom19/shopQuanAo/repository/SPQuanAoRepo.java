package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.SPQuanAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SPQuanAoRepo extends JpaRepository<SPQuanAo, Integer> {
}
