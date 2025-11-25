//package com.Nhom19.shopQuanAo.Controller;
//
//import com.Nhom19.shopQuanAo.DTO.Request.Admin.ThemDmRequest;
//import com.Nhom19.shopQuanAo.DTO.Response.Admin.ThemDmResponse;
//import com.Nhom19.shopQuanAo.entity.DmCha;
//import com.Nhom19.shopQuanAo.repository.DmChaRepository;
//import com.Nhom19.shopQuanAo.service.DanhMucService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/danhmuc")
//public class DanhMucController {
//    @Autowired
//    DanhMucService danhMucService;
//
//    @PostMapping
//    public DmCha addDmCha(@RequestBody ThemDmRequest request) {
////    ThemDmResponse themDmResponse = new ThemDmResponse();
////    themDmResponse = danhMucService.themDanhMuc(request);
//        DmCha dmCha = danhMucService.themDanhMuc(request);
//        return dmCha;
//    }
////    @GetMapping
////    public List<DmCha> getDmCha() {
////        return danhMucService.getAll();
////    }
////    @GetMapping
////    public ThemDmResponse showDmCha(@RequestParam Integer id) {
////
////    }
//}
