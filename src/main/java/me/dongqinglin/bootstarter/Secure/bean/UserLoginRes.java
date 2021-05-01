package me.dongqinglin.bootstarter.Secure.bean;

public class UserLoginRes {
    private String jwt;
    private int id;
    private String email;
    private String username;

    public UserLoginRes(String jwt, int id, String email, String username) {
        this.jwt = jwt;
        this.id = id;
        this.email = email;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserLoginRes{" +
                "jwt='" + jwt + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
