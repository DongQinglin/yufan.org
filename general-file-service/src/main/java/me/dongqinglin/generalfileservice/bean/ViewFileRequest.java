package me.dongqinglin.generalfileservice.bean;

public class ViewFileRequest extends FileRequest {
    private String filePath;

    public ViewFileRequest() {
    }

    public ViewFileRequest(Boolean atStaticFolder, Boolean atUploadFolder, String filePath) {
        super(atStaticFolder, atUploadFolder);
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return "ViewFileRequest{" +
                "filePath='" + filePath + '\'' +
                ", atStaticFolder=" + atStaticFolder +
                ", atUploadFolder=" + atUploadFolder +
                '}';
    }
}
