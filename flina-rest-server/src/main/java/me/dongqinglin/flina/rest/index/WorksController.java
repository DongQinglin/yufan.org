package me.dongqinglin.flina.rest.index;

import me.dongqinglin.flina.rest.data.payload.response.Message;
import me.dongqinglin.flina.rest.data.schema.entity.User;
import me.dongqinglin.flina.rest.data.schema.entity.WorksToStyleWithStatus;
import me.dongqinglin.flina.rest.middleware.jwt.JwtConfig;
import me.dongqinglin.flina.rest.middleware.role.HasRole;
import me.dongqinglin.flina.rest.data.payload.request.WorksSaveRequest;
import me.dongqinglin.flina.rest.bussiness.WorksGroupService;
import me.dongqinglin.flina.rest.bussiness.WorksService;
import me.dongqinglin.flina.rest.bussiness.WorksWorksGroupServzice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(JwtConfig.AUTH_URL_PREFIX + "works")
public class WorksController {

    @Autowired
    private WorksService service;
    @Autowired
    private WorksGroupService worksGroupService;
    @Autowired
    private WorksWorksGroupServzice worksWorksGroupServzice;



    @GetMapping("/user/works/author/{authorId}")
    public Message getAuthorWorks(@PathVariable String authorId) {
        if(authorId.trim().isEmpty()) return Message.createIllegalMessage("不允许空值");
        Message message = Message.createSuccessMessage("查找成功");
        try {
//            List<Works> authorWorks = service.getAuthorWorks(Integer.parseInt(authorId));
//            message.setExtra(authorWorks);
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }
        return message;
    }

    @GetMapping("/user/works/{worksId}")
    public Message getWorks(@PathVariable String worksId) {
        if(worksId.trim().isEmpty()) return Message.createIllegalMessage("不允许空值");
        Message message = Message.createSuccessMessage("查找成功");
        try {
//            Works authorWorks = service.getWorks(Integer.parseInt(worksId));
//            message.setExtra(authorWorks);
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }
        return message;
    }

    @PostMapping("/user/works")
    public Message save(@RequestBody WorksSaveRequest request) {
        Message message = Message.createSuccessMessage("保存成功");
        try {
//            service.saveWorks(request);
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }
        return message;
    }

    @GetMapping("/user/works/author/{authorId}/works/{worksId}")
    public Message deleteWorks(@PathVariable String authorId, @PathVariable String worksId) {
        if(authorId.trim().isEmpty()) return Message.createIllegalMessage("不允许空值");
        if(worksId.trim().isEmpty()) return Message.createIllegalMessage("不允许空值");
        Message message = Message.createSuccessMessage("删除成功");
        try {
//            service.deleteWorks(Integer.parseInt(worksId), Integer.parseInt(authorId));
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }
        return message;
    }

    @GetMapping("/admin/works")
    public Message getAdminWorks(Long worksid) {
        Message message = Message.createSuccessMessage("查找成功");
        WorksToStyleWithStatus byId = service.findById(worksid);
        if(Objects.isNull(byId)) {
            return Message.createIllegalMessage("查找失败");
        }
        message.setData(byId);
        return message;
    }

    @GetMapping("/admin/all")
    public Message getAdminAllWorks() {
        Message message = Message.createSuccessMessage("查找成功");
        message.setData(service.getAll());
        return message;
    }

    @GetMapping("/admin/works/works-group/{worksGroupId}")
    public Message getGroupWorks(@PathVariable Integer worksGroupId) {
        Message message = Message.createSuccessMessage("查找成功");
//        Optional<WorksGroup> optional = worksGroupService.findOne(worksGroupId);
//        if (optional.isPresent()) {
//            WorksGroup worksGroup = optional.get();
//            worksGroup = worksWorksGroupServzice.getWorksGroup(worksGroup);
//            message.setExtra(worksGroup);
//        }else {
//            message = Message.createIllegalMessage("查找失败");
//        }


        return message;
    }

}
