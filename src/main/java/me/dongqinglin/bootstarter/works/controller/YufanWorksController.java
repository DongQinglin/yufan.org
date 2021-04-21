package me.dongqinglin.bootstarter.works.controller;

import me.dongqinglin.bootstarter.Secure.bean.FlinaUserDetail;
import me.dongqinglin.bootstarter.Secure.bean.UserLoginReq;
import me.dongqinglin.bootstarter.Secure.bean.UserLoginRes;
import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;
import me.dongqinglin.bootstarter.global.bean.Message;
import me.dongqinglin.bootstarter.global.config.ServerConfigContrant;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ServerConfigContrant.PREFIX_URL)
public class YufanWorksController {

    @GetMapping("/user/works/{id}")
    public Message logIn(@PathVariable String id) {

        return Message.createIllegalMessage("正在查找id");
    }
}
