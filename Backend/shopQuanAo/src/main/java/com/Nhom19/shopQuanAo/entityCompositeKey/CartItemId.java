package com.Nhom19.shopQuanAo.entityCompositeKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CartItemId {
    @Column(name = "ma_gh")
    private Integer cartId;

    @Column(name = "ma_bien_the")
    private Integer variantId;

    public CartItemId() {}

    public CartItemId(Integer cartId, Integer variantId) {
        this.cartId = cartId;
        this.variantId = variantId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemId that = (CartItemId) o;
        return Objects.equals(cartId, that.cartId) &&
                Objects.equals(variantId, that.variantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, variantId);
    }
// Getters, setters, equals, hashCode
}
