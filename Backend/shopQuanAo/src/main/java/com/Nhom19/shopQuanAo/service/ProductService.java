package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreationProductRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Admin.UpdateProductRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ColorRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ImageRequest;
import com.Nhom19.shopQuanAo.DTO.Request.SizeRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductResponse2;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.*;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.SPNamResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.mapper.ProductMapper;
import com.Nhom19.shopQuanAo.mapper.UserMapper;
import com.Nhom19.shopQuanAo.mapper.VariantMapper;
import com.Nhom19.shopQuanAo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public List<ProductResponse2> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> {
                    ProductResponse2 productResponse = productMapper.toDTO(product);

                    productImagesRepo.getDaiDienByProducts(product.getMaSp())
                            .ifPresent(img ->
                                    productResponse.setAnhDaiDien(img.getUrlImage())
                            );

                    return productResponse;
                })
                .toList();
    }

    public List<ProductBestSellerResponse>getTopBestSeller(){
        List<ProductBestSellerResponse> result =
                productRepository.findBestSellerProducts(PageRequest.of(0, 10));

        if (result.size() < 10) {
            int remain = 10 - result.size();

            List<Integer> ids = result.stream()
                    .map(ProductBestSellerResponse::getMaSp)
                    .toList();

            List<ProductBestSellerResponse> random =
                    productRepository.findRandomProductsExclude(
                            ids,
                            PageRequest.of(0, remain)
                    );

            result.addAll(random);
        }

        return result;

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

    public Boolean UpdateProduct(UpdateProductRequest request, Integer maSp){
        Products products = productRepository.findById(maSp).orElseThrow(()-> new RuntimeException("không tìm thấy sản phẩm"));
        products.setGia(products.getGia());
        products.setTenSp(request.getTenSp());
        products.setTypes(productTypeRepo.findById(request.getMaLoai()).orElse(products.getTypes()));
        products.setChiTiet(request.getChiTiet());
        productRepository.save(products);
        return true;
    }

    //SHOW CHI TIẾT SẢN PHẨM
    public ProductDetailResponse getProductDetail(int id) {
        Products products = productRepository.getById(id);

        List<ProductImages> listAnhSP = productImagesRepo.findByProductsOrderByDaiDienDesc(products);
        System.out.println("listAnhSP: " + listAnhSP.toString());
        ProductDetailResponse productDetailResponse = productMapper.toDTO2(products);

        List<String> images = listAnhSP.stream()
                .map(ProductImages::getUrlImage)
                .toList();

        productDetailResponse.setListAnhSP(images);
        productDetailResponse.setMaLoai(products.getTypes().getMaLoai());
        // Ảnh đầu tiên chắc chắn là đại diện
        productDetailResponse.setAnhDaiDien(
                listAnhSP.isEmpty() ? null : listAnhSP.get(0).getUrlImage()
        );
        // Lấy danh sách comment
        List<ProductComments> productComments = productCommentRepo.findByProducts(products);
        System.out.println("productComments: " + productComments.toString());
        // Set số lượng đánh giá
        productDetailResponse.setSoLuongDanhGia(productComments.size());
        // Map comments
        productDetailResponse.setProductComments(
                productComments.stream()
                        .map(pc -> {
                            Users users = pc.getUsers();
                            UserCommentResponse userResponse = userMapper.toUserCommentResponse(users);

                            ProductResponse res = new ProductResponse();
                            res.setMaBl(pc.getMaBl());
                            res.setNgayTao(pc.getNgayTao());
                            res.setNoiDung(pc.getNoiDung());
                            res.setDiemDanhGia(pc.getDiemDanhGia());
                            res.setUsers(userResponse);

                            CommentVariantResponse productVariants = new CommentVariantResponse();
                            productVariants.setMaBienThe(pc.getProductVariants().getMaBienThe());
                            OrderItems orderItems = orderItemRepo.findByOrdersAndProductVariants(pc.getOrders(),pc.getProductVariants()).orElseThrow(()-> new RuntimeException("Sản phẩm trong đơn hàng không tồn tại"));

                            productVariants.setSoLuongDat(orderItems.getSoLuong());
                            productVariants.setTenMs(pc.getProductVariants().getColors().getTenMs());
                            productVariants.setTenKc(pc.getProductVariants().getSizes().getTenKc());

                            res.setProductVariants(productVariants);

                            return res;
                        })
                        .collect(Collectors.toList())
        );

        // Map variant
        List<ProductVariants> variants =
                productVariantsRepo.findByProductOrderByImageDaiDien(products);

        Map<Integer, ProductVariants> uniqueColorMap = new LinkedHashMap<>();

        for (ProductVariants v : variants) {
            Integer maMs = v.getColors().getMaMs();

            // chỉ giữ bản ghi đầu tiên (đã đúng thứ tự đại diện)
            uniqueColorMap.putIfAbsent(maMs, v);
        }

        List<ColorResponse> colors = uniqueColorMap.values().stream()
                .map(v -> getColorDetail(products.getMaSp(), v.getColors().getMaMs()))
                .toList();

        productDetailResponse.setVariants(colors);
        return productDetailResponse;
    }

    ///LẤY RA SẢN PHẨM BIẾN THỂ
    @Transactional()
    public ColorResponse getColorDetail(Integer maSp, Integer maMs) {

        ProductColors color = productColorRepo.findById(maMs)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu"));

        ColorResponse res = new ColorResponse();
        res.setMaMs(color.getMaMs());
        res.setTenMs(color.getTenMs());

        // Ảnh đã được sắp xếp: đại diện đứng đầu
        List<ProductImages> images =
                productImagesRepo.getImagesByProductAndColorOrderByDaiDien(maSp, maMs);

        res.setUrlImages(
                images.isEmpty() ? null : images.get(0).getUrlImage()
        );

        // Size theo màu
        List<ProductVariants> variants =
                productVariantsRepo.getSizesByProductAndColor(maSp, maMs);

        List<ColorSizeResponse> sizeList = variants.stream()
                .map(v -> {
                    ColorSizeResponse s = new ColorSizeResponse();
                    s.setMaKc(v.getSizes().getMaKc());
                    s.setTenKc(v.getSizes().getTenKc());
                    s.setSoluong(v.getSoluong());
                    return s;
                })
                .toList();

        res.setSizes(sizeList);

        return res;
    }


// THÊM SẢN PHẨM MỚI
    @Transactional
    public Boolean createProduct(CreationProductRequest request) {
        Products products = productMapper.toEntity(request);

        ProductTypes productTypes = productTypeRepo.findById(request.getMaLoai()).orElseThrow(() -> new RuntimeException("Không tìm thấy màu"));
        products.setTypes(productTypes);
        productRepository.save(products);

        List<ColorRequest> colors = request.getColors();
        colors.forEach(color -> {
            ProductColors colorsX = productColorRepo.findById(color.getMaMs()).orElseThrow(() -> new RuntimeException("Không tìm thấy màu"));
            List<ImageRequest> imageRequests = color.getUrlImages();
            imageRequests.forEach(imageRequest -> {
                ProductImages productImages2 = new ProductImages();
                productImages2.setUrlImage(imageRequest.getUrl());
                productImages2.setProducts(products);
                productImages2.setProductColor(colorsX);
                productImages2.setDaiDien(imageRequest.getDaiDien());
                productImages2.setDaiDienMau(imageRequest.getDaiDienMau());
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

// tìm kiếm sản phẩm theo keyword
    public List<ProductBestSellerResponse> searchByKeyword(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }

        List<Products> products =
                productRepository.searchByKeyword(keyword.trim());

        if (products.isEmpty()) {
            return List.of();
        }

        // 1 query lấy toàn bộ ảnh đại diện
        List<ProductImages> daiDienImages =
                productImagesRepo.findDaiDienByProducts(products);

        // Map productId -> urlImage
        Map<Integer, String> imageMap = daiDienImages.stream()
                .collect(Collectors.toMap(
                        pi -> pi.getProducts().getMaSp(),
                        ProductImages::getUrlImage
                ));

        return products.stream()
                .map(product -> {
                    ProductBestSellerResponse dto =
                            productMapper.toDTO5(product);

                    dto.setUrlImage(
                            imageMap.get(product.getMaSp())
                    );

                    return dto;
                })
                .toList();
    }


    // tìm theo đối tượng
    public List<ProductBestSellerResponse> findByDoiTuong(String doiTuong) {

        if (doiTuong == null || doiTuong.trim().isEmpty()) {
            return List.of();
        }
        Pageable pageable = PageRequest.of(0, 10);

        List<Products> products =
                productRepository.findByDoiTuong(doiTuong.trim(),pageable);

        if (products.isEmpty()) {
            return List.of();
        }
        // 1 query lấy toàn bộ ảnh đại diện
        List<ProductImages> daiDienImages =
                productImagesRepo.findDaiDienByProducts(products);

        // Map productId -> urlImage
        Map<Integer, String> imageMap = daiDienImages.stream()
                .collect(Collectors.toMap(
                        pi -> pi.getProducts().getMaSp(),
                        ProductImages::getUrlImage
                ));

        return products.stream()
                .map(product -> {
                    ProductBestSellerResponse dto =
                            productMapper.toDTO5(product);

                    dto.setUrlImage(
                            imageMap.get(product.getMaSp())
                    );

                    return dto;
                })
                .toList();
    }

    //danh mục sản phẩm
    public Page<ProductBestSellerResponse> getProductsByTypes(
            int page,
            int size,
            String sortBy,
            String direction,
            String doiTuong,
            String tenLoai,
            Integer chiTietLoai
    ) {
    /* ===============================
       1. Chuẩn hóa filter rỗng
       =============================== */
        if (doiTuong != null && doiTuong.isBlank()) doiTuong = null;
        if (tenLoai != null && tenLoai.isBlank()) tenLoai = null;

    /* ===============================
       2. Validate sort
       =============================== */
        List<String> allowedSorts =
                List.of("gia", "tenSp", "danhGia");

        if (!allowedSorts.contains(sortBy)) {
            sortBy = "danhGia";
        }

        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

    /* ===============================
       3. Tìm types
       =============================== */
        List<ProductTypes> types =
                productTypeRepo.search(doiTuong, tenLoai, chiTietLoai);

        if (types.isEmpty()) {
            return Page.empty(pageable);
        }

    /* ===============================
       4. Tìm products theo types
       =============================== */
        Page<Products> productsPage =
                productRepository.findByTypesIn(types, pageable);

        List<Products> products = productsPage.getContent();

        if (products.isEmpty()) {
            return productsPage.map(p -> null);
        }

    /* ===============================
       5. Lấy ảnh đại diện
       =============================== */
        List<ProductImages> daiDienImages =
                productImagesRepo.findDaiDienByProducts(products);

        Map<Integer, String> imageMap =
                daiDienImages.stream()
                        .collect(Collectors.toMap(
                                pi -> pi.getProducts().getMaSp(),
                                ProductImages::getUrlImage
                        ));

    /* ===============================
       6. Map sang DTO
       =============================== */
        return productsPage.map(product -> {
            ProductBestSellerResponse dto =
                    productMapper.toDTO5(product);

            dto.setUrlImage(
                    imageMap.get(product.getMaSp())
            );

            return dto;
        });
    }

}
