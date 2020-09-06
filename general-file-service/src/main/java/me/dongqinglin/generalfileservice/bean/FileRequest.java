package me.dongqinglin.generalfileservice.bean;

public class FileRequest {
    Boolean atStaticFolder;
    Boolean atUploadFolder;

    public FileRequest() {
    }

    public FileRequest(Boolean atStaticFolder, Boolean atUploadFolder) {
        this.atStaticFolder = atStaticFolder;
        this.atUploadFolder = atUploadFolder;
    }

    public Boolean getAtStaticFolder() {
        return atStaticFolder;
    }

    public void setAtStaticFolder(Boolean atStaticFolder) {
        this.atStaticFolder = atStaticFolder;
    }

    public Boolean getAtUploadFolder() {
        return atUploadFolder;
    }

    public void setAtUploadFolder(Boolean atUploadFolder) {
        this.atUploadFolder = atUploadFolder;
    }

    @Override
    public String toString() {
        return "FileRequest{" +
                "atStaticFolder=" + atStaticFolder +
                ", atUploadFolder=" + atUploadFolder +
                '}';
    }
}
