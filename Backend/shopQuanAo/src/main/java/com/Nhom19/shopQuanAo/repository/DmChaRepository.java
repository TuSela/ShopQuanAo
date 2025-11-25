package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.DmCha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DmChaRepository extends JpaRepository<DmCha, Integer> {
//    Optional<DmCha> findById(String Id);

}
