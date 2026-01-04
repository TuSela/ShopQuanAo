package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.ThongKeFilterRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.ThongKeResponse;
import com.Nhom19.shopQuanAo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ThongKe {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ProductVariantRepo productVariantRepo;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private ProductCommentRepo productCommentRepo;
    @Autowired
    private OrderItemRepo orderItemRepo;
    @PreAuthorize("hasAuthority('REPORT_VIEW')")
    public ThongKeResponse getDashboardThongKe(LocalDateTime fromDate,
                                               LocalDateTime toDate) {
        ThongKeResponse thongKe = new ThongKeResponse();

        thongKe.setCustomerStatisticDTO(
                userRepository.thongKeHanhViKhachHang()
        );
        thongKe.setLowStockProductDTO(
                productVariantRepo.sanPhamTonKhoThap(2)
        );
        thongKe.setRevenueByMonthDTO(
                orderRepository.doanhThuTheoThang(fromDate, toDate)
        );
        thongKe.setRevenueByPaymentMethodDTO(
                orderRepository.doanhThuTheoPhuongThuc(fromDate, toDate)
        );
        thongKe.setOrderByMonthDTO(
                orderRepository.countCompletedOrdersByMonth(fromDate, toDate)
        );
        thongKe.setRatingStatisticDTO(
                productCommentRepo.thongKeDanhGia(fromDate, toDate)
        );
        thongKe.setTopCustomerDTO(
                userRepository.topKhachHang(PageRequest.of(0, 5))
        );
        thongKe.setTopProductDTO(
                orderItemRepo.topSanPhamBanChay(PageRequest.of(0, 5))
        );
        thongKe.setOrderStatusStatisticDTO(
                orderRepository.countOrdersByStatus()
        );
        return thongKe;
    }
}
