package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.DmConRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Admin.ThemDmRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.DmChaResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ThemDmResponse;
import com.Nhom19.shopQuanAo.entity.DmCha;
import com.Nhom19.shopQuanAo.entity.DmCon;
import com.Nhom19.shopQuanAo.mapper.DmMapper;
import com.Nhom19.shopQuanAo.repository.DmChaRepository;
import com.Nhom19.shopQuanAo.repository.DmConRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DanhMucService {
    @Autowired
    DmMapper dmMapper;
    @Autowired
    DmChaRepository dmChaRepository;
    @Autowired
    private DmConRepository dmConRepository;

    public ThemDmResponse themDanhMuc (ThemDmRequest request){
        System.out.println("DanhMucService :"+ request.getName()+ request.getStatus()+ request.getDmCon());
        DmCha dmCha= new DmCha();
        dmCha.setName(request.getName());
        dmCha.setStatus(request.getStatus());
        var resulst = dmChaRepository.save(dmCha);

        request.getDmCon().forEach(tenDmCon -> {
            DmCon dmCon = new DmCon();
            dmCon.setName(tenDmCon);
            dmCon.setDmCha(dmCha);
            dmConRepository.save(dmCon);
        });

        ThemDmResponse themDmResponse = new ThemDmResponse();
        themDmResponse.setName( dmCha.getName());
        themDmResponse.setId( dmCha.getId());
        themDmResponse.setDmCon(dmCha.getDmCon());

        return themDmResponse;
    }
//
//    public DmCon themDmCon(DmConRequest request){
//        DmCon dmCon= new DmCon();
//
//        dmCon.setName(request.getName());
//        dmCon.setStatus(request.getStatus());
//
//        dmCon.setId(request.getId());
//        DmCha dmCha = dmChaRepository.findById(request.getId());
//
//        dmCon.getDmCha(dmCha);
//        dmConRepository.save(dmCon);
//        return dmCon;
//    }
//    public List<DmChaResponse> getAll (){
//        return List<DmChaResponse> dmChaList = dmChaRepository.findAll().get();
//
//    }
}
