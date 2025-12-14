package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.TypeCreationRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductTypeResponse;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import com.Nhom19.shopQuanAo.mapper.ProductTypeMapper;
import com.Nhom19.shopQuanAo.repository.ProductTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
