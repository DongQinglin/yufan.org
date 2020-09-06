package me.dongqinglin.generalfileservice.bean;

public class DeleteFileRequest extends FileRequest {
    private String sourcePath;

    public DeleteFileRequest() {
    }

    public DeleteFileRequest(Boolean atStaticFolder, Boolean atUploadFolder, String sourcePath) {
        super(atStaticFolder, atUploadFolder);
        this.sourcePath = sourcePath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    @Override
    public String toString() {
        return "DeleteFileRequest{" +
                "sourcePath='" + sourcePath + '\'' +
                ", atStaticFolder=" + atStaticFolder +
                ", atUploadFolder=" + atUploadFolder +
                '}';
    }
}
