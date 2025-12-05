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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    public Boolean userUpdate( Integer userID,CapNhatUserRequest request)  {
        Users user = userRepository.findById(userID).get();
            user.setSdt(request.getSdt());
            user.setEmail(request.getEmail());
            user.setHoten(request.getHoten());
            user.setGioiTinh(request.getGioiTinh());
            user.setNgaySinh(request.getNgaySinh());

            userRepository.save(user);

            return true;

        }
    public Boolean updateMyPass(Integer userID, UpdatePassRequest request) {
        Users user = userRepository.findById(userID).get();
        if (request.getNewPass1().equals(request.getNewPass2())) {
            if (user.getPassword().equals(request.getOldPass())) {
                user.setPassword(request.getNewPass1());


                userRepository.save(user);
                return true;
            } else {
                throw new AppException(ErrorCode.PASSWORD_INVALID);
            }
        }else {
            throw new AppException(ErrorCode.PASSWORD_CONFIRM_NOT_MATCH);
        }
    }
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public UserResponse getMyInfo()
    {
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepository.findBySdt(sdt);
        return userMapper.toUserResponse(users);
    }
    public String uploadAvatar(Integer userID,MultipartFile avatar) throws IOException {
        Users user = userRepository.findById(userID).get();
        // nếu client không upload ảnh → giữ ảnh cũ
        if (avatar != null && !avatar.isEmpty()) {

            // 1. Kiểm tra loại ảnh
            if (!avatar.getContentType().startsWith("image/")) {
                throw new RuntimeException("File không phải ảnh");
            }

            // 2. Tạo tên file mới (tránh trùng)
            String fileName = System.currentTimeMillis() + "_" + avatar.getOriginalFilename();

            // 3. Đường dẫn lưu trong server
            String uploadDir = "D:/shopbanquanao/src/assets/avatar/";

            // Tạo thư mục nếu chưa tồn tại
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // 4. Lưu ảnh mới
            String filePath = uploadDir + fileName;
            avatar.transferTo(new File(filePath));

            // 5. Xóa ảnh cũ nếu có
            if (user.getAvatar() != null) {
                File old = new File(user.getAvatar());
                if (old.exists()) old.delete();
            }

            // 6. Lưu đường dẫn mới vào DB
            user.setAvatar(filePath);
        }
        userRepository.save(user);
        return user.getAvatar();
    }

}
