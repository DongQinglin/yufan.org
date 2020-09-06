package me.dongqinglin.auto.bean;

public class Message {
    public enum StatusEnum { SUCCESS, ERROR }
    private String[] statusEnmuStr = {"SUCCESS", "ERROR"};
    private int status;
    private String content;

    public Message(String content) {
        this.content = content;
    }

    public Message(String content, int status) {
        this.content = content;
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return statusEnmuStr[status];
    }
}
