package com.Nhom19.shopQuanAo.DTO.Response.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCommentResponse {
    private int maBl;
    private int maTk;
    private LocalDateTime ngayTao;
    private Integer diemDanhGia;
    private String noiDung;
    private String hoten; // from users

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCommentResponse)) return false;
        ProductCommentResponse that = (ProductCommentResponse) o;
        return maBl == that.maBl;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maBl);
    }
}
