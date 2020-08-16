package me.dongqinglin.auto.controller.admin;
//package 更改为包名;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import me.dongqinglin.auto.entity.TbAdmInfo;
import me.dongqinglin.auto.service.TbAdmInfoService;
import me.dongqinglin.auto.util.ExcelUtil;
import me.dongqinglin.auto.util.FileUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/admin/tbAdmInfo")
public class TbAdmInfoController {

    @Value("${file-service.profile}")
    private String uploadFilePath;

    @Autowired
    private TbAdmInfoService tbAdmInfoService;
    @PostMapping
    //@PreAuthorize("hasRole('admin')") // 用户权限控制 还可以是@PreAuthorize("hasAuthority('read')")等
    public void addTbAdmInfo(@RequestBody TbAdmInfo tbAdmInfo) { tbAdmInfoService.save(tbAdmInfo); }
    @PutMapping
    public void updateTbAdmInfo(@RequestBody TbAdmInfo tbAdmInfo) {tbAdmInfoService.update(tbAdmInfo);}
    @DeleteMapping("{id}")
    public void deleteTbAdmInfoBy(@PathVariable int id){tbAdmInfoService.deleteById(id);}
    @GetMapping
    public List<TbAdmInfo> getAllTbAdmInfo() {

        List<TbAdmInfo> result = tbAdmInfoService.findAll();
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        return result;
    }
    @GetMapping("{id}")
    public Optional<TbAdmInfo> getTbAdmInfoBy(@PathVariable int id) { return tbAdmInfoService.findById(id); }
    //@PostMapping("/getLikeBy") // filter在前端得以实现，后端就不再需要模糊查询
    //public List<TbAdmInfo> getTbAdmInfoLikeBy(@RequestBody TbAdmInfo tbAdmInfo) { return tbAdmInfoService.findLikeBy(user); }
    @PostMapping("fileUpload/{id}")
    public void uploadFile(@PathVariable int id, @RequestParam("files") MultipartFile[] files){
        if(files.length == 0 ) System.out.println("没有文件");
        for (int i = 0; i < files.length; i++) {
            try {
                MultipartFile file = files[i];
                String fileName = file.getOriginalFilename();
                System.out.println(fileName);
                String fileFolder = uploadFilePath + "tbAdmInfo/" + id + "/";
                FileUtil.makeDir(fileFolder);
                file.transferTo(new File(fileFolder + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String targetFolder = uploadFilePath + "tbAdmInfo/zip/";
        FileUtil.makeDir(targetFolder);
        String targetPath = uploadFilePath + "tbAdmInfo/zip/" + id + ".zip";
        String fileFolder = uploadFilePath + "tbAdmInfo/" + id + "/";
        FileUtil.zipFiles(fileFolder,targetPath);
    }
    @GetMapping("fileDownload/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable int id){
        String[] result = new String[1];
        String targetPath = "tbAdmInfo/zip/" + id + ".zip";
        result[0] = targetPath;
        return ResponseEntity.ok(result);
    }

    @GetMapping("export")
    public void export(HttpServletResponse response) throws IOException{
        String fileName = "数据表名";
        List<TbAdmInfo> all = tbAdmInfoService.findAll();
        String[] th = {"id", "orgId", "addr", "areaCode"};
        HSSFWorkbook excel = ExcelUtil.createExcel(fileName, th);
        HSSFSheet sheet = excel.getSheetAt(0);
        int rowNum = 1;
        for (TbAdmInfo tbAdmInfo: all) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(tbAdmInfo.getId());
            row.createCell(1).setCellValue(tbAdmInfo.getOrgId());
            row.createCell(2).setCellValue(tbAdmInfo.getsAddr());
            row.createCell(3).setCellValue(tbAdmInfo.getsAreaCode());
            rowNum++;
        }

        ExcelUtil.setBrowser(response, excel, fileName);
    }

    @PostMapping("import")
    public void importData(@RequestParam("files") MultipartFile[] files){
        if(files.length == 0 ) System.out.println("没有文件");
        for (int i = 0; i < files.length; i++) {

            MultipartFile file = files[i];
            String fileName = file.getOriginalFilename();
            String folder = uploadFilePath + "tempFile/";
            FileUtil.makeDir(folder);
            System.out.println(folder + fileName);
            File newFile = new File(folder + fileName);
            List<Object[]> objects = ExcelUtil.importExcel(folder + fileName);
            for (int j = 0; j < objects.size(); j++) {
                Object object = objects.get(i);
                TbAdmInfo object1 = (TbAdmInfo) object;
                System.out.println(object);
            }
            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

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



        }

    }
}