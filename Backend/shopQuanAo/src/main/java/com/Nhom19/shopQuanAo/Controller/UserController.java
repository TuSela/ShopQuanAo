package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CapNhatUserRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Customer.AddressRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Customer.TaoUsersRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Customer.UpdatePassRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.TaoUsersResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.UserResponse;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.entity.addresses;
import com.Nhom19.shopQuanAo.service.AddressSevice;
import com.Nhom19.shopQuanAo.service.JwtUtils;
import com.Nhom19.shopQuanAo.service.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.validation.Valid;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AddressSevice addressSevice;
    @PostMapping()
    public ApiResponse<TaoUsersResponse> creatUsers(@RequestBody @Valid TaoUsersRequest Request){
        ApiResponse<TaoUsersResponse> apiResponse = new ApiResponse<>();
        UserResponse taiKhoanKhachHang = userService.createUsers(Request);
        TaoUsersResponse taoUsersResponse = new TaoUsersResponse();
        if (taiKhoanKhachHang != null) {
            taoUsersResponse.setSuccess(true);
        } else {
            taoUsersResponse.setSuccess(false);
        }
         apiResponse.setResult(taoUsersResponse);
         return apiResponse;
    }
    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers(){
            ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
            apiResponse.setResult(userService.getUsers());
            return apiResponse;
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable Integer userId)
    {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUserById(userId));
        return  apiResponse;
    }
    @PutMapping("/{userId}")
    public ApiResponse<Boolean> upDateUser(@PathVariable Integer userId, @RequestBody @Valid CapNhatUserRequest request){
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.userUpdate(userId, request));
        return apiResponse;
    }
    @DeleteMapping("/{userId}")
    public ApiResponse deleteUser(@PathVariable Integer userId)
    {
        ApiResponse apiResponse = new ApiResponse();
        var resuts = userService.deleteUserById(userId);
        if (resuts) {
            apiResponse.setResult(true);
        } else {
            apiResponse.setResult(false);
        }
        return apiResponse;
    }
    @GetMapping("/myinfor")
    public ApiResponse<UserResponse> getMyInfor()
    {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getMyInfo());
        return apiResponse;
    }
    @GetMapping("/address")
    public ApiResponse<List<addresses>> getAddresses()
    {
        ApiResponse<List<addresses>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(addressSevice.getmyaddress());
        return apiResponse;
    }
    @PostMapping("/address")
    public ApiResponse<Boolean> createAddress(@RequestBody AddressRequest request)
    {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(addressSevice.CreateAddress(request));
        return apiResponse;
    }
    @DeleteMapping("/address/{MaDiaChi}")
    public ApiResponse<Boolean> DeleteAddress(@PathVariable Integer MaDiaChi)
    {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(addressSevice.DeleteAddress(MaDiaChi));
        return apiResponse;
    }
    @PutMapping ("/address/{MaDiaChi}")
    public ApiResponse<Boolean> UpdateAddress(@RequestBody AddressRequest request, @PathVariable Integer MaDiaChi)
    {

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(addressSevice.UpdateAddress(request,MaDiaChi));
        return apiResponse;
    }
    @Autowired
    JwtUtils jwtUtils;
    @PutMapping("/myinfor")
    public ApiResponse<Boolean> UpdateMyInfor(@RequestBody CapNhatUserRequest request, @RequestHeader("Authorization") String authHeader)
    {
        String token = authHeader.substring(7);
        JWTClaimsSet claims = jwtUtils.parseToken(token);
        try {
            Integer Id = claims.getIntegerClaim("id");
            ApiResponse<Boolean> apiResponse = new ApiResponse<>();

            apiResponse.setResult(userService.userUpdate(Id,request));
            return apiResponse;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/change-password")
    public ApiResponse<Boolean> UpdateMyPassword(@RequestBody UpdatePassRequest request, @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        JWTClaimsSet claims = jwtUtils.parseToken(token);
        try {
            Integer Id = claims.getIntegerClaim("id");
            ApiResponse<Boolean> apiResponse = new ApiResponse<>();

            apiResponse.setResult(userService.updateMyPass(Id,request));
            return apiResponse;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
