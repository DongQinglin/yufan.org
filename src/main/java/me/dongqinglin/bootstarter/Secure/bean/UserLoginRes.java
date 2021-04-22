package me.dongqinglin.bootstarter.Secure.bean;

public class UserLoginRes {
    private String jwt;
    private int id;
    private String email;

    public UserLoginRes(String jwt, int id, String email) {
        this.jwt = jwt;
        this.id = id;
        this.email = email;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserLoginRes{" +
                "jwt='" + jwt + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
