package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.Admins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admins, Integer> {
    Admins findByUsername(String username);
}
