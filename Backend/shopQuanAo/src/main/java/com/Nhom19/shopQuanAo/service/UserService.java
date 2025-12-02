package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CapNhatUserRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Customer.TaoUsersRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Customer.UpdatePassRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.UserResponse;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.UserMapper;
import com.Nhom19.shopQuanAo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    public UserResponse createUsers(TaoUsersRequest request){
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        // kiểm tra tồn tại
        if (userRepository.existsBySdt(request.getSdt())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Users users = userMapper.toUsers(request);
        UserResponse userResponse = userMapper.toUserResponse(users);

        userRepository.save(users);
        return userResponse;
    }
    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }
    public UserResponse getUserById(Integer id)
    {
       return userMapper.toUserResponse(userRepository.findById(id).get());
    }

    public boolean deleteUserById(Integer id)
    {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        else  {
            return false;
        }

    }

    public Boolean userUpdate( Integer userID,CapNhatUserRequest request)
    {
            Users user = userRepository.findById(userID).get();
            user.setSdt(request.getSdt());
            user.setEmail(request.getEmail());
            user.setHoten(request.getHoTen());
            userRepository.save(user);

            return true;
    }

    public Boolean updateMyPass(Integer userID, UpdatePassRequest request)
    {
        Users user = userRepository.findById(userID).get();
        if(userRepository.existsByPassword(request.getOldPass())) {
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return true;
        }
        else  {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
    }
    public UserResponse getMyInfo()
    {
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepository.findBySdt(sdt);
        return userMapper.toUserResponse(users);

    }
}
