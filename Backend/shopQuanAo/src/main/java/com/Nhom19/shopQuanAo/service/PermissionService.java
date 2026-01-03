package com.Nhom19.shopQuanAo.service;


import com.Nhom19.shopQuanAo.DTO.Request.Admin.PermissionRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.PermissionResponse;
import com.Nhom19.shopQuanAo.entity.Permission;
import com.Nhom19.shopQuanAo.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
    public PermissionResponse create(PermissionRequest request){
        if (request == null) {
            throw new IllegalArgumentException("Request must not be null");
        }
        String name = request.getName();
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Permission name must be provided and not empty.");
        }
            Permission permission=new Permission();
            permission.setName(request.getName());
            permission.setDescription(request.getDescription());
            permissionRepository.save(permission);
            PermissionResponse permissionResponse=new PermissionResponse(permission.getDescription(), permission.getName());
            return permissionResponse;
        }
    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
    public List<PermissionResponse> findAll(){
        List<PermissionResponse> permissionResponseList=new ArrayList<>();
        Permission permission =new Permission();
        return permissionRepository.findAll()
                    .stream()
                    .map(p -> new PermissionResponse(p.getDescription(), p.getName()))
                    .collect(Collectors.toList());

    }
    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}
