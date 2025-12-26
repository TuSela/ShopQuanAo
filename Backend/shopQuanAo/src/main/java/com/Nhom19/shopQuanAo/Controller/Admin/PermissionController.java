package com.Nhom19.shopQuanAo.Controller.Admin;


import com.Nhom19.shopQuanAo.DTO.Request.Admin.PermissionRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.PermissionResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> addPermission(@RequestBody PermissionRequest permission){

        PermissionResponse permissionResponse = permissionService.create(permission);
        ApiResponse<PermissionResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(permissionResponse);
        return apiResponse;
    }
    @GetMapping
    public ApiResponse<List<PermissionResponse>> getPermission(PermissionRequest permission){
        List<PermissionResponse> all = permissionService.findAll();
        ApiResponse<List<PermissionResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(all);
        return apiResponse;
    }
    @DeleteMapping("/{permission}")
    public ApiResponse<Boolean> deletePermission(@PathVariable String permission){
        permissionService.delete(permission);
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(true);
        return apiResponse;
    }
}
