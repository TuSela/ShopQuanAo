package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.entity.SPQuanAo;
import com.Nhom19.shopQuanAo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeCotroller {
@Autowired
private SanPhamService sanPhamService;
    @GetMapping()
    public ApiResponse<List<SPQuanAo>> showHomePage(){
        SPQuanAo SPQuanAo=new SPQuanAo();

       ApiResponse<List<SPQuanAo>> apiResponse =new ApiResponse();
       apiResponse.setResult(sanPhamService.getSPQuanAo());
       return apiResponse;
    }

}
