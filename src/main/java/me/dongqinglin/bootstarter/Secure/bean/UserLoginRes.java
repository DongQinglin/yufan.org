package me.dongqinglin.bootstarter.Secure.bean;

public class UserLoginRes {
    private String jwt;
    private int id;

    public UserLoginRes(String jwt, int id) {
        this.jwt = jwt;
        this.id = id;
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

    @Override
    public String toString() {
        return "UserLoginRes{" +
                "jwt='" + jwt + '\'' +
                ", id=" + id +
                '}';
    }
}
