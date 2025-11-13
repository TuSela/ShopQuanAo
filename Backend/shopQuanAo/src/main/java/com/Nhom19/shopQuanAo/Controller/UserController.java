package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Request.CapNhatUserRequest;
import com.Nhom19.shopQuanAo.DTO.Request.TaoUsersRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.TaoUsersResponse;
import com.Nhom19.shopQuanAo.DTO.Response.UserResponse;
import com.Nhom19.shopQuanAo.entity.Users;
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
    public ApiResponse<TaoUsersResponse> creatUsers(@RequestBody @Valid TaoUsersRequest Request){
        ApiResponse<TaoUsersResponse> apiResponse = new ApiResponse<>();
        Users taiKhoanKhachHang = userService.createUsers(Request);
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
    public ApiResponse<List<Users>> getUsers(){
            ApiResponse<List<Users>> apiResponse = new ApiResponse<>();
            apiResponse.setResult(userService.getUsers());
            return apiResponse;
    }


    @GetMapping("/{userId}")
    public ApiResponse<Users> getUser(@PathVariable Integer userId)
    {
        ApiResponse<Users> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUserById(userId));
        return  apiResponse;
    }

    @PutMapping("/{userId}")
    public ApiResponse<Users> upDateUser(@PathVariable Integer userId, @RequestBody @Valid CapNhatUserRequest request){
        ApiResponse<Users> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.userUpdate(userId, request));
        return apiResponse;
    }
    @DeleteMapping("/{userId}")
    public ApiResponse deleteUser(@PathVariable Integer userId)
    {
        ApiResponse apiResponse = new ApiResponse();
        userService.deleteUserById(userId);
        apiResponse.setResult("success");
        return apiResponse;
    }
}
