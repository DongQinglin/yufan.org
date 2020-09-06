package me.dongqinglin.generalfileservice.bean;

public class NewFileOrEditRequest extends FileRequest{
    private String filePath;
    private String fileData;

    public NewFileOrEditRequest() {
    }

    public NewFileOrEditRequest(Boolean atStaticFolder, Boolean atUploadFolder, String filePath, String fileData) {
        super(atStaticFolder, atUploadFolder);
        this.filePath = filePath;
        this.fileData = fileData;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileData() {
        return fileData;
    }

    @Override
    public String toString() {
        return "NewFileRequest{" +
                "fileData='" + fileData + '\'' +
                ", atStaticFolder=" + atStaticFolder +
                ", atUploadFolder=" + atUploadFolder +
                '}';
    }
}
