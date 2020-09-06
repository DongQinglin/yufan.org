package me.dongqinglin.generalfileservice.bean;

public class Message {
    public enum StatusEnum { SUCCESS, ERROR, ILLEGAL_PARAMETER }
    public int status;
    private String content;

    public Message(String content, int status) {
        this.content = content;
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public int getStatus() {
        return status;
    }
}
