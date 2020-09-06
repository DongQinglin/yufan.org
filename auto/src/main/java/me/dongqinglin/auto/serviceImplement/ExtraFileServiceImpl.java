package me.dongqinglin.auto.serviceImplement;

import me.dongqinglin.auto.service.ExtraFileService;
import me.dongqinglin.auto.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ExtraFileServiceImpl implements ExtraFileService {

    @Value("${prosonal-file-server}")
    private String uploadFilePath;

    @Override
    public void uploadExtraFiles(MultipartFile[] files, String tableName, int id) {
        if(files.length == 0 ) System.out.println("None file.");

        String IDFolder = uploadFilePath + tableName +"/" + id + "/";
        String ZipFolder = uploadFilePath + tableName  + "/zip/";
        FileUtil.makeDir(IDFolder);
        FileUtil.makeDir(ZipFolder);

        for (int i = 0; i < files.length; i++) {
            try {
                MultipartFile file = files[i];
                String fileName = file.getOriginalFilename();
                // System.out.println(fileName);
                file.transferTo(new File(IDFolder + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String zipFileName = ZipFolder + id + ".zip";
        FileUtil.zipFiles(IDFolder,zipFileName);
    }

}
