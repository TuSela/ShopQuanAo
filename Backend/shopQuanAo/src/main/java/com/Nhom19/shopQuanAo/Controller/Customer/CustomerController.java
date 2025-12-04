package com.Nhom19.shopQuanAo.Controller.Customer;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CapNhatUserRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Customer.AddressRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Customer.UpdatePassRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.UserResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.entity.addresses;
import com.Nhom19.shopQuanAo.service.AddressSevice;
import com.Nhom19.shopQuanAo.service.JwtUtils;
import com.Nhom19.shopQuanAo.service.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/myinfor")
public class CustomerController {
    @Autowired
    UserService userService;
    @Autowired
    AddressSevice addressSevice;

    @GetMapping()
    public ApiResponse<UserResponse> getMyInfo() {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getMyInfo());
        return apiResponse;
    }

    @GetMapping("/address")
    public ApiResponse<List<addresses>> getAddresses() {
        ApiResponse<List<addresses>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(addressSevice.getmyaddress());
        return apiResponse;
    }

    @PostMapping("/address")
    public ApiResponse<Boolean> createAddress(@RequestBody AddressRequest request) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(addressSevice.CreateAddress(request));
        return apiResponse;
    }

    @DeleteMapping("/address/{MaDiaChi}")
    public ApiResponse<Boolean> DeleteAddress(@PathVariable Integer MaDiaChi) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(addressSevice.DeleteAddress(MaDiaChi));
        return apiResponse;
    }

    @PutMapping("/address/{MaDiaChi}")
    public ApiResponse<Boolean> UpdateAddress(@RequestBody AddressRequest request, @PathVariable Integer MaDiaChi) {

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(addressSevice.UpdateAddress(request, MaDiaChi));
        return apiResponse;
    }

    @Autowired
    JwtUtils jwtUtils;

    @PutMapping()
    public ApiResponse<Boolean> UpdateMyInfor(@RequestBody CapNhatUserRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        JWTClaimsSet claims = jwtUtils.parseToken(token);
        try {
            Integer Id = claims.getIntegerClaim("id");
            ApiResponse<Boolean> apiResponse = new ApiResponse<>();

            apiResponse.setResult(userService.userUpdate(Id, request));
            return apiResponse;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/change-password")
    public ApiResponse<Boolean> UpdateMyPassword(@RequestBody UpdatePassRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        JWTClaimsSet claims = jwtUtils.parseToken(token);
        try {
            Integer Id = claims.getIntegerClaim("id");
            ApiResponse<Boolean> apiResponse = new ApiResponse<>();

            apiResponse.setResult(userService.updateMyPass(Id, request));
            return apiResponse;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/avatar")
    public ApiResponse<String> UpdateMyAvatar(@RequestHeader("Authorization") String authHeader, @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        String token = authHeader.substring(7);
        JWTClaimsSet claims = jwtUtils.parseToken(token);
        try {
            Integer Id = claims.getIntegerClaim("id");
            ApiResponse<String> apiResponse = new ApiResponse<>();

            apiResponse.setResult(userService.uploadAvatar(Id, avatar));
            return apiResponse;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
