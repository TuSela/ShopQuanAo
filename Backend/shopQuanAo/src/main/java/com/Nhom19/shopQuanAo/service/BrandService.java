package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.BrandCreationRequest;
import com.Nhom19.shopQuanAo.entity.Brands;
import com.Nhom19.shopQuanAo.mapper.BrandMapper;
import com.Nhom19.shopQuanAo.repository.BrandRository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRository brandRository;
    @Autowired
    BrandMapper brandMapper;

    public Brands createBrand(BrandCreationRequest request) {
//        Brands brand = new Brands();
//        System.out.println("ket qua request: "+ request.getTenHang() +request.getDiaChi());
//        brand.setTenHang(request.getTenHang());
//        brand.setSdt(request.getSdt());
//        brand.setEmail(request.getEmail());
//        brand.setDiaChi(request.getDiaChi());
//        brandRository.save(brand);

        Brands brand = brandMapper.toBrand(request);
        brandRository.save(brand);
        return brand;
    }
    public List<Brands> getAllBrands() {
        List<Brands> brands = brandRository.findAll();
        return brands;
    }
}
