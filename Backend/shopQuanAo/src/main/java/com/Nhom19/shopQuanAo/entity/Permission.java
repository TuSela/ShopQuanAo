package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Permission {
    @Id
    @Column(name = "name")
    private String name;
    private String description;
}
