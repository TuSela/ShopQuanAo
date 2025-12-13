package com.Nhom19.shopQuanAo.entityCompositeKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class OrderItemId {
    @Column(name = "ma_ddh")
    private Integer orderId;

    @Column(name = "ma_bien_the")
    private Integer variantId;

    public OrderItemId() {}

    public OrderItemId(Integer orderId, Integer variantId) {
        this.orderId = orderId;
        this.variantId = variantId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(variantId, that.variantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, variantId);
    }
// Getters, setters, equals, hashCode
}
