package com.Nhom19.shopQuanAo.Controller.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.AdminRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.AdminResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.entity.Admin;
import com.Nhom19.shopQuanAo.service.Adminservice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admins")
@RestController
public class AdminCotroller {
    @Autowired
    private Adminservice adminservice;

    @PostMapping
    public ApiResponse<AdminResponse> createUser(@RequestBody @Valid AdminRequest request)
    {
        ApiResponse <AdminResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(adminservice.createAdmin(request));
        return apiResponse;
    }


    @GetMapping()
    public ApiResponse<List<AdminResponse>>  getUsers()
    {
        ApiResponse<List<AdminResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(adminservice.getUsers());
        return apiResponse;
    }

    @GetMapping("/{userId}")
    public ApiResponse<Admin> getUser(@PathVariable Integer userId)
    {
        ApiResponse<Admin> apiResponse = new ApiResponse<>();
        apiResponse.setResult(adminservice.getUserById(userId));
        return  apiResponse;
    }
    @PutMapping("/{userId}")
    public ApiResponse<AdminResponse> upDateUser(@PathVariable Integer userId, @RequestBody @Valid AdminRequest request){
        ApiResponse<AdminResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(adminservice.userUpdate(userId, request));
        return apiResponse;
    }
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Integer userId)
    {
        adminservice.deleteUserById(userId);
        return "success";
    }
    @GetMapping("/myinfor")
    public ApiResponse<AdminResponse> getMyInfor()
    {
        ApiResponse<AdminResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(adminservice.getMyInfo());
        return apiResponse;
    }
}
