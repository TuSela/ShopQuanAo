package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.CreateCartRequest;
import com.Nhom19.shopQuanAo.entity.*;
import com.Nhom19.shopQuanAo.entityCompositeKey.CartItemId;
import com.Nhom19.shopQuanAo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public Boolean createCart(CreateCartRequest request, Integer Id) {

        Cart cart = new Cart();

        Users user = userRepo.findById(Id).get();

        if (!cartRepository.existsByUsers(user)) {

            cart.setUsers(user);
            cart.setTongTien(new BigDecimal("0"));
            cart.setNgayTao(LocalDateTime.now());
            cart.setNgaySua(LocalDateTime.now());
            cartRepository.save(cart);
        }
        Cart cart1 = cartRepository.findByUsers(user);
        CartItems cartItems = new CartItems();

        cartItems.setCart(cart1);
        cartItems.setSoluong(request.getSoLuong());
        Products products =productRepo.findById(request.getMaSp()).orElseThrow(() -> new RuntimeException("người dùng không tồn tại"));

        BigDecimal thanhTien = products.getGia().multiply(BigDecimal.valueOf(request.getSoLuong()));

        cartItems.setTongTien(thanhTien);
        ProductVariants productVariants = productVariantRepo.findByProductAndColorAndSize(request.getMaSp(),  request.getMaMs(), request.getMaKc()).orElseThrow(() -> new RuntimeException("Sản phẩm biến thể không tồn tại"));

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
       return true;
    }
}
