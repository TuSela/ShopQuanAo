package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

}
