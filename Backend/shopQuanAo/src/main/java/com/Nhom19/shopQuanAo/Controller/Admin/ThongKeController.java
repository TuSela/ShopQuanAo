package com.Nhom19.shopQuanAo.Controller.Admin;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.ThongKeFilterRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard.ThongKeResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.service.ThongKe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/thongke")
@RestController
public class ThongKeController {
    @Autowired
    private ThongKe thongKe;
    @GetMapping
    public ApiResponse<ThongKeResponse> getDashboardThongKe(@RequestParam(required = false)
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            LocalDateTime fromDate,

                                                            @RequestParam(required = false)
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                LocalDateTime toDate)
    {
        ApiResponse<ThongKeResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(
                thongKe.getDashboardThongKe(fromDate, toDate)
        );
        return apiResponse;
    }
}
