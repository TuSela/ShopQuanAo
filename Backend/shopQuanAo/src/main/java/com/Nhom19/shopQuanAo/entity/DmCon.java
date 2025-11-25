package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DmCon {
    public DmCon(String name, DmCha dmCha) {
        this.name = name;
        this.dmCha = dmCha;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dm_con_ma")
    private int id;

    @Column(name ="dm_con_ten")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "dm_ma")
    private DmCha dmCha;

    private String status;

//    @OneToMany(mappedBy = "products")
//    private Set<Products> products = new HashSet<>();

}
