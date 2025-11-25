package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.DmChaResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ThemDmResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.DmConResponse;
import com.Nhom19.shopQuanAo.entity.DmCha;
import com.Nhom19.shopQuanAo.entity.DmCon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface DmMapper {
//    DmCha toDmCha(ThemDmRequest request);
    ThemDmResponse toDmResponse(DmCha dmCha);
    DmChaResponse toDmChaResponse(DmCon dmCon);
    DmConResponse toDmConResponse(DmCon dmCon);
}
