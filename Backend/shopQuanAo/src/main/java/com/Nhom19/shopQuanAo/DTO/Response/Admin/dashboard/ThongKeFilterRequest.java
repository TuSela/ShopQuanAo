package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ThongKeFilterRequest {
    private LocalDateTime fromDate ;
    private LocalDateTime toDate ;
}
