package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreationProductRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ColorRequest;
import com.Nhom19.shopQuanAo.DTO.Request.SizeRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ProductDetail.CommentVariantResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.*;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.SPNamResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductDetailResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.mapper.ProductMapper;
import com.Nhom19.shopQuanAo.mapper.UserMapper;
import com.Nhom19.shopQuanAo.mapper.VariantMapper;
import com.Nhom19.shopQuanAo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductVariantRepo productVariantsRepo;
    @Autowired
    private ProductImagesRepo productImagesRepo;
    @Autowired
    private ProductCommentRepo productCommentRepo;
    @Autowired
      private UserMapper userMapper;
    @Autowired
    private   ProductColorRepo productColorRepo;
    @Autowired
    private   ProductSizeRepo productSizeRepo;
    @Autowired
    private ProductTypeRepo productTypeRepo;
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private VariantMapper variantMapper;
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


    //SHOW CHI TIẾT SẢN PHẨM
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
                            List<OrderItems> orderItems = orderItemRepo.findByOrders(pc.getOrders());
                            List<CommentVariantResponse> productVariants = res.getProductVariants();
                            orderItems.forEach(o -> {
                                ProductVariants pv =  o.getProductVariants();
                                CommentVariantResponse commentVariantResponse = variantMapper.toDTO2(pv);
                                commentVariantResponse.setTenKc(String.valueOf(pv.getSizes().getTenKc()));
                                commentVariantResponse.setTenMs(String.valueOf(pv.getColors().getTenMs()));

                                commentVariantResponse.setSoLuongDat(o.getSoLuong());
                                productVariants.add(commentVariantResponse);

                            });
                            res.setProductVariants(productVariants);


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


    ///LẤY RA SẢN PHẨM BIẾN THỂ
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


// THÊM SẢN PHẨM MỚI
    public Boolean createProduct(CreationProductRequest request) {
        Products products = productMapper.toEntity(request);

        ProductTypes productTypes = productTypeRepo.findById(request.getMaLoai()).orElseThrow(() -> new RuntimeException("Không tìm thấy màu"));
        products.setTypes(productTypes);
        productRepository.save(products);
        ProductImages productImages = new ProductImages();
        productImages.setProducts(products);
        productImages.setUrlImage(request.getDaiDien());
        productImages.setDaiDien(true);
        productImagesRepo.save(productImages);
        List<ColorRequest> colors = request.getColors();

        colors.forEach(color -> {
            ProductColors colorsX = productColorRepo.findById(color.getMaMs()).orElseThrow(() -> new RuntimeException("Không tìm thấy màu"));
            List<String> urlImages = color.getUrlImages();
            urlImages.forEach(url -> {
                ProductImages productImages2 = new ProductImages();
                productImages2.setUrlImage(url);
                productImages2.setProducts(products);
                productImages2.setProductColor(colorsX);
                productImages2.setDaiDien(false);
                productImagesRepo.save(productImages2);
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
