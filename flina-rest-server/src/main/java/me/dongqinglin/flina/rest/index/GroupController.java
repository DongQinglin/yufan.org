package me.dongqinglin.flina.rest.index;

import me.dongqinglin.flina.rest.bussiness.ActivityService;
import me.dongqinglin.flina.rest.bussiness.GroupService;
import me.dongqinglin.flina.rest.bussiness.WorksGroupService;
import me.dongqinglin.flina.rest.bussiness.WorksWorksGroupServzice;
import me.dongqinglin.flina.rest.data.payload.response.Group;
import me.dongqinglin.flina.rest.data.payload.response.Message;
import me.dongqinglin.flina.rest.data.schema.entity.*;
import me.dongqinglin.flina.rest.data.schema.repository.SeriesRepo;
import me.dongqinglin.flina.rest.middleware.jwt.JwtConfig;
import me.dongqinglin.flina.rest.middleware.role.HasRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(JwtConfig.AUTH_URL_PREFIX + "group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping("/all")
    public Message getGroup() {
        Message message = Message.createSuccessMessage("查找成功");
        Collection<Group> allByStatus_name = groupService.findAllByStatus_Name(Status.StatusEnum.ENABLE.toString());
        return message.setData(allByStatus_name);
    }



}
