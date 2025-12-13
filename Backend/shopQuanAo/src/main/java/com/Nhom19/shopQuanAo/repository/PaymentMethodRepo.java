package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.entity.PaymentMethods;
import com.Nhom19.shopQuanAo.entity.ProductColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepo extends JpaRepository<PaymentMethods, Integer> {
}
