package me.dongqinglin.generalfileservice.bean;

public class FileServiceMessage extends Message {
    public enum FileStatusEnum { SUCCESS, ERROR, ILLEGAL_PARAMETER, EXIST_FOLDER, EXIST_FILE, NOT_EXIST }
    public FileServiceMessage(String content, int status) {
        super(content, status);
    }
    @Override
    public int getStatus() {
        return super.status;
    }
}
