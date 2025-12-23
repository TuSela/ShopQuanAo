package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreationProductRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductResponse2;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Categories.PageResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV.ChiTietLoaiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.ProductDetailResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.service.ProductService;
import com.Nhom19.shopQuanAo.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/products")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTypeService productTypeService;
//    @GetMapping()
//    public ApiResponse<List<ProductResponse2>> showHomePage(){
//        Products Product =new Products();
//
//        ApiResponse<List<ProductResponse2>> apiResponse =new ApiResponse();
//        apiResponse.setResult(productService.getProducts());
//        return apiResponse;
//    }
    @GetMapping("/{maSp}")
    public ApiResponse<ProductDetailResponse> getProductDetail(@PathVariable("maSp") int maSp){
        ApiResponse<ProductDetailResponse> apiResponse =new ApiResponse();
        apiResponse.setResult(productService.getProductDetail(maSp));
        return apiResponse;
    }
    @GetMapping()
    public ApiResponse<List<ProductBestSellerResponse>> getProducts10(){
        ApiResponse<List<ProductBestSellerResponse>> apiResponse = new ApiResponse();
        List<ProductBestSellerResponse> productBestSellerResponseList =productService.getTopBestSeller();
        apiResponse.setResult(productBestSellerResponseList);
        return apiResponse;
    }
    @PostMapping
    public ApiResponse<Boolean> addProduct(@RequestBody CreationProductRequest request){
        ApiResponse<Boolean> apiResponse = new ApiResponse();
        apiResponse.setResult(productService.createProduct(request));
        return apiResponse;
    }
    @GetMapping("/search")
    public ApiResponse<List<ProductBestSellerResponse>> searchProducts(
            @RequestParam String keyword){

        ApiResponse<List<ProductBestSellerResponse>> response = new ApiResponse<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            response.setResult(List.of());
            response.setMessage("Keyword is empty");
            return response;
        }

        response.setResult(productService.searchByKeyword(keyword));
        return response;
    }

    @GetMapping("/by-doi-tuong")
    public ApiResponse<List<ProductBestSellerResponse>> getByDoiTuong(
            @RequestParam("doiTuong") String doiTuong) {

        ApiResponse<List<ProductBestSellerResponse>> response =
                new ApiResponse<>();

        response.setResult(
                productService.findByDoiTuong(doiTuong)
        );

        return response;
    }
    @GetMapping("/categories")
    public ApiResponse<PageResponse<ProductBestSellerResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "gia") String sort,
            @RequestParam(defaultValue = "desc") String direction,

            @RequestParam(required = false) String doiTuong,
            @RequestParam(required = false) String tenLoai,
            @RequestParam(required = false) Integer maLoai
    ) {
        //lấy ra danh mục liên quan
        ApiResponse<PageResponse<ProductBestSellerResponse>> response = new ApiResponse<>();
        List<ChiTietLoaiResponse> chiTietLoaiResponses =productTypeService.getChiTietLoai(doiTuong, tenLoai, maLoai);
        // lấy ra sản phẩm
        Page<ProductBestSellerResponse> page1 = productService.getProductsByTypes(
                page, size, sort, direction,
                doiTuong, tenLoai, maLoai
        );
        // lấy ra tiêu đề danh mục

        String TenDanhMuc= productTypeService.getTenPageDanhMuc(doiTuong, tenLoai, maLoai);
        response.setResult( new PageResponse<>(
                TenDanhMuc,
                page1.getContent(),
                chiTietLoaiResponses,
                page1.getNumber(),
                page1.getSize(),
                page1.getTotalElements(),
                page1.getTotalPages()
        ));
        return response;
    }
    @GetMapping("/product-list")
    public ApiResponse<List<ProductResponse2>> getProductsByType(){
        ApiResponse<List<ProductResponse2>> response = new ApiResponse<>();
        response.setResult(productService.getProducts());
        return response;
    }
//    @GetMapping("/product-list/{maSp}")
//    public ApiResponse<ProductResponse2> getProductsByType(@PathVariable("maSp") int maSp){
//        ApiResponse<ProductResponse2> response = new ApiResponse<>();
//        return response;
//    }
}
