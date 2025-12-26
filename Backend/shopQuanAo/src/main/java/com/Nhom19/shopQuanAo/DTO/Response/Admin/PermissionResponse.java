package com.Nhom19.shopQuanAo.DTO.Response.Admin;

public class PermissionResponse {
    public  String name;
    public String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PermissionResponse(String description, String name) {
        this.description = description;
        this.name = name;
    }
}

