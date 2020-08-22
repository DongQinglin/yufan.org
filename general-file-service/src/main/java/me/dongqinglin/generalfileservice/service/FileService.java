package me.dongqinglin.generalfileservice.service;

import me.dongqinglin.generalfileservice.bean.Directory;
import me.dongqinglin.generalfileservice.bean.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    String staticRoot();
    String uploadRoot();

    Directory dir(String folderPath);
    Message newFolder(String folderPath);
    Message newFile(String filePath, String fileData);
    Message editFile(String filePath, String fileData);
    Message view(String filePath);

    Message rename(String originSourceName, String newSourceName);
    Message delete(String sourcePath);
    Message copy(String sourcePath, String targetPath);
    Message cut(String sourcePath, String targetPath);

    Message zip(String sourcePath, String targetPath);
    Message unzip(String sourcePath, String targetPath);

    Message upload(MultipartFile[] files, String targetPath);


}
