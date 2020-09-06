package me.dongqinglin.generalfileservice.bean;

public class CopyCutAndZipFileRequest extends FileRequest {
    private String sourcePath;
    private String targetPath;

    public CopyCutAndZipFileRequest() {
    }

    public CopyCutAndZipFileRequest(Boolean atStaticFolder, Boolean atUploadFolder, String sourcePath, String targetPath) {
        super(atStaticFolder, atUploadFolder);
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    @Override
    public String toString() {
        return "CopyCutAndZipFileRequest{" +
                "sourcePath='" + sourcePath + '\'' +
                ", targetPath='" + targetPath + '\'' +
                ", atStaticFolder=" + atStaticFolder +
                ", atUploadFolder=" + atUploadFolder +
                '}';
    }
}
