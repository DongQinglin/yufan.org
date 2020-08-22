package me.dongqinglin.generalfileservice.bean;

public class DirFileRequest extends FileRequest {
    private String folderPath;

    public DirFileRequest() {
    }

    public DirFileRequest(Boolean atStaticFolder, Boolean atUploadFolder, String folderPath) {
        super(atStaticFolder, atUploadFolder);
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }
}
