package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.CreateCartRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Customer.UpdateMyCartReq;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart.CreatCartResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart.MyCartItemResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart.MyCartResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.CommentVariantResponse;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.entityCompositeKey.CartItemId;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.ProductMapper;
import com.Nhom19.shopQuanAo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private ProductVariantRepo productVariantRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    AuthenticationService authenticationService;

//    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public CreatCartResponse createCart(CreateCartRequest request, Integer Id) {

        Cart cart = new Cart();

        Users user = userRepo.findById(Id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!cartRepository.existsByUsers(user)) {

            cart.setUsers(user);
            cart.setTongTien(new BigDecimal("0"));
            cart.setNgayTao(LocalDateTime.now());
            cart.setNgaySua(LocalDateTime.now());
            cartRepository.save(cart);
        }
        Cart cart1 = cartRepository.findByUsers(user).orElseThrow(()->new AppException(ErrorCode.CART_NOT_EXISTED));


        CartItems cartItems = new CartItems();

        cartItems.setCart(cart1);
        cartItems.setSoluong(request.getSoLuong());
        ProductVariants productVariants = productVariantRepo.findByProductAndColorAndSize(request.getMaSp(),  request.getMaMs(), request.getMaKc()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED));

        cartItems.setProductVariants(productVariants);
        CartItemId id = new CartItemId(
                cart1.getMaGh(),
                productVariants.getMaBienThe()
        );
        cartItems.setId(id);

        Products products = productRepo.findById(request.getMaSp())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        boolean cartItemsX = cartItemRepo.existsByCartAndProductVariants(cart1, productVariants);
        if (cartItemsX) {
            CartItems cartItems2 = cartItemRepo.findByCartAndProductVariants(cart1, productVariants);
            cartItems.setSoluong(cartItems2.getSoluong() + request.getSoLuong());
            BigDecimal thanhTien = products.getGia().multiply(BigDecimal.valueOf(cartItems.getSoluong()));
            cartItems.setTongTien(thanhTien);
        }else {
            BigDecimal thanhTien = products.getGia().multiply(BigDecimal.valueOf(request.getSoLuong()));
            cartItems.setTongTien(thanhTien);
        }

        cartItemRepo.save(cartItems);
        BigDecimal thanhTien = BigDecimal.ZERO;

        List<CartItems> cartItemsList = cartItemRepo.findByCart(cart1);

        for (CartItems item : cartItemsList) {
            thanhTien = thanhTien.add(item.getTongTien());
        }
        cart1.setTongTien(thanhTien);

       cart1.setNgaySua(LocalDateTime.now());
       cartRepository.save(cart1);
       CreatCartResponse response = new CreatCartResponse();
       response.setSuccess(Boolean.TRUE);
       response.setToken(authenticationService.generateTokenUsers(user));
       return response;
    }
    @Autowired
    private ProductImagesRepo productImagesRepo;
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public MyCartResponse getAllMyCart() {
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepo.findBySdt(sdt);

        Cart cart = cartRepository.findByUsers(users).orElseThrow(()-> new AppException(ErrorCode.CART_NOT_EXISTED));
        MyCartResponse myCartResponse = new MyCartResponse();
        myCartResponse.setMaGioHang(cart.getMaGh());
        myCartResponse.setTongTien(cart.getTongTien());

        List<CartItems> cartItems = cartItemRepo.findByCart(cart);
        List<MyCartItemResponse> myCartItemResponseList = new ArrayList<>();
        cartItems.forEach(cartItems1 -> {

            MyCartItemResponse cartItemResponse = new MyCartItemResponse();

            CommentVariantResponse commentVariantResponse = productMapper.toDTO6(cartItems1.getProductVariants());
            commentVariantResponse.setTenKc(cartItems1.getProductVariants().getSizes().getTenKc());
            commentVariantResponse.setTenMs(cartItems1.getProductVariants().getColors().getTenMs());
            commentVariantResponse.setSoLuongDat(cartItems1.getSoluong());
            commentVariantResponse.setTongTien(cartItems1.getTongTien());

            cartItemResponse.setVariant(commentVariantResponse);

            ProductBestSellerResponse productBestSellerResponse = productMapper.toDTO5(cartItems1.getProductVariants().getProducts());
//            List<ProductImages> images =
//                    cartItems1.getProductVariants()
//                            .getProducts()
//                            .getImages();
            ProductImages firstImage = new ProductImages();
//            if (images != null && !images.isEmpty()) {
//                for (ProductImages image : images) {
//                    if (image.getDaiDienMau() == true) {
//                        firstImage.setUrlImage(image.getUrlImage());
//                        break;
//                    }
//                    else if (image.getDaiDien() == true)  {
//                        firstImage.setUrlImage(image.getUrlImage());
//                    }
//                }
//            }
            List<ProductImages> images=
                    productImagesRepo.getImagesByProductAndColor(cartItems1.getProductVariants()
                            .getProducts().getMaSp(), cartItems1.getProductVariants().getColors().getMaMs());

            String url = images.stream()
                    .filter(img -> Boolean.TRUE.equals(img.getDaiDienMau()))
                    .map(ProductImages::getUrlImage)
                    .findFirst()
                    .orElse(images.isEmpty() ? null : images.get(0).getUrlImage());

            firstImage.setUrlImage(url);

            productBestSellerResponse.setUrlImage(firstImage.getUrlImage());
            cartItemResponse.setProduct(productBestSellerResponse);

            myCartItemResponseList.add(cartItemResponse);

        });
        myCartResponse.setItems(myCartItemResponseList);

        return myCartResponse;
    }
