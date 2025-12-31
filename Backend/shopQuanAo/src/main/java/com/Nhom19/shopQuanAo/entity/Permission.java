package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @Column(name = "name")
    private String name;

    private String description;
}
