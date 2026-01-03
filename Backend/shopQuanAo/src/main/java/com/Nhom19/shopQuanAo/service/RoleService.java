package com.Nhom19.shopQuanAo.service;


import com.Nhom19.shopQuanAo.DTO.Request.Admin.RoleRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.RoleResponse;
import com.Nhom19.shopQuanAo.entity.Permission;
import com.Nhom19.shopQuanAo.entity.Role;
import com.Nhom19.shopQuanAo.repository.PermissionRepository;
import com.Nhom19.shopQuanAo.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private PermissionRepository permissionRepository;
    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
    public RoleResponse create(RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
        RoleResponse roleResponse=new RoleResponse();
        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());
        roleResponse.setPermissions(role.getPermissions());
        return roleResponse;
    }
    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
    public List<RoleResponse> getAll() {
        return roleRepository.findAll(). stream().map(p -> new RoleResponse(p.getName(),p.getDescription(),p.getPermissions())).collect(Collectors.toList());
    }
    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
    public Boolean update(RoleRequest request,String id) {
        Role role = roleRepository.findById(id).orElse(null);
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        Set<Permission> permissions = new HashSet<>();
        Set<String> permissions1 = request.getPermissions();
        permissions.addAll(permissionRepository.findAllById(request.getPermissions()));
        role.setPermissions(permissions);
        roleRepository.save(role);
        return true;
    }
    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
    @Transactional
    public Boolean deleteRole(String id) {
        try {
            roleRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
