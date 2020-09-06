package me.dongqinglin.auto.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExtraFileService {
    public void uploadExtraFiles(MultipartFile[] files, String tableName, int id);
}
