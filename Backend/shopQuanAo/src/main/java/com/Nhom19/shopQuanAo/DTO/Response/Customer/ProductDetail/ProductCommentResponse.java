package com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter

public class ProductCommentResponse {
        private int maBl;
        private UserCommentResponse users;
        private CommentVariantResponse productVariants;
        private LocalDateTime ngayTao;
        private BigDecimal diemDanhGia;
        private String noiDung;
        private Integer maSp;
        private String trangThai;
}
