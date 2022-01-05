package me.dongqinglin.flina.rest.index;

import me.dongqinglin.flina.rest.data.payload.response.Message;
import me.dongqinglin.flina.rest.data.schema.entity.Style;
import me.dongqinglin.flina.rest.data.schema.entity.User;
import me.dongqinglin.flina.rest.middleware.jwt.JwtConfig;
import me.dongqinglin.flina.rest.middleware.role.HasRole;
import me.dongqinglin.flina.rest.middleware.tomcat.ServerConfigContrant;
import me.dongqinglin.flina.rest.data.schema.entity.Activity;
import me.dongqinglin.flina.rest.bussiness.ActivityService;
import me.dongqinglin.flina.rest.bussiness.WorksGroupService;
import me.dongqinglin.flina.rest.bussiness.WorksWorksGroupServzice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping(JwtConfig.AUTH_URL_PREFIX + "activity")
public class ActivityController {

    @Autowired
    private ActivityService service;
    @Autowired
    private WorksGroupService worksGroupService;
    @Autowired
    private WorksWorksGroupServzice worksWorksGroupServzice;
    @GetMapping("/all")
    public Message getActivity() {
        Message message = Message.createSuccessMessage("查找成功");
        List<Activity> activities = service.getEnableActivity();
        return message.setData(activities);
    }
    @GetMapping("/style")
    public Message getStyle() {
        Message message = Message.createSuccessMessage("查找成功");
        List<Style> style = service.getEnableStyle();
        return message.setData(style);
    }


}
