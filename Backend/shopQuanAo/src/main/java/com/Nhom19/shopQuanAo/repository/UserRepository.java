package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    // Derived query: Spring Data sẽ tìm property tenTK trong entity
    boolean existsByUsername(String username);
    Users findByUsername(String username);
    boolean existsByPassword(String password);



    Users findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsBySdt(String sdt);

}
