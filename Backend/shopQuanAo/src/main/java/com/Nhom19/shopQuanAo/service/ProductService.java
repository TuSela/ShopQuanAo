package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreationProductRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Admin.UpdateProductRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ColorRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ImageRequest;
import com.Nhom19.shopQuanAo.DTO.Request.SizeRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductResponse2;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Categories.PageResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV.ChiTietLoaiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.*;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.SPNamResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('PRODUCT_MANAGE')")
    public List<ProductResponse2> getProducts() {
        return productRepository.findAllByOrderByMaSpDesc()
                .stream()
                .map(product -> {
                    ProductResponse2 productResponse = productMapper.toDTO(product);
                    productResponse.setChiTiet(null);
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

    @PreAuthorize("hasAuthority('PRODUCT_MANAGE')")
    public Boolean UpdateProduct(UpdateProductRequest request, Integer maSp){
        Products products = productRepository.findById(maSp).orElseThrow(()-> new RuntimeException("không tìm thấy sản phẩm"));
        products.setGia(request.getGia());
        products.setTenSp(request.getTenSp());
        products.setTypes(productTypeRepo.findById(request.getMaLoai()).orElse(products.getTypes()));
        products.setChiTiet(request.getChiTiet());
        productRepository.save(products);
        return true;
    }

    //SHOW CHI TIẾT SẢN PHẨM
    @Transactional
    public ProductDetailResponse getProductDetail(int id) {

        Products product = productRepository.findProductDetail(id)
                .orElseThrow(() -> new AppException(
                        ErrorCode.SP_DANG_BAO_TRI
                ));

        // ===== MAP CƠ BẢN =====
        ProductDetailResponse res = productMapper.toDTO2(product);
        res.setMaLoai(product.getTypes().getMaLoai());

        // ===== IMAGES =====
        List<String> images = productImagesRepo.findImageUrlsByProduct(product);
        res.setListAnhSP(images);

        productImagesRepo.findFirstByProductsAndDaiDienTrue(product)
                .ifPresent(img -> res.setAnhDaiDien(img.getUrlImage()));

        // ===== COMMENTS =====
        List<ProductComments> comments =
                productCommentRepo.findDetailComments(product);

        res.setSoLuongDanhGia(comments.size());

        List<ProductCommentResponse> commentResponses = comments.stream()
                .map(pc -> {
                    ProductCommentResponse c = new ProductCommentResponse();
                    c.setMaBl(pc.getMaBl());
                    c.setNgayTao(pc.getNgayTao());
                    c.setNoiDung(pc.getNoiDung());
                    c.setDiemDanhGia(pc.getDiemDanhGia());

                    // user
                    c.setUsers(userMapper.toUserCommentResponse(pc.getUsers()));

                    // variant
                    CommentVariantResponse v = new CommentVariantResponse();
                    v.setMaBienThe(pc.getProductVariants().getMaBienThe());
                    v.setTenMs(pc.getProductVariants().getColors().getTenMs());
                    v.setTenKc(pc.getProductVariants().getSizes().getTenKc());

                    // số lượng đặt (đã JOIN FETCH)
                    pc.getOrders().getItems().stream()
                            .filter(oi -> oi.getProductVariants()
                                    .equals(pc.getProductVariants()))
                            .findFirst()
                            .ifPresent(oi -> v.setSoLuongDat(oi.getSoLuong()));

                    c.setProductVariants(v);
                    return c;
                })
                .toList();

        res.setProductComments(commentResponses);

        // ===== VARIANTS (GOM THEO MÀU) =====
        List<ProductVariants> variants =
                productVariantsRepo.findVariantsForDetail(product);

        Map<Integer, ProductVariants> uniqueColorMap = new LinkedHashMap<>();
        for (ProductVariants v : variants) {
            uniqueColorMap.putIfAbsent(v.getColors().getMaMs(), v);
        }
        List<ColorResponse> colors = uniqueColorMap.values().stream()
                .map(v -> getColorDetail(product.getMaSp(), v.getColors().getMaMs()))
                .toList();
        res.setVariants(colors);
        return res;
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
    @PreAuthorize("hasAuthority('PRODUCT_MANAGE')")
    @Transactional
    public Boolean createProduct(CreationProductRequest request) {
        Products products = productMapper.toEntity(request);

        ProductTypes productTypes = productTypeRepo.findById(request.getMaLoai()).orElseThrow(() -> new RuntimeException("Không tìm thấy màu"));
        products.setTypes(productTypes);
        products.setTrangThai(false);
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
                productVariants.setTrangThai(false);
                productVariantsRepo.save(productVariants);
            });
        });
        return true;
    }

