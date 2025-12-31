package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);
    Boolean existsIdByUsername(String username);
}
