package me.dongqinglin.auto.controller.admin;


import java.util.List;
import java.util.Optional;
import me.dongqinglin.auto.bean.Message;
import me.dongqinglin.auto.entity.TbAdmInfo;
import me.dongqinglin.auto.service.ExtraFileService;
import me.dongqinglin.auto.service.TbAdmInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/admin/tbAdmInfo")
public class TbAdmInfoController {

    @Autowired
    private TbAdmInfoService tbAdmInfoService;

    @Autowired
    private ExtraFileService extraFileService;

    @PostMapping
    // @PreAuthorize("hasRole('admin')") // User access control can also be @PreAuthorize("hasAuthority('read')") etc.
    public void addTbAdmInfo(@RequestBody TbAdmInfo tbAdmInfo) {
        tbAdmInfoService.save(tbAdmInfo);
    }

    @PutMapping
    public void updateTbAdmInfo(@RequestBody TbAdmInfo tbAdmInfo) {
        tbAdmInfoService.update(tbAdmInfo);
    }

    @DeleteMapping("{id}")
    public void deleteTbAdmInfoBy(@PathVariable int id){
        tbAdmInfoService.deleteById(id);
    }

    @GetMapping
    public List<TbAdmInfo> getAllTbAdmInfo() {
        List<TbAdmInfo> result = tbAdmInfoService.findAll();
        return result;
    }

    @GetMapping("{id}")
    public Optional<TbAdmInfo> getTbAdmInfoBy(@PathVariable int id) {
        return tbAdmInfoService.findById(id);
    }

    //@PostMapping("/getLikeBy") // The filter is implemented on the front end, and the back end no longer needs fuzzy query
    //public List<TbAdmInfo> getTbAdmInfoLikeBy(@RequestBody TbAdmInfo tbAdmInfo) { return tbAdmInfoService.findLikeBy(user); }

    // The file with the same name is repeatedly uploaded, and the result is overwritten
    @PostMapping("uploadExtra/{id}")
    public Message uploadExtraFiles(@PathVariable int id, @RequestParam("files") MultipartFile[] files){
        Message result = new Message("success");
        String tableName = "tbAdmInfo";
        extraFileService.uploadExtraFiles(files, tableName, id);
        return result;
    }

    @GetMapping("downloadExtra/{id}")
    public Message downloadExtraFiles(@PathVariable int id){
        Message result;
        String targetPath = "tbAdmInfo/zip/" + id + ".zip";
        result = new Message(targetPath);
        return result;
    }

}