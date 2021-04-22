package me.dongqinglin.bootstarter.user.controller;

import me.dongqinglin.bootstarter.global.bean.Message;
import me.dongqinglin.bootstarter.global.config.ServerConfigContrant;
import me.dongqinglin.bootstarter.user.bean.UserMeteSaveRequest;
import me.dongqinglin.bootstarter.user.service.UserMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ServerConfigContrant.PREFIX_URL)
public class UserMetaController {

    @Autowired
    private UserMetaService service;

    @GetMapping("/user/usermeta/{id}")
    public Message logIn(@PathVariable String id) {
        Message message = Message.createSuccessMessage("正在查找id");
        try {
            message.setExtra(service.getUserMeta(id));
        } catch (Exception e) {
            message = Message.createIllegalMessage("没有用户元数据");
        }


        return message;
    }

    @PostMapping("/user/usermeta")
    public Message save(@RequestBody UserMeteSaveRequest request) {
        Message message = Message.createSuccessMessage("正在查找id");
        try {
            service.saveUserMeta(request);
        } catch (Exception e) {
            message = Message.createIllegalMessage("没有用户元数据");
        }


        return message;
    }
}
