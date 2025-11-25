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
@Table(name = "dm")
public class DmCha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dm_ma")
    private int id;

    @Column(name ="dm_ten")
    private String name;

    @Column(name = "dm_tt")
    private String status;

    @OneToMany(mappedBy = "dmCha",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DmCon> dmCon = new HashSet<>();
}
