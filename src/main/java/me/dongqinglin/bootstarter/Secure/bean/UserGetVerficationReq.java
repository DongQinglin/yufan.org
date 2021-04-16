package me.dongqinglin.bootstarter.Secure.bean;

public class UserGetVerficationReq {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserGetVerficationReq{" +
                "email='" + email + '\'' +
                '}';
    }
}
