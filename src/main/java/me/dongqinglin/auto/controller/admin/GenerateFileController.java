package me.dongqinglin.auto.controller.admin;

import me.dongqinglin.auto.bean.GenerateFileRequest;
import me.dongqinglin.auto.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

@RestController
@RequestMapping("api/admin/generateFile")
public class GenerateFileController {

    @Value("${file-service.profile}")
    private String uploadFilePath;

    @PostMapping
    public ResponseEntity<?> GenerateFile(@RequestBody GenerateFileRequest[] files){
        if(files.length == 0 ) return null;
        String[] result = new String[1];
        String folder = uploadFilePath + "tempFile/";
        for (int i = 0; i < files.length; i++) {
//            System.out.println(files[i]);
            String filePath =  folder + files[i].getFileName();
            String fileData = files[i].getFileData();
            FileUtil.writeFile(filePath, fileData);
        }
        String zipPath = uploadFilePath + "flina.zip";
        FileUtil.zipFiles(folder, zipPath);

        final Timer timer = new Timer();
        //设定定时任务
        timer.schedule(new TimerTask() {
            //定时任务执行方法
            @Override
            public void run() {
                FileUtil.deleteDirectory(folder);
                System.out.println("We delete temp files success.");
            }
        }, 1000*60*5);
        result[0] = "flina.zip";
        return ResponseEntity.ok(result);
    }
}
