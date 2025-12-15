package com.Nhom19.shopQuanAo.Controller.Customer;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.CreateCartRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Customer.UpdateMyCartReq;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart.CreatCartResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart.MyCartResponse;
import com.Nhom19.shopQuanAo.entity.Cart;
import com.Nhom19.shopQuanAo.repository.CartItemRepo;
import com.Nhom19.shopQuanAo.service.CartService;
import com.Nhom19.shopQuanAo.service.JwtUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping
    public ApiResponse<CreatCartResponse> CreateCart(@RequestBody CreateCartRequest request, @RequestHeader("Authorization") String authHeader) {
        ApiResponse <CreatCartResponse> response = new ApiResponse<>();
        String token = authHeader.substring(7);
        JWTClaimsSet claims = jwtUtils.parseToken(token);
        try {
            Integer Id = claims.getIntegerClaim("id");
            response.setResult(cartService.createCart(request,Id));
            return  response;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping
    public ApiResponse<MyCartResponse> getMyCart() {
        ApiResponse<MyCartResponse> response = new ApiResponse<>();
        response.setResult(cartService.getAllMyCart());
        return response;
    }
    @PutMapping("/{maBienThe}")
    public ApiResponse<Boolean> updateMyCart(@RequestBody UpdateMyCartReq request, @PathVariable Integer maBienThe) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setResult(cartService.UpdateMyCart(request,maBienThe));
        return response;
    }
    @DeleteMapping("/{maBienThe}")
    public ApiResponse<CreatCartResponse> deleteMyCart(@PathVariable Integer maBienThe) {
        ApiResponse<CreatCartResponse> response = new ApiResponse<>();
        response.setResult(cartService.DeleteMyCartItem(maBienThe));
        return response;
    }
}
