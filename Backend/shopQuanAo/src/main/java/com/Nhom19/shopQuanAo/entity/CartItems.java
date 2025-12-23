package com.Nhom19.shopQuanAo.entity;

import com.Nhom19.shopQuanAo.entityCompositeKey.CartItemId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItems {
    @EmbeddedId

    private CartItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cartId")
    @JoinColumn(name = "ma_gh")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("variantId")
    @JoinColumn(name = "ma_bien_the")
    private ProductVariants productVariants;

    private int soluong;

    private BigDecimal tongTien;
}