// tìm kiếm sản phẩm theo keyword
public PageResponse<ProductBestSellerResponse> searchByKeyword(
        int page,
        int size,
        String sortBy,
        String direction,
        String keyword
) {
    List<String> allowedSorts = List.of("gia", "tenSp", "danhGia");

    if (!allowedSorts.contains(sortBy)) {
        sortBy = "danhGia";
    }

    Sort.Direction sortDirection =
            "asc".equalsIgnoreCase(direction)
                    ? Sort.Direction.ASC
                    : Sort.Direction.DESC;

    Pageable pageable =
            PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

    // keyword rỗng → trả response rỗng
    if (keyword == null || keyword.trim().isEmpty()) {
       throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
    }
    Page<Products> productPage =
            productRepository.searchByKeyword(keyword.trim(), pageable);

    if (productPage.isEmpty()) {
        throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
    }

    List<Products> products = productPage.getContent();

    // 1 query lấy ảnh đại diện
    List<ProductImages> daiDienImages =
            productImagesRepo.findDaiDienByProducts(products);

    Map<Integer, String> imageMap = daiDienImages.stream()
            .collect(Collectors.toMap(
                    pi -> pi.getProducts().getMaSp(),
                    ProductImages::getUrlImage
            ));

    // ===== MAP DTO SẢN PHẨM =====
    List<ProductBestSellerResponse> dtoList =
            products.stream()
                    .map(product -> {
                        ProductBestSellerResponse dto =
                                productMapper.toDTO5(product);

                        dto.setUrlImage(imageMap.get(product.getMaSp()));
                        return dto;
                    })
                    .toList();

    // ===== MAP CHI TIẾT LOẠI (KHÔNG TRÙNG) =====
    List<ChiTietLoaiResponse> chiTietLoai =
            products.stream()
                    .map(p -> p.getTypes())
                    .distinct()
                    .map(t -> new ChiTietLoaiResponse(
                            t.getMaLoai(),
                            t.getChiTietLoai()
                    ))
                    .toList();

    return new PageResponse<>(
            keyword,
            dtoList,
            chiTietLoai,
            page,
            size,
            productPage.getTotalElements(),
            productPage.getTotalPages()
    );
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
    public PageResponse<ProductBestSellerResponse> getProductsBestSeller(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Pageable pageable = PageRequest.of(page, size);

    Page<Products> pageResult =
            productRepository.findBestSellerProducts3(pageable);
    List<Products> products = pageResult.getContent();
        // 1 query lấy ảnh đại diện
        List<ProductImages> daiDienImages =
                productImagesRepo.findDaiDienByProducts(products);

        Map<Integer, String> imageMap = daiDienImages.stream()
                .collect(Collectors.toMap(
                        pi -> pi.getProducts().getMaSp(),
                        ProductImages::getUrlImage
                ));
        // ===== MAP DTO SẢN PHẨM =====
        List<ProductBestSellerResponse> dtoList =
                products.stream()
                        .map(product -> {
                            ProductBestSellerResponse dto =
                                    productMapper.toDTO5(product);
                            dto.setUrlImage(imageMap.get(product.getMaSp()));
                            return dto;
                        })
                        .toList();
// ===== MAP CHI TIẾT LOẠI (KHÔNG TRÙNG) =====
        List<ChiTietLoaiResponse> chiTietLoai =
                products.stream()
                        .map(p -> p.getTypes())
                        .distinct()
                        .map(t -> new ChiTietLoaiResponse(
                                t.getMaLoai(),
                                t.getChiTietLoai()
                        ))
                        .toList();
    return new PageResponse<>(
            "Sản phẩm bán chạy nhất",
            dtoList,
            chiTietLoai, // nếu không cần chi tiết loại
            pageResult.getNumber(),
            pageResult.getSize(),
            pageResult.getTotalElements(),
            pageResult.getTotalPages()
            );
    }

    @PreAuthorize("hasAuthority('PRODUCT_MANAGE')")
    @Transactional
    public void enableProduct(int maSp) {
        var product = productRepository.findById(maSp).orElseThrow(
                ()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setTrangThai(true);
    }

    @PreAuthorize("hasAuthority('PRODUCT_MANAGE')")
    @Transactional
    public void disableProduct(int maSp) {
        var product = productRepository.findById(maSp).orElseThrow(
                ()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setTrangThai(false);

        product.getProductVariants().forEach(variant -> {
            variant.setTrangThai(false);
            productVariantsRepo.save(variant);
        });

        productRepository.save(product);
    }
}
