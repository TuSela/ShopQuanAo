package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.TypeCreationRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductTypeResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV.CategoryDTO;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV.NavMenuDTO;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import com.Nhom19.shopQuanAo.mapper.ProductTypeMapper;
import com.Nhom19.shopQuanAo.repository.ProductTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {
    @Autowired
    ProductTypeRepo productTypeRepo;
    @Autowired
    ProductTypeMapper productTypeMapper;

    public ProductTypes addProductType(TypeCreationRequest request){
        ProductTypes productType = productTypeMapper.toProductTypes(request);
        return productTypeRepo.save(productType);
    }

    public List<ProductTypeResponse> getTypes (){
         List<ProductTypes> productTypes = productTypeRepo.findAll();
         return productTypes.stream()
                 .map(productTypeMapper::toProductTypeResponse)
                 .collect(Collectors.toList());
    }

    public List<NavMenuDTO> buildNavMenu() {

        List<ProductTypes> list = productTypeRepo.findAllForMenu();

        // Group theo doi_tuong → ten_loai → chi_tiet_loai
        Map<String, Map<String, List<String>>> grouped =
                list.stream()
                        .collect(Collectors.groupingBy(
                                ProductTypes::getDoiTuong,
                                Collectors.groupingBy(
                                        ProductTypes::getTenLoai,
                                        Collectors.mapping(
                                                ProductTypes::getChiTietLoai,
                                                Collectors.toList()
                                        )
                                )
                        ));

        // Build DTO
        return grouped.entrySet().stream()
                .map(doiTuongEntry -> {
                    NavMenuDTO nav = new NavMenuDTO();
                    nav.setDoiTuong(doiTuongEntry.getKey());

                    List<CategoryDTO> categories =
                            doiTuongEntry.getValue().entrySet().stream()
                                    .map(catEntry -> {
                                        CategoryDTO cat = new CategoryDTO();
                                        cat.setTenLoai(catEntry.getKey());
                                        cat.setChiTietLoai(catEntry.getValue());
                                        return cat;
                                    })
                                    .toList();

                    nav.setCategories(categories);
                    return nav;
                })
                .toList();
    }
}
