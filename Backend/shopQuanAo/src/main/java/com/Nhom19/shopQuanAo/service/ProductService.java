package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreationProductRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ColorRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ProductRequest;
import com.Nhom19.shopQuanAo.DTO.Request.SizeRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.UserResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.*;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.SPNamResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductDetailResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ProductResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.mapper.ProductMapper;
import com.Nhom19.shopQuanAo.mapper.UserMapper;
import com.Nhom19.shopQuanAo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductVariantRepo productVariantsRepo;
    @Autowired
    private ProductImagesRepo productImagesRepo;
    @Autowired
    private ProductCommentRepo productCommentRepo;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductColorRepo productColorRepo;
    @Autowired
    ProductSizeRepo productSizeRepo;
    @Autowired
    ProductTypeRepo productTypeRepo;


    public List<ProductResponse> getProducts(){
       return   productRepository.findAll().stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    public List<ProductBestSellerResponse>getTopBestSeller(){
        return productRepository.getProductsForHome();
    }
//    public List<ProductBestSellerResponse> getAllProducts(){
//        return productRepository.getSanPhamTheoMau();
//    }
    public List<SPNamResponse>getSpNam(){
        return productRepository.findTopSellingByDoiTuongNative("Nam",10);
    }
    public List<ProductBestSellerResponse> getProduct10(){
        return productRepository.findAnyTenProductsNative();
    }






    public ProductDetailResponse getProductDetail(int id) {
        Products products = productRepository.getById(id);
        Set<ProductVariants> productVariant = productVariantsRepo.findByProducts(products);
        Set<ProductImages> listAnhSP = productImagesRepo.findByProducts(products);

        ProductDetailResponse productDetailResponse = productMapper.toDTO2(products);

        List<String> images = listAnhSP.stream()
                .map(ProductImages::getUrlImage)
                .toList();

        productDetailResponse.setListAnhSP(images);


        // Lấy danh sách comment
        Set<ProductComments> productComments = productCommentRepo.getByMaBl(id);

        // Set số lượng đánh giá
        productDetailResponse.setSoLuongDanhGia(productComments.size());

        // Map comments
        productDetailResponse.setProductComments(
                productComments.stream()
                        .map(pc -> {
                            Users users = pc.getUsers();
                            UserCommentResponse userResponse = userMapper.toUserCommentResponse(users);

                            ProductCommentResponse res = new ProductCommentResponse();
                            res.setMaBl(pc.getMaBl());
                            res.setNgayTao(pc.getNgayTao());
                            res.setNoiDung(pc.getNoiDung());
                            res.setDiemDanhGia(pc.getDiemDanhGia());
                            res.setUsers(userResponse);

                            return res;
                        })
                        .collect(Collectors.toSet())
        );
        // Map variant
        productDetailResponse.setVariants(
                productVariant.stream()
                        .map(variant -> {
                            ProductColors productColors = variant.getColors();
                            System.out.println("maMS: "+ productColors.getMaMs());
                            ColorResponse colorDetail= getColorDetail(products.getMaSp(),productColors.getMaMs());

                            return colorDetail;
                        })
                        .collect(Collectors.toSet())
        );

        return productDetailResponse;
    }
    public ColorResponse getColorDetail(Integer maSp, Integer maMs) {
        ColorResponse res = new ColorResponse();
        ProductColors color = productColorRepo.findById(maMs)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu"));

        res.setMaMs(color.getMaMs());
        res.setTenMs(color.getTenMs());

        // Lấy 1 ảnh đầu tiên
        List<ProductImages> images = productImagesRepo.getImagesByProductAndColor(maSp, maMs);
        if (!images.isEmpty()) {
            res.setUrlImages(images.get(0).getUrlImage());
        }

        // Lấy size theo màu
        List<ProductVariants> variants = productVariantsRepo.getSizesByProductAndColor(maSp, maMs);

        List<ColorSizeResponse> sizeList = variants.stream().map(v -> {
            ColorSizeResponse s = new ColorSizeResponse();
            s.setMaKc(v.getSizes().getMaKc());
            s.setTenKc(v.getSizes().getTenKc());
            s.setSoluong(v.getSoluong());
            return s;
        }).toList();

        res.setSizes(sizeList);

        return res;
    }


    public Boolean createProduct(CreationProductRequest request) {
        Products products = productMapper.toEntity(request);

        ProductTypes productTypes = productTypeRepo.findById(request.getMaLoai()).orElseThrow(() -> new RuntimeException("Không tìm thấy màu"));
        products.setTypes(productTypes);
        productRepository.save(products);
        List<ColorRequest> colors = request.getColors();

        colors.forEach(color -> {
            ProductColors colorsX = productColorRepo.findById(color.getMaMs()).orElseThrow(() -> new RuntimeException("Không tìm thấy màu"));
            List<String> urlImages = color.getUrlImages();
            urlImages.forEach(url -> {
                ProductImages productImages = new ProductImages();
                productImages.setUrlImage(url);
                productImages.setProducts(products);
                productImages.setProductColor(colorsX);
                productImages.setDaiDien(false);
                productImagesRepo.save(productImages);
            });

            List<SizeRequest> size = color.getSizes();

            size.forEach(sizeRequest -> {
                ProductSizes sizes = productSizeRepo.findById(sizeRequest.getMaKc()).orElseThrow(() -> new RuntimeException("Không tìm thấy size"));
                ProductVariants productVariants = new ProductVariants();
                productVariants.setProducts(products);
                productVariants.setColors(colorsX);
                productVariants.setSizes(sizes);
                productVariants.setSoluong(sizeRequest.getSoluong());
                productVariantsRepo.save(productVariants);
            });
        });

        return true;
    }


}
