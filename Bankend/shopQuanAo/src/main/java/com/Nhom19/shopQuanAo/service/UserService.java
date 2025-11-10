package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.TaoUsersRequest;
import com.Nhom19.shopQuanAo.entity.TaiKhoanKhachHang;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.repository.TaiKhoanKhachHangRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private TaiKhoanKhachHangRepo taiKhoanKhachHangRepo;

    public TaiKhoanKhachHang createUsers(TaoUsersRequest request){
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        // kiểm tra tồn tại
        if (taiKhoanKhachHangRepo.existsByTenTK(request.getTentk())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        TaiKhoanKhachHang users = new TaiKhoanKhachHang();
        users.setTenTK(request.getTentk());
        users.setDiaChi(request.getDiachi());
        users.setEmail(request.getEmail());
        users.setSdt(request.getSdt());
        users.setMatKhau(request.getMatkhau());
        users.setHovaTen(request.getHovaten());

        // Log debug trước khi save nếu cần:
        System.out.println("Saving user: " + users.getTenTK() + ", diaChi=" + users.getDiaChi());

        return taiKhoanKhachHangRepo.save(users);
    }
    public List<TaiKhoanKhachHang> getUsers(){
        return taiKhoanKhachHangRepo.findAll();
    }
}
