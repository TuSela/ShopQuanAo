package com.Nhom19.shopQuanAo.Controller.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CommentRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.service.CommentService;
import com.Nhom19.shopQuanAo.service.JwtUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RequestMapping("/comments")
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    JwtUtils jwtUtils;

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


}
