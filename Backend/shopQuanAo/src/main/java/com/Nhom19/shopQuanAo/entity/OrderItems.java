package com.Nhom19.shopQuanAo.entity;


import com.Nhom19.shopQuanAo.entityCompositeKey.OrderItemId;
import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;

@Data
@Entity
public class OrderItems {
    @EmbeddedId
    private OrderItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")  // ánh xạ field orderId trong OrderItemId
    @JoinColumn(name = "ma_ddh")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("variantId") // ánh xạ field variantId trong OrderItemId
    @JoinColumn(name = "ma_bien_the")
    private ProductVariants productVariants;

    @Column(name = "so_luong_dat")
    private int soLuong;

    @Column(name = "tong_tien")
    private BigDecimal tongTien;
}

