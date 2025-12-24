package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.AddressRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.OrderDetailRes.AddressResponse;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.entity.addresses;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.AddressMapper;
import com.Nhom19.shopQuanAo.repository.AddressRepository;
import com.Nhom19.shopQuanAo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressSevice {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AddressMapper addressMapper;

    @Autowired
    UserRepository userRepository;

    private Users getCurrentUser() {
        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String sdt = authentication.getName();
        Users users = userRepository.findBySdt(sdt);

        if (users == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        return users;
    }

    public List<AddressResponse> getmyaddress() {

        Users users = getCurrentUser();

        List<addresses> addrList = addressRepository.findByUsers(users);

        List<AddressResponse> responses = new ArrayList<>();
        addrList.forEach(addr ->
                responses.add(addressMapper.ToDTO(addr))
        );

        return responses;
    }

    public Boolean CreateAddress (AddressRequest request){
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepository.findBySdt(sdt);
        int maTk = users.getMaTk();
        addresses addr = addressMapper.ToEntity(request);
        addr.setUsers(users);
        addressRepository.save(addr);
        return true;
    }
    public Boolean DeleteAddress(Integer id){          addressRepository.deleteById(id);
        return true;
    }
    public addresses getAddressById(Integer id) {

        if (id == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        return addressRepository.findById(id)
                .orElseThrow(() ->
                        new AppException(ErrorCode.ADDRESS_NOT_EXISTED)
                );
    }
    public Boolean updateAddress(AddressRequest request, Integer id) {

        if (request == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        addresses addr = getAddressById(id);

        Users users = getCurrentUser();
        if (!addr.getUsers().getMaTk().equals(users.getMaTk())) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        addr.setSdt(request.getSdt());
        addr.setHoten(request.getHoten());
        addr.setEmail(request.getEmail());
        addr.setDiaChi(request.getDiaChi());
        addr.setTinhThanhPho(request.getTinhThanhPho());
        addr.setQuanHuyen(request.getQuanHuyen());
        addr.setPhuongXa(request.getPhuongXa());
        addressRepository.save(addr);
        return true;
    }
}
