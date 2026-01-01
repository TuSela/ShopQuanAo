package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.TypeCreationRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductTypeResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV.CategoryDTO;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV.ChiTietLoaiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV.NavMenuDTO;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.ProductTypeMapper;
import com.Nhom19.shopQuanAo.repository.ProductTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.text.Normalizer;


@Service
public class ProductTypeService {
    @Autowired
    ProductTypeRepo productTypeRepo;
    @Autowired
    ProductTypeMapper productTypeMapper;

    public ProductTypeResponse addProductType(TypeCreationRequest request){
        if (productTypeRepo.existsByChiTietLoai(request.getChiTietLoai())){
            throw new RuntimeException();
        }

        ProductTypes productType = productTypeMapper.toProductTypes(request);
        productTypeRepo.save(productType);
        return productTypeMapper.toProductTypeResponse(productType);
    }

    public List<ProductTypeResponse> getTypes (){
         List<ProductTypes> productTypes = productTypeRepo.findAll();
         return productTypes.stream()
                 .map(productTypeMapper::toProductTypeResponse)
                 .collect(Collectors.toList());
    }

    public List<ProductTypeResponse> getALLTypesByNam (){
        List<ProductTypes> productTypes = productTypeRepo.findAll();
        return productTypes.stream()
                .map(productTypeMapper::toProductTypeResponse)
                .collect(Collectors.toList());
    }

    private String normalize(String input) {
        if (input == null) return "";
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }

    public List<NavMenuDTO> buildNavMenu() {

        List<ProductTypes> list = productTypeRepo.findAllForMenuSorted();

        // Group theo doiTuong -> tenLoai -> List<ProductTypes>
        Map<String, Map<String, List<ProductTypes>>> grouped =
                list.stream()
                        .collect(Collectors.groupingBy(
                                ProductTypes::getDoiTuong,
                                Collectors.groupingBy(ProductTypes::getTenLoai)
                        ));

        return grouped.entrySet().stream()

                // 1️⃣ Sort doiTuong A-Z
                .sorted(Comparator.comparing(e -> normalize(e.getKey())))

                .map(doiTuongEntry -> {

                    NavMenuDTO nav = new NavMenuDTO();
                    nav.setDoiTuong(doiTuongEntry.getKey());

                    List<CategoryDTO> categories =
                            doiTuongEntry.getValue().entrySet().stream()

                                    // 2️⃣ Sort tenLoai A-Z
                                    .sorted(Comparator.comparing(e -> normalize(e.getKey())))

                                    .map(catEntry -> {

                                        CategoryDTO cat = new CategoryDTO();
                                        cat.setTenLoai(catEntry.getKey());

                                        // 3️⃣ Map chiTietLoai + maLoai
                                        List<ChiTietLoaiResponse> chiTietLoai =
                                                catEntry.getValue().stream()

                                                        // tránh trùng maLoai
                                                        .collect(Collectors.toMap(
                                                                ProductTypes::getMaLoai,
                                                                pt -> {
                                                                    ChiTietLoaiResponse dto =
                                                                            new ChiTietLoaiResponse();
                                                                    dto.setMaLoai(pt.getMaLoai());
                                                                    dto.setChiTietLoai(pt.getChiTietLoai());
                                                                    return dto;
                                                                },
                                                                (a, b) -> a
                                                        ))
                                                        .values().stream()

                                                        // 4️⃣ Sort chiTietLoai A-Z
                                                        .sorted(Comparator.comparing(
                                                                dto -> normalize(dto.getChiTietLoai())
                                                        ))
                                                        .collect(Collectors.toList());

                                        cat.setDanhMuc(chiTietLoai);
                                        return cat;
                                    })
                                    .collect(Collectors.toList());

                    nav.setCategories(categories);
                    return nav;
                })
                .collect(Collectors.toList());
    }
    public List<ChiTietLoaiResponse> getChiTietLoai(String doiTuong, String tenLoai, Integer chiTietLoai) {
        if (doiTuong != null && doiTuong.isBlank()) doiTuong = null;
        if (tenLoai != null && tenLoai.isBlank()) tenLoai = null;
        if (chiTietLoai != null){
            ProductTypes productTypes = productTypeRepo.findById(chiTietLoai).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_TYPE_NOT_FOUND));
            doiTuong = productTypes.getDoiTuong();
            tenLoai = productTypes.getTenLoai();
        }
        List<ProductTypes> productTypes = productTypeRepo.findByDoiTuongAndTenLoai(doiTuong,tenLoai);
        return productTypes.stream().map(productTypeMapper::toChiTietLoaiResponse).collect(Collectors.toList());
    }

    public String getTenPageDanhMuc(String doiTuong, String tenLoai, Integer chiTietLoai) {

        String TenPageDanhMuc = tenLoai + " " + doiTuong;
        if (doiTuong == null ) {
            doiTuong = "";
            TenPageDanhMuc = tenLoai;
        }
        if (tenLoai == null ) {
            TenPageDanhMuc = doiTuong;
        }
        if (chiTietLoai != null) {
            ProductTypes productTypes = productTypeRepo.findById(chiTietLoai).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_TYPE_NOT_FOUND));
            TenPageDanhMuc = productTypes.getChiTietLoai() + " " + productTypes.getDoiTuong();
        }
        return TenPageDanhMuc;
    }

    public ProductTypeResponse getProductType (int maLoai){
        var  productType = productTypeRepo.findById(maLoai).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_TYPE_NOT_FOUND));
        return productTypeMapper.toProductTypeResponse(productType);
    }

    public ProductTypeResponse updateProductType (int maLoai, TypeCreationRequest request){
        var productType = productTypeRepo.findById(maLoai).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_TYPE_NOT_FOUND));

        productTypeMapper.updateProductTypes(productType, request);
        productTypeRepo.save(productType);

        return productTypeMapper.toProductTypeResponse(productType);
    }

    public boolean deleteProductType (int maLoai){
        if (!productTypeRepo.existsById(maLoai)){
            return false;
        }
        productTypeRepo.deleteById(maLoai);
        return true;
    }

}
