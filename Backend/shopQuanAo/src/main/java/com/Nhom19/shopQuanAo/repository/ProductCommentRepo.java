package com.Nhom19.shopQuanAo.repository;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.NegativeCommentDTO;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.RatingStatisticDTO;
import com.Nhom19.shopQuanAo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface ProductCommentRepo extends JpaRepository<ProductComments, Integer> {
    Set<ProductComments> getByMaBl(Integer id);
    List<ProductComments> findByProducts(Products product);
    @Query("""
    SELECT pc
    FROM ProductComments pc
    JOIN FETCH pc.users
    JOIN FETCH pc.productVariants pv
    JOIN FETCH pv.colors
    JOIN FETCH pv.sizes
    WHERE pc.products = :product
""")
    List<ProductComments> findDetailComments(@Param("product") Products product);

    boolean existsByUsersAndOrdersAndProductVariants(Users users,Orders orders, ProductVariants productVariants);

    // lấy ra danh sách comment của tôi
        @Query("""
        SELECT pc
        FROM ProductComments pc
        JOIN pc.orders o
        JOIN o.users u
        WHERE u.maTk = :maTk
        ORDER BY pc.ngayTao DESC
    """)
        List<ProductComments> findMyComments(@Param("maTk") Integer maTk);

    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.RatingStatisticDTO(
        pc.diemDanhGia,
        COUNT(pc.maBl)
    )
    FROM ProductComments pc
    WHERE (:fromDate IS NULL OR pc.ngayTao >= :fromDate)
      AND (:toDate IS NULL OR pc.ngayTao <= :toDate)
    GROUP BY pc.diemDanhGia
    ORDER BY pc.diemDanhGia
""")
    List<RatingStatisticDTO> thongKeDanhGia(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("""
    SELECT new com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.NegativeCommentDTO(
        pc.noiDung,
        u.email,
        p.tenSp,
        pc.trangThai
    )
    FROM ProductComments pc
    JOIN pc.users u
    JOIN pc.productVariants pv
    JOIN pv.products p
    WHERE pc.diemDanhGia <= 2
      AND pc.trangThai = 'HIDDEN'
""")
    List<NegativeCommentDTO> binhLuanTieuCuc();

}


