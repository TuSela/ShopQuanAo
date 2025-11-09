package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.entity.SPQuanAo;
import com.Nhom19.shopQuanAo.repository.SPQuanAoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service


public class SanPhamService {
    @Autowired
    private SPQuanAoRepo spQuanAoRepo;

    public List<SPQuanAo> getSPQuanAo(){
       return spQuanAoRepo.findAll();
    }
}
