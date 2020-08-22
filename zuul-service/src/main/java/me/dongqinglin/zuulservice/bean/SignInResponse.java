package me.dongqinglin.zuulservice.bean;

public class SignInResponse {
    private String jwt;
    public SignInResponse(String jwt) {
        this.jwt = jwt;
    }
    public String getJwt() {
        return jwt;
    }
}
