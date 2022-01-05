package me.dongqinglin.flina.rest.middleware.jwt;

public class ApiException extends Exception {
    private String message;

    public ApiException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
