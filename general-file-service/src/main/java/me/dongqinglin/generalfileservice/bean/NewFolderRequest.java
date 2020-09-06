package me.dongqinglin.generalfileservice.bean;

public class NewFolderRequest extends FileRequest{

    private String folderPath;

    public NewFolderRequest() {
    }

    public NewFolderRequest(Boolean atStaticFolder, Boolean atUploadFolder, String folderPath) {
        super(atStaticFolder, atUploadFolder);
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    @Override
    public String toString() {
        return "NewFolderRequest{" +
                "folderPath='" + folderPath + '\'' +
                ", atStaticFolder=" + atStaticFolder +
                ", atUploadFolder=" + atUploadFolder +
                '}';
    }
}
