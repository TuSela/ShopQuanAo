package com.Nhom19.shopQuanAo.DTO.Response.Admin.dashboard;

import lombok.Data;

import java.math.BigDecimal;


public class RatingStatisticDTO {
    private BigDecimal soSao;
    private Long soLuot;
    public RatingStatisticDTO(BigDecimal soSao, Long soLuot) {
        this.soSao = soSao;
        this.soLuot = soLuot;
    }

    public BigDecimal getSoSao() {
        return soSao;
    }

    public void setSoSao(BigDecimal soSao) {
        this.soSao = soSao;
    }

    public Long getSoLuot() {
        return soLuot;
    }

    public void setSoLuot(Long soLuot) {
        this.soLuot = soLuot;
    }
}
