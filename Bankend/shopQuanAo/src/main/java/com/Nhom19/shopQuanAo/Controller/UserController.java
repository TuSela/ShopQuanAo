package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Request.TaoUsersRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.TaoUsersResponse;
import com.Nhom19.shopQuanAo.entity.TaiKhoanKhachHang;
import com.Nhom19.shopQuanAo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping()
    public ApiResponse<TaoUsersResponse> creatUsers(@RequestBody @Valid  TaoUsersRequest Request){
        ApiResponse<TaoUsersResponse> apiResponse = new ApiResponse<>();
        TaiKhoanKhachHang taiKhoanKhachHang = userService.createUsers(Request);
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
    public ApiResponse<List<TaiKhoanKhachHang>> getUsers(){
            ApiResponse<List<TaiKhoanKhachHang>> apiResponse = new ApiResponse<>();
            apiResponse.setResult(userService.getUsers());
            return apiResponse;

    }

}
