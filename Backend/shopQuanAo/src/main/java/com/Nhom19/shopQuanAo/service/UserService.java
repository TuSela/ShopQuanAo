package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.CapNhatUserRequest;
import com.Nhom19.shopQuanAo.DTO.Request.TaoUsersRequest;
import com.Nhom19.shopQuanAo.DTO.Response.UserResponse;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Users createUsers(TaoUsersRequest request){
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        // kiểm tra tồn tại
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Users users = new Users();
        users.setUsername(request.getUsername());
        users.setDiachi(request.getDiachi());
        users.setEmail(request.getEmail());
        users.setSdt(request.getSdt());
        users.setPassword(request.getPassword());
        users.setHoten(request.getHoten());

        // Log debug trước khi save nếu cần:
        System.out.println("Saving user: " + users.getUsername() + ", diaChi=" + users.getDiachi());

        return userRepository.save(users);
    }
    public List<Users> getUsers(){
        return userRepository.findAll();
    }
    public Users getUserById(Integer id)
    {
       return userRepository.findById(id).get();
    }
    public void deleteUserById(Integer id)
    {
        userRepository.deleteById(id);
    }
    public Users userUpdate(Integer userID, CapNhatUserRequest request)
    {
        {
            Users user = getUserById(userID);
            user.setPassword(request.getPassword());
            user.setDiachi(request.getDiachi());
            user.setEmail(request.getEmail());
            user.setSdt(request.getSdt());
            user.setHoten(request.getHoten());
            return userRepository.save(user);
        }
    }
}
