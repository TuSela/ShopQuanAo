package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.AdminRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.AdminResponse;
import com.Nhom19.shopQuanAo.entity.Admin;
import com.Nhom19.shopQuanAo.entity.Role;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.AdminMapper;
import com.Nhom19.shopQuanAo.repository.AdminRepository;
import com.Nhom19.shopQuanAo.repository.PermissionRepository;
import com.Nhom19.shopQuanAo.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
    public AdminResponse createAdmin(AdminRequest request){
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Admin admin1 = adminRepository.findByUsername(sdt).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        Admin users = new Admin();
        if(adminRepository.existsIdByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        users.setUsername(request.getUsername());
        users.setPassword(request.getPassword());
        users.setManagerCode(admin1.getMaTk());
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        users.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles1 = new HashSet<>();
        Set<String> roles = request.getRoles();
        roles.forEach(role1 -> {
           roles1.add(roleRepository.findById(role1).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED)));
        });
        users.setRoles(roles1);
        Admin admin =  adminRepository.save(users);
        return adminMapper.toDTO(admin);
    }
    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
    public List<AdminResponse> getUsers()
    {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(adminMapper::toDTO).collect(Collectors.toList());
    }
        @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
//    @PostAuthorize("returnObject.username == authentication.name")
    public Admin getUserById(Integer id)
    {
        Admin admin = adminRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        return admin;
    }
    public AdminResponse getMyInfo()
    {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Admin admin = adminRepository.findByUsername(username).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        return adminMapper.toDTO(admin);
    }
    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
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
    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
    @Transactional
    public void disableAdmin(Integer adminId, String authHeader) {
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Admin admin1 = adminRepository.findByUsername(sdt).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        if (adminId.equals(admin1.getMaTk())) {
            throw new AppException(ErrorCode.CANNOT_LOCK_SELF);
        }
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        if(adminId.equals(admin.getManagerCode())&&adminId.equals(1)){
            throw new AppException(ErrorCode.USER_NOT_UNDER_YOUR_MANAGEMENT);
        }
        admin.setTrangThai(false);
        adminRepository.save(admin);
    }

    @PreAuthorize("hasAuthority('ADMIN_MANAGE')")
    public Boolean enableAdmin(Integer adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!adminId.equals(admin.getManagerCode()) && !adminId.equals(1)) {
            throw new AppException(ErrorCode.USER_NOT_UNDER_YOUR_MANAGEMENT);
        }
        admin.setTrangThai(true);
        adminRepository.save(admin);
        return true;
    }

}
