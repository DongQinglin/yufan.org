package me.dongqinglin.flina.rest.index;

import me.dongqinglin.flina.rest.data.payload.response.Message;
import me.dongqinglin.flina.rest.middleware.tomcat.ServerConfigContrant;
import me.dongqinglin.flina.rest.data.payload.request.AddGroupReq;
import me.dongqinglin.flina.rest.data.payload.request.DeleteGroupWorksReq;
import me.dongqinglin.flina.rest.bussiness.WorksGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ServerConfigContrant.FLINA_1_0)
public class WorksGroupController {

    @Autowired
    private WorksGroupService worksGroupService;

    @GetMapping("/admin/works-group/activity/{activityId}")
    public Message getWorksGroups(@PathVariable Integer activityId) {
        Message message = Message.createSuccessMessage("查找成功");
//        List<WorksGroup> worksGroups = worksGroupService.listGroup(activityId);
        return message.setData(null);
    }


    @PostMapping("/admin/works-group/")
    public Message addGroup(@RequestBody AddGroupReq addGroupReq) {
        Message message = Message.createSuccessMessage("查找成功");
//        worksGroupService.addGroup(addGroupReq);
        return message;
    }

    @PutMapping("admin/works-group")
    public Message deleteGroupWorks(@RequestBody DeleteGroupWorksReq deleteGroupWorksReq) {
        Message message = Message.createSuccessMessage("查找成功");
//        worksGroupService.deleteGroupWorks(deleteGroupWorksReq);
        return message;
    }
}
