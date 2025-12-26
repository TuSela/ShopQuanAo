package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.entity.addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<addresses, Integer> {
    public List<addresses> findByUsers(Users users);

}
