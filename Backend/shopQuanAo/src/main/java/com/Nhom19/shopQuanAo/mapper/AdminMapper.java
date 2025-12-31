package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.AdminResponse;
import com.Nhom19.shopQuanAo.entity.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminResponse toDTO(Admin admin);
//    AdminMapper toEntity(AdminRequest adminRequest);
}
