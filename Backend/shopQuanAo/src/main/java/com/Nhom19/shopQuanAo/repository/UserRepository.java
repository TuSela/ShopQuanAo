package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.CustomerStatisticDTO;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.TopCustomerDTO;
import com.Nhom19.shopQuanAo.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findBySdt(String sdt);
    boolean existsByPassword(String password);
    Users findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsBySdt(String sdt);

    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.CustomerStatisticDTO(
        u.hoten,
        COUNT(o.maDdh),
        COALESCE(SUM(o.tongTien), 0)
    )
    FROM Users u
    LEFT JOIN Orders o
        ON o.users = u
       AND o.orderStatus = 'DA_GIAO'
    GROUP BY u.maTk, u.hoten
""")
    List<CustomerStatisticDTO> thongKeHanhViKhachHang();

    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.TopCustomerDTO(
        u.maTk,
        u.hoten,
        COUNT(o.maDdh),
        SUM(o.tongTien)
    )
    FROM Orders o
    JOIN o.users u
    WHERE o.orderStatus = 'DA_GIAO'
    GROUP BY u.hoten,u.maTk
    ORDER BY SUM(o.tongTien) DESC
""")
    List<TopCustomerDTO> topKhachHang(Pageable pageable);

}
