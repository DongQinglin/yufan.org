package me.dongqinglin.bootstarter.works.controller;

import me.dongqinglin.bootstarter.global.bean.Message;
import me.dongqinglin.bootstarter.global.config.ServerConfigContrant;
import me.dongqinglin.bootstarter.works.entity.Activity;
import me.dongqinglin.bootstarter.works.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping(ServerConfigContrant.PREFIX_URL)
public class ActivityController {

    @Autowired
    private ActivityService service;

    @GetMapping("/user/activity")
    public Message getActivity() {
        Message message = Message.createSuccessMessage("查找成功");
        List<Activity> activities = service.getEnableActivity();
        return message.setExtra(activities);
    }
}
