package com.Nhom19.shopQuanAo.DTO.Response.Admin;


import com.Nhom19.shopQuanAo.entity.Permission;

import java.util.Set;

public class RoleResponse {
    private String name;
    private String description;
    private Set<Permission> permissions;
    public String getName() {
        return name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public RoleResponse(String name, String description, Set<Permission> permissions) {
        this.name = name;
        this.description = description;
        this.permissions = permissions;
    }

    public RoleResponse() {
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
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
}
