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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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

    public CreatCartResponse createCart(CreateCartRequest request, Integer Id) {

        Cart cart = new Cart();

        Users user = userRepo.findById(Id).get();

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
        cartItems.setSoluong(cartItems.getSoluong() + request.getSoLuong());
        Products products =productRepo.findById(request.getMaSp()).orElseThrow(() -> new RuntimeException("người dùng không tồn tại"));

        BigDecimal thanhTien = products.getGia().multiply(BigDecimal.valueOf(request.getSoLuong()));

        cartItems.setTongTien(thanhTien);
        ProductVariants productVariants = productVariantRepo.findByProductAndColorAndSize(request.getMaSp(),  request.getMaMs(), request.getMaKc()).orElseThrow(() -> new RuntimeException("Sản phẩm biến thể không tồn tại"));
        CartItems cartItemsX = cartItemRepo.findByCartAndProductVariants(cart1, productVariants);
        if ( cartItemsX != null) {
            cartItems.setSoluong(cartItemsX.getSoluong() + request.getSoLuong());
        }
        cartItems.setProductVariants(productVariants);
        CartItemId id = new CartItemId(
                cart1.getMaGh(),
                productVariants.getMaBienThe()
        );
        cartItems.setId(id);
        cartItemRepo.save(cartItems);

       List<CartItems> cartItemsList= cartItemRepo.findByCart(cart1);
       cartItemsList.forEach(cartItems1 -> {
         cart1.setTongTien(cart1.getTongTien().add(cartItems1.getTongTien()));
       });

       cart1.setNgaySua(LocalDateTime.now());
       cartRepository.save(cart1);
       CreatCartResponse response = new CreatCartResponse();
       response.setSuccess(Boolean.TRUE);
       response.setToken(authenticationService.generateToken(user));
       return response;
    }

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
            cartItemResponse.setVariant(commentVariantResponse);

            ProductBestSellerResponse productBestSellerResponse = productMapper.toDTO5(cartItems1.getProductVariants().getProducts());
            List<ProductImages> images =
                    cartItems1.getProductVariants()
                            .getProducts()
                            .getImages();
            ProductImages firstImage = new ProductImages();

            if (images != null && !images.isEmpty()) {
                for (ProductImages image : images) {
                    if (image.getDaiDien() == true) {
                        firstImage.setUrlImage(image.getUrlImage());
                        break;
                    }
                }
            }

            productBestSellerResponse.setUrlImage(firstImage.getUrlImage());
            cartItemResponse.setProduct(productBestSellerResponse);

            myCartItemResponseList.add(cartItemResponse);

        });
        myCartResponse.setItems(myCartItemResponseList);

        return myCartResponse;
    }
    public Boolean UpdateMyCart(UpdateMyCartReq request, Integer maBienThe) {
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepo.findBySdt(sdt);
        Cart cart = cartRepository.findByUsers(users).orElseThrow(()-> new AppException(ErrorCode.CART_NOT_EXISTED));

        ProductVariants productVariants= productVariantRepo.findById(maBienThe).orElseThrow(()-> new RuntimeException("Không tìm thấy mã biến thể"));

        CartItems cartItems = cartItemRepo.findByCartAndProductVariants(cart, productVariants);
        cartItems.setSoluong(request.getSoLuong());

        BigDecimal thanhTien = productVariants.getProducts().getGia().multiply(BigDecimal.valueOf(request.getSoLuong()));
        cartItems.setTongTien(thanhTien);
        cartItemRepo.save(cartItems);

        List<CartItems> cartItemsList= cartItemRepo.findByCart(cart);
        cartItemsList.forEach(cartItems1 -> {
            cart.setTongTien(cart.getTongTien().add(cartItems1.getTongTien()));
        });

        cart.setNgaySua(LocalDateTime.now());
        cartRepository.save(cart);

        return true;

    }

    public Boolean DeleteMyCartItem(Integer maBienThe) {
        var context = SecurityContextHolder.getContext();
        String sdt = context.getAuthentication().getName();
        Users users = userRepo.findBySdt(sdt);
        Cart cart = cartRepository.findByUsers(users).orElseThrow(()-> new AppException(ErrorCode.CART_NOT_EXISTED));
        ProductVariants productVariants= productVariantRepo.findById(maBienThe).orElseThrow(()-> new RuntimeException("Không tìm thấy mã biến thể"));

        CartItems cartItems = cartItemRepo.findByCartAndProductVariants(cart, productVariants);

        cart.setTongTien(cart.getTongTien().subtract(cartItems.getTongTien()));
        cart.setNgaySua(LocalDateTime.now());
        cartItemRepo.delete(cartItems);
        return true;
    }
}
