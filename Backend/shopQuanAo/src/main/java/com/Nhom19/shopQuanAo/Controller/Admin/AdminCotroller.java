package com.Nhom19.shopQuanAo.Controller.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.AdminRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.AdminResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.entity.Admin;
import com.Nhom19.shopQuanAo.service.Adminservice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PutMapping("/{id}/disable")
    public ApiResponse<?> disableAdmin(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String authHeader

    ) {
        adminservice.disableAdmin(id, authHeader);
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Admin đã bị khóa");
        return apiResponse;
    }
    @PutMapping("/{id}/enable")
    public ApiResponse<Boolean> enableAdmin(@PathVariable Integer id) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Admin đã được mở khóa");
        apiResponse.setResult(adminservice.enableAdmin(id));
        return apiResponse;
    }
}
