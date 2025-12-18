package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.TypeCreationRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductTypeResponse;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import com.Nhom19.shopQuanAo.exception.DuplicateTypeException;
import com.Nhom19.shopQuanAo.exception.TypeNotFoundException;
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

    public ProductTypeResponse addProductType(TypeCreationRequest request){
        if (!productTypeRepo.existsByTenLoai(request.getTenLoai())){
            throw new DuplicateTypeException();
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

    public ProductTypeResponse getProductType (int maLoai){
        var  productType = productTypeRepo.findById(maLoai).orElseThrow(TypeNotFoundException::new);
        return productTypeMapper.toProductTypeResponse(productType);
    }

    public ProductTypeResponse updateProductType (int maLoai, TypeCreationRequest request){
        var productType = productTypeRepo.findById(maLoai).orElseThrow(TypeNotFoundException::new);

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
