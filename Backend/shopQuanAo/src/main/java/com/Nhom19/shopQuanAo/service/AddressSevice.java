package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.AddressRequest;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.entity.addresses;
import com.Nhom19.shopQuanAo.mapper.AddressMapper;
import com.Nhom19.shopQuanAo.repository.AddressRepository;
import com.Nhom19.shopQuanAo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AddressSevice {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AddressMapper addressMapper;

    @Autowired
    UserRepository userRepository;
    public addresses getmyaddress (){
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepository.findBySdt(sdt);
        int maTk = users.getMaTk();
        System.out.println("maTk:"+maTk);
        addresses addr = addressRepository.findByMaTk(maTk);
        return addr;
    }
    public Boolean CreateAddress (AddressRequest request){
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepository.findBySdt(sdt);
        int maTk = users.getMaTk();
        addresses addr = addressMapper.ToEntity(request);
        addr.setMaTk(maTk);
        addressRepository.save(addr);
        return true;
    }
    public Boolean DeleteAddress(Integer id){          addressRepository.deleteById(id);
        return true;
    }
    public addresses getAddressById(Integer id){
        return addressRepository.findById(id).get();
    }
    public Boolean UpdateAddress(AddressRequest request, Integer id){
        addresses addr = getAddressById(id);
        addr.setSdt(request.getSdt());
        addr.setHoten(request.getHoten());
        addr.setEmail(request.getEmail());
        addr.setDiaChi(request.getDiaChi());
        addr.setQuanHuyen(request.getQuanHuyen());
        addr.setPhuongXa(request.getPhuongXa());

        addressRepository.save(addr);
        return true;
    }
}
