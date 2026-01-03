package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;

import lombok.Data;

import java.util.List;

@Data
public class ThongKeResponse {
    private List<CustomerStatisticDTO> customerStatisticDTO;
    private List<LowStockProductDTO> lowStockProductDTO;
    private List<RevenueByMonthDTO> revenueByMonthDTO;
    private List<RevenueByPaymentMethodDTO> revenueByPaymentMethodDTO;
    private List<NegativeCommentDTO> negativeCommentDTO;
    private List<OrderByMonthDTO> orderByMonthDTO;
    private List<RatingStatisticDTO> ratingStatisticDTO;
    private List<TopCustomerDTO> topCustomerDTO;
    private List<TopProductDTO> topProductDTO;
    private List<OrderStatusStatisticDTO> orderStatusStatisticDTO;
}