//    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Boolean UpdateMyCart(UpdateMyCartReq request, Integer maBienThe) {
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepo.findBySdt(sdt);
        Cart cart = cartRepository.findByUsers(users).orElseThrow(()-> new AppException(ErrorCode.CART_NOT_EXISTED));

        ProductVariants productVariants = productVariantRepo.findById(maBienThe)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED));

        CartItems cartItems = cartItemRepo.findByCartAndProductVariants(cart, productVariants);
        if (cartItems == null) {
            throw new AppException(ErrorCode.CART_ITEM_NOT_EXISTED);
        }

        cartItems.setSoluong(request.getSoLuong());

        BigDecimal thanhTien = productVariants.getProducts().getGia().multiply(BigDecimal.valueOf(request.getSoLuong()));
        cartItems.setTongTien(thanhTien);
        cartItemRepo.save(cartItems);

        BigDecimal thanhTien2 = BigDecimal.ZERO;

        List<CartItems> cartItemsList = cartItemRepo.findByCart(cart);

        for (CartItems item : cartItemsList) {
            thanhTien2 = thanhTien2.add(item.getTongTien());
        }
        cart.setTongTien(thanhTien2);
        cart.setNgaySua(LocalDateTime.now());
        cartRepository.save(cart);

        return true;

    }
//    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Transactional
    public CreatCartResponse DeleteMyCartItem(Integer maBienThe) {
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepo.findBySdt(sdt);
        Cart cart = cartRepository.findByUsers(users).orElseThrow(()-> new AppException(ErrorCode.CART_NOT_EXISTED));
        ProductVariants productVariants= productVariantRepo.findById(maBienThe).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED));

        CartItems cartItems =
                cartItemRepo.findByCartAndProductVariants(cart, productVariants);

        if (cartItems == null) {
            throw new AppException(ErrorCode.CART_ITEM_NOT_EXISTED);
        }

        BigDecimal thanhTien2 = BigDecimal.ZERO;

        List<CartItems> cartItemsList = cartItemRepo.findByCart(cart);

        for (CartItems item : cartItemsList) {
            thanhTien2 = thanhTien2.add(item.getTongTien());
        }
        cart.setTongTien(thanhTien2);

        cart.setNgaySua(LocalDateTime.now());
        cartItemRepo.delete(cartItems);
        if(!cartItemRepo.existsByCart(cart)){
            cartRepository.delete(cart);
        }
        CreatCartResponse creatCartResponse = new CreatCartResponse();
        creatCartResponse.setToken(authenticationService.generateTokenUsers(users));
        creatCartResponse.setSuccess(Boolean.TRUE);
        return creatCartResponse;
    }
}
