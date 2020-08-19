package me.dongqinglin.auto.bean;

public class GenerateFileRequest {
    private String fileName;
    private String fileData;

    public GenerateFileRequest() {
    }

    public GenerateFileRequest(String fileName, String fileData) {
        this.fileName = fileName;
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileData() {
        return fileData;
    }

    @Override
    public String toString() {
        return "GenerateFileRequest{" +
                "fileName='" + fileName + '\'' +
                ", fileData='" + fileData + '\'' +
                '}';
    }
}
