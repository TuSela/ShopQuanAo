package com.Nhom19.shopQuanAo.DTO.Response.Customer;
import com.Nhom19.shopQuanAo.DTO.Response.ProductDetail.CommentVariantResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class ProductCommentResponse {
    private int maBl;
    private UserCommentResponse users;
    private List<CommentVariantResponse> productVariants = new ArrayList<>();
    private LocalDateTime ngayTao;
    private Float diemDanhGia;
    private String noiDung;

}
