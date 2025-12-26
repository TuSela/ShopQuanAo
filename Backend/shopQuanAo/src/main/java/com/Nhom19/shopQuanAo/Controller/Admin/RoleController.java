package com.Nhom19.shopQuanAo.Controller.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.PermissionRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Admin.RoleRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.RoleResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> addPermission(@RequestBody RoleRequest request){

        RoleResponse roleResponse = roleService.create(request);
        ApiResponse<RoleResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleResponse);
        return apiResponse;
    }
    @GetMapping
    public ApiResponse<List<RoleResponse>> getPermission(PermissionRequest permission){
        List<RoleResponse> all = roleService.getAll();
        ApiResponse<List<RoleResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(all);
        return apiResponse;
    }
//    @DeleteMapping("/{permission}")
//    public ApiResponse<Boolean> deletePermission(@PathVariable String permission){
//        roleService.delete(permission);
//        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(true);
//        return apiResponse;
//    }
}
