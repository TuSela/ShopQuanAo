package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.AdminRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.AdminResponse;
import com.Nhom19.shopQuanAo.entity.Admin;
import com.Nhom19.shopQuanAo.enums.Role;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.AdminMapper;
import com.Nhom19.shopQuanAo.repository.AdminRepository;
import com.Nhom19.shopQuanAo.repository.PermissionRepository;
import com.Nhom19.shopQuanAo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Adminservice {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public AdminResponse createAdmin(AdminRequest request){
        Admin users = new Admin();
        if(adminRepository.existsIdByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        users.setUsername(request.getUsername());
        users.setPassword(request.getPassword());

//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        users.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.ADMIN.name());
//        users.setRoles(request.getRoles());
        Admin admin =  adminRepository.save(users);
        return adminMapper.toDTO(admin);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<AdminResponse> getUsers()
    {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(adminMapper::toDTO).collect(Collectors.toList());
    }
    //    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @PostAuthorize("returnObject.username == authentication.name")
    public Admin getUserById(Integer id)
    {
        Admin admin = adminRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        return admin;
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public AdminResponse getMyInfo()
    {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Admin admin = adminRepository.findByUsername(username).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        return adminMapper.toDTO(admin);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public AdminResponse userUpdate(Integer userID, AdminRequest request)
    {
        Admin user = getUserById(userID);
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        Admin admin = adminRepository.save(user);
        return  adminMapper.toDTO(admin);
    }

    public void  deleteUserById(Integer id)
    {
        adminRepository.deleteById(id);
    }
}
