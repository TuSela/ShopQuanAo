package com.Nhom19.shopQuanAo.DTO.Response.Admin;

public class UserResponse {
    private Integer maTk;
    private String username;
    private String password;
    private String hoten;
    private String email;
    private String sdt;
    private String diachi;

    public UserResponse() {
    }

    public UserResponse(Integer maTk, String username, String password, String hoten, String email, String sdt, String diachi) {
        this.maTk = maTk;
        this.username = username;
        this.password = password;
        this.hoten = hoten;
        this.email = email;
        this.sdt = sdt;
        this.diachi = diachi;
    }

    public Integer getMaTk() {
        return maTk;
    }

    public void setMaTk(Integer maTk) {
        this.maTk = maTk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
//    @OneToMany(mappedBy = "users")
//    private Set<Orders> orders = new HashSet<>();

//    @OneToMany(mappedBy = "users")
//    private Set<ProductComments> comments = new HashSet<>();

    // feedbacks if needed
//    @OneToMany(mappedBy = "users")
//    private Set<Feedbacks> feedbacks = new HashSet<>();
}
