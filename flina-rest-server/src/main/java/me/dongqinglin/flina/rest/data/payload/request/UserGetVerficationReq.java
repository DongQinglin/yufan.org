package me.dongqinglin.flina.rest.data.payload.request;

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
