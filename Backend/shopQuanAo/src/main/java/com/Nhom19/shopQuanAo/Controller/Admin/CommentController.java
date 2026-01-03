package com.Nhom19.shopQuanAo.Controller.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CommentRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.ProductCommentResponse;
import com.Nhom19.shopQuanAo.service.CommentService;
import com.Nhom19.shopQuanAo.service.JwtUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping("/comments")
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    JwtUtils jwtUtils;
    @GetMapping
    public ApiResponse<List<ProductCommentResponse>> mapToProductComment() {
        ApiResponse<List<ProductCommentResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(commentService.mapToProductComment());
        return apiResponse;
    }
    @PostMapping
    public ApiResponse<Boolean> CreateComment(@RequestBody CommentRequest request, @RequestHeader("Authorization") String authHeader) {
        ApiResponse <Boolean> response = new ApiResponse<>();
        String token = authHeader.substring(7);
        JWTClaimsSet claims = jwtUtils.parseToken(token);
        try {
            Integer Id = claims.getIntegerClaim("id");
            response.setResult(commentService.CreateComment(request,Id));
            return  response;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/{id}/disable")
    public ApiResponse<?> disableAdmin(
            @PathVariable Integer id

    ) {
        commentService.disableComment(id);
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Admin đã bị khóa");
        return apiResponse;
    }
    @PutMapping("/{id}/enable")
    public ApiResponse<Boolean> enableAdmin(@PathVariable Integer id) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Admin đã được mở khóa");
        apiResponse.setResult(commentService.enableComment(id));
        return apiResponse;
    }
    @DeleteMapping("/{maBl}")
    public ApiResponse<Boolean> deleteComment(@PathVariable Integer maBl) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
        commentService.deleteComment(maBl);
            response.setResult(true);
        }
        catch (Exception e) {
            response.setResult(false);
        }
        return response;
    }
}
