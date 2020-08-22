package me.dongqinglin.generalfileservice.bean;

import java.io.File;

public class RenameFileRequest extends FileRequest {
    private String originSourceName;
    private String newSourceName;

    public RenameFileRequest() {
    }

    public RenameFileRequest(Boolean atStaticFolder, Boolean atUploadFolder, String originSourceName, String newSourceName) {
        super(atStaticFolder, atUploadFolder);
        this.originSourceName = originSourceName;
        this.newSourceName = newSourceName;
    }

    public String getOriginSourceName() {
        return originSourceName;
    }

    public String getNewSourceName() {
        return newSourceName;
    }

    @Override
    public String toString() {
        return "RenameFileRequest{" +
                "originSourceName='" + originSourceName + '\'' +
                ", newSourceName='" + newSourceName + '\'' +
                ", atStaticFolder=" + atStaticFolder +
                ", atUploadFolder=" + atUploadFolder +
                '}';
    }
}
