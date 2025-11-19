package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.TaoUsersRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.UserResponse;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.UserMapper;
import com.Nhom19.shopQuanAo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Users users = userMapper.toUsers(request);
        UserResponse userResponse = userMapper.toUserResponse(users);

        // Log debug trước khi save nếu cần:
        System.out.println("Saving user: " + users.getUsername() + ", diaChi=" + users.getDiachi());
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

//    public UserResponse userUpdate(Integer userID, CapNhatUserRequest request)
//    {
//            Users user = userRepository.findById(userID).get();
//            user = userMapper.toUsers2(user,request);
//            userRepository.save(user);
//            return userMapper.toUserResponse(user);
//    }
}
