package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreateOrUpdateSizeRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductSizeResponse;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.ProductSizeMapper;
import com.Nhom19.shopQuanAo.repository.ProductSizeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSizeService {
    @Autowired
    private ProductSizeRepo productSizeRepo;
    @Autowired
    private ProductSizeMapper productSizeMapper;

    public List<ProductSizeResponse>  getAllProductSize()
    {
        List<ProductSizeResponse> productSizeResponses = new ArrayList<>();
        return productSizeRepo.findAll()
                .stream()
                .map(productSizeMapper::toProductSizeResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('SIZE_MANAGE')")
    public ProductSizeResponse getProductSize (int maKc) {
        var size = productSizeRepo.findById(maKc).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_SIZE_NOT_FOUND));

        return productSizeMapper.toProductSizeResponse(size);
    }
    @PreAuthorize("hasAuthority('SIZE_MANAGE')")
    public ProductSizeResponse createSize (CreateOrUpdateSizeRequest createOrUpdateSizeRequest) {
        if (productSizeRepo.existsByTenKc(createOrUpdateSizeRequest.getTenKc())) {
            throw new RuntimeException();
        }

        var size = productSizeMapper.createSize(createOrUpdateSizeRequest);
        size.setTenKc(createOrUpdateSizeRequest.getTenKc());
        productSizeRepo.save(size);

        return productSizeMapper.toProductSizeResponse(size);
    }
    @PreAuthorize("hasAuthority('SIZE_MANAGE')")
    public ProductSizeResponse updateSize(int maKc, CreateOrUpdateSizeRequest request) {
        var size = productSizeRepo.findById(maKc).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_SIZE_NOT_FOUND));

        productSizeMapper.updateSize(size, request);
        productSizeRepo.save(size);

        return productSizeMapper.toProductSizeResponse(size);
    }

    public boolean deleteSize(int maKc) {
        if (!productSizeRepo.existsById(maKc)) {
            throw  new AppException(ErrorCode.PRODUCT_SIZE_EXISTED);
        }

        productSizeRepo.deleteById(maKc);
        return true;
    }
}
