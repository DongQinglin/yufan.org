package me.dongqinglin.bootstarter.works.controller;

import me.dongqinglin.bootstarter.Secure.bean.FlinaUserDetail;
import me.dongqinglin.bootstarter.Secure.bean.UserLoginReq;
import me.dongqinglin.bootstarter.Secure.bean.UserLoginRes;
import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;
import me.dongqinglin.bootstarter.global.bean.Message;
import me.dongqinglin.bootstarter.global.config.ServerConfigContrant;
import me.dongqinglin.bootstarter.user.bean.UserMeteSaveRequest;
import me.dongqinglin.bootstarter.works.bean.WorksSaveRequest;
import me.dongqinglin.bootstarter.works.entity.Works;
import me.dongqinglin.bootstarter.works.service.ActivityService;
import me.dongqinglin.bootstarter.works.service.WorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ServerConfigContrant.PREFIX_URL)
public class YufanWorksController {

    @Autowired
    private WorksService service;

    @GetMapping("/user/works/author/{authorId}")
    public Message getAuthorWorks(@PathVariable String authorId) {
        Message message = Message.createSuccessMessage("正在查找id");
        try {
            List<Works> authorWorks = service.getAuthorWorks(Integer.parseInt(authorId));
            message.setExtra(authorWorks);
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }
        return message;
    }

    @GetMapping("/user/works/{worksId}")
    public Message getWorks(@PathVariable String worksId) {
        Message message = Message.createSuccessMessage("正在查找id");
        try {
            Works authorWorks = service.getWorks(Integer.parseInt(worksId));
            message.setExtra(authorWorks);
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }
        return message;
    }

    @PostMapping("/user/works")
    public Message save(@RequestBody WorksSaveRequest request) {
        Message message = Message.createSuccessMessage("正在查找id");
        try {
            service.saveWorks(request);
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }


        return message;
    }

    @DeleteMapping("/user/works/{worksId}")
    public Message deleteWorks(@PathVariable String worksId) {
        Message message = Message.createSuccessMessage("正在查找id");
        try {
            service.deleteWorks(Integer.parseInt(worksId));
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }
        return message;
    }
}
