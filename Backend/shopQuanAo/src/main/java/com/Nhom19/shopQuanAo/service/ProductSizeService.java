package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreateOrUpdateSizeRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductSizeResponse;
import com.Nhom19.shopQuanAo.exception.DuplicateSizeException;
import com.Nhom19.shopQuanAo.exception.SizeNotFoundException;
import com.Nhom19.shopQuanAo.mapper.ProductSizeMapper;
import com.Nhom19.shopQuanAo.repository.ProductSizeRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ProductSizeResponse getProductSize (int maKc) {
        var size = productSizeRepo.findById(maKc).orElseThrow(SizeNotFoundException::new);

        return productSizeMapper.toProductSizeResponse(size);
    }

    public ProductSizeResponse createSize (CreateOrUpdateSizeRequest createOrUpdateSizeRequest) {
        if (productSizeRepo.existsByTenKc(createOrUpdateSizeRequest.getTenKc())) {
            throw new DuplicateSizeException();
        }

        var size = productSizeMapper.createSize(createOrUpdateSizeRequest);
        size.setTenKc(createOrUpdateSizeRequest.getTenKc());
        productSizeRepo.save(size);

        return productSizeMapper.toProductSizeResponse(size);
    }

    public ProductSizeResponse updateSize(int maKc, CreateOrUpdateSizeRequest request) {
        var size = productSizeRepo.findById(maKc).orElseThrow(SizeNotFoundException::new);

        productSizeMapper.updateSize(size, request);
        productSizeRepo.save(size);

        return productSizeMapper.toProductSizeResponse(size);
    }

    public boolean deleteSize(int maKc) {
        if (!productSizeRepo.existsById(maKc)) {
            return false;
        }

        productSizeRepo.deleteById(maKc);
        return true;
    }
}
