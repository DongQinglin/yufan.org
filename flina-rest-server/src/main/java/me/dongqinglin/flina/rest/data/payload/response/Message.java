package me.dongqinglin.flina.rest.data.payload.response;

public class Message<T> {

    private CodeStatus status;
    private String content;
    private T data;

    public Message() {
    }

    private Message(CodeStatus code, String content, T data) {
        this.status = code;
        this.content = content;
        this.data = data;
    }

    public static Message createSuccessMessage(String content) {
        return new Message(CodeStatus.SUCCESSFUL, content, null);
    }

    public static Message createIllegalMessage(String content) {
        return new Message(CodeStatus.FAILURE, content, null);
    }

    public enum CodeStatus {
        SUCCESSFUL, FAILURE
    }

    public CodeStatus getStatus() {
        return status;
    }

    public void setStatus(CodeStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public T getData() {
        return data;
    }

    public Message setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "status=" + status +
                ", content='" + content + '\'' +
                ", data=" + data +
                '}';
    }
}