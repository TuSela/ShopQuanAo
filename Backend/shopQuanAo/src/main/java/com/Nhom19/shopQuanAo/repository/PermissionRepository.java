package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,String> {

}
