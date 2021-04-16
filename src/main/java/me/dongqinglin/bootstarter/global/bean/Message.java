package me.dongqinglin.bootstarter.global.bean;

public class Message<T> {
    private final static int SUCCESS = 200;
    public final static int ILLEGAL = 400;
    private String content;
    private Integer status;
    private T extra;

    public Message() {
    }

    private Message(String content, Integer status) {
        this.content = content;
        this.status = status;
    }

    public static Message createMessage(String content, Integer status) {
        return new Message(content, status);
    }

    public static Message createSuccessMessage(String content) {
        return new Message(content, SUCCESS);
    }

    public static Message createIllegalMessage(String content) {
        return new Message(content, ILLEGAL);
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Message setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public T getExtra() {
        return extra;
    }

    public Message setExtra(T extra) {
        this.extra = extra;
        return this;
    }
}
