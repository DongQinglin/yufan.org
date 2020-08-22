package me.dongqinglin.generalfileservice.controller;

import me.dongqinglin.generalfileservice.bean.*;
import me.dongqinglin.generalfileservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MainController {
    @Autowired
    private FileService fileService;

    private String getFolderString(String folderPath, Boolean atStaticFolder, Boolean atUploadFolder) {
        if (atStaticFolder == null ) atStaticFolder = false;
        if (atUploadFolder == null ) atUploadFolder = false;
        if (atStaticFolder && atUploadFolder) atStaticFolder = false;
        if (folderPath == null) folderPath = "";
        if (atStaticFolder) folderPath = fileService.staticRoot() + folderPath;
        if (atUploadFolder) folderPath = fileService.uploadRoot() + folderPath;
        return folderPath;
    }
    @PostMapping("dir")
    Directory dir(@RequestBody DirFileRequest dirFileRequest) {
        String folderPath = getFolderString(dirFileRequest.getFolderPath(), dirFileRequest.getAtStaticFolder(), dirFileRequest.getAtUploadFolder());
        return fileService.dir(folderPath);
    }

    @PostMapping("new-folder")
    Message newFolder(@RequestBody NewFolderRequest folderRequest){
        String folderPath = getFolderString(folderRequest.getFolderPath(), folderRequest.getAtStaticFolder(), folderRequest.getAtUploadFolder());
        return fileService.newFolder(folderPath);

    }
    @PostMapping("new-file")
    Message newFile(@RequestBody NewFileOrEditRequest fileOrEditRequest){
        String filePath = getFolderString(fileOrEditRequest.getFilePath(), fileOrEditRequest.getAtStaticFolder(), fileOrEditRequest.getAtUploadFolder());

        return fileService.newFile(filePath, fileOrEditRequest.getFileData());
    }
    @PostMapping("edit-file")
    Message editFile(@RequestBody NewFileOrEditRequest fileOrEditRequest){
        String filePath = getFolderString(fileOrEditRequest.getFilePath(), fileOrEditRequest.getAtStaticFolder(), fileOrEditRequest.getAtUploadFolder());
        return fileService.editFile(filePath, fileOrEditRequest.getFileData());
    }
    @PostMapping("view")
    Message view(@RequestBody ViewFileRequest viewFileRequest){
        String filePath = getFolderString(viewFileRequest.getFilePath(), viewFileRequest.getAtStaticFolder(), viewFileRequest.getAtUploadFolder());
        return fileService.view(filePath);
    }
    @PostMapping("rename")
    Message rename(@RequestBody RenameFileRequest renameFileRequest){
        String originSourceName = getFolderString(renameFileRequest.getOriginSourceName(), renameFileRequest.getAtStaticFolder(), renameFileRequest.getAtUploadFolder());
        String newSourceName = getFolderString(renameFileRequest.getNewSourceName(), renameFileRequest.getAtStaticFolder(), renameFileRequest.getAtUploadFolder());
        return fileService.rename(originSourceName, newSourceName);
    }
    @PostMapping("delete")
    Message delete(@RequestBody DeleteFileRequest deleteFileRequest){
        String sourcePath = getFolderString(deleteFileRequest.getSourcePath(), deleteFileRequest.getAtStaticFolder(), deleteFileRequest.getAtUploadFolder());
        return fileService.delete(sourcePath);
    }
    @PostMapping("copy")
    Message copy(@RequestBody CopyCutAndZipFileRequest copyCutAndZipFileRequest){
        String sourcePath = getFolderString(copyCutAndZipFileRequest.getSourcePath(), copyCutAndZipFileRequest.getAtStaticFolder(), copyCutAndZipFileRequest.getAtUploadFolder());
        String targetPath = getFolderString(copyCutAndZipFileRequest.getTargetPath(), copyCutAndZipFileRequest.getAtStaticFolder(), copyCutAndZipFileRequest.getAtUploadFolder());
        return fileService.copy(sourcePath, targetPath);
    }
    @PostMapping("cut")
    Message cut(@RequestBody CopyCutAndZipFileRequest copyCutAndZipFileRequest){
        String sourcePath = getFolderString(copyCutAndZipFileRequest.getSourcePath(), copyCutAndZipFileRequest.getAtStaticFolder(), copyCutAndZipFileRequest.getAtUploadFolder());
        String targetPath = getFolderString(copyCutAndZipFileRequest.getTargetPath(), copyCutAndZipFileRequest.getAtStaticFolder(), copyCutAndZipFileRequest.getAtUploadFolder());
        return fileService.cut(sourcePath, targetPath);
    }
    @PostMapping("zip")
    Message zip(@RequestBody CopyCutAndZipFileRequest copyCutAndZipFileRequest){
        String sourcePath = getFolderString(copyCutAndZipFileRequest.getSourcePath(), copyCutAndZipFileRequest.getAtStaticFolder(), copyCutAndZipFileRequest.getAtUploadFolder());
        String targetPath = getFolderString(copyCutAndZipFileRequest.getTargetPath(), copyCutAndZipFileRequest.getAtStaticFolder(), copyCutAndZipFileRequest.getAtUploadFolder());
        return fileService.zip(sourcePath, targetPath);
    }
    @PostMapping("unzip")
    Message unzip(@RequestBody CopyCutAndZipFileRequest copyCutAndZipFileRequest){
        String sourcePath = getFolderString(copyCutAndZipFileRequest.getSourcePath(), copyCutAndZipFileRequest.getAtStaticFolder(), copyCutAndZipFileRequest.getAtUploadFolder());
        String targetPath = getFolderString(copyCutAndZipFileRequest.getTargetPath(), copyCutAndZipFileRequest.getAtStaticFolder(), copyCutAndZipFileRequest.getAtUploadFolder());
        return fileService.unzip(sourcePath, targetPath);
    }
    @PostMapping("upload")
    Message upload(MultipartFile[] files, String targetPath){
        targetPath = fileService.uploadRoot() + targetPath;
        return fileService.upload(files, targetPath);
    }
}
