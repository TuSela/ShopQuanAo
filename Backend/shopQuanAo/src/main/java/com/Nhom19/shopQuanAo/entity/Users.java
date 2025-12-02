package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maTk;
    private String password;
    private String hoten;
    private String email;
    private String sdt;
    public Users() {
    }

    public Users(Integer maTk, String username, String password, String hoten, String email, String sdt, String diachi) {
        this.maTk = maTk;
        this.password = password;
        this.hoten = hoten;
        this.email = email;
        this.sdt = sdt;

    }

    public Integer getMaTk() {
        return maTk;
    }

    public void setMaTk(Integer maTk) {
        this.maTk = maTk;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }




//    @OneToMany(mappedBy = "users")
//    private Set<Orders> orders = new HashSet<>();

//    @OneToMany(mappedBy = "users")
//    private Set<ProductComments> comments = new HashSet<>();

    // feedbacks if needed
//    @OneToMany(mappedBy = "users")
//    private Set<Feedbacks> feedbacks = new HashSet<>();
}
