package me.dongqinglin.flina.rest.index;

import me.dongqinglin.flina.rest.data.payload.response.Message;
import me.dongqinglin.flina.rest.data.payload.response.UserMetaViewResponse;
import me.dongqinglin.flina.rest.middleware.tomcat.ServerConfigContrant;
import me.dongqinglin.flina.rest.data.payload.response.IUserMetaViewResponse;
import me.dongqinglin.flina.rest.data.payload.request.UserMeteSaveRequest;
import me.dongqinglin.flina.rest.bussiness.UserMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(ServerConfigContrant.FLINA_1_0)
public class UserMetaController {

    @Autowired
    private UserMetaService service;

    @GetMapping("/user/usermeta/{id}")
    public Message getUsermeta(@PathVariable String id) {
        Message message = Message.createSuccessMessage("查找成功");
        try {
//            message.setData(service.getUserMeta(id));
        } catch (Exception e) {
            message = Message.createIllegalMessage("未找到实名信息");
        }
        return message;
    }

    @PostMapping("/user/usermeta")
    public Message save(@RequestBody UserMeteSaveRequest request) {
        Message message = Message.createSuccessMessage("保存成功");
        try {
            service.saveUserMeta(request);
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }


        return message;
    }

    @GetMapping("admin/usermeta")
    public Message getAllUserMetaView() {
        Message message = Message.createSuccessMessage("保存成功");
        try {
            Collection<UserMetaViewResponse> allUserMetaView = service.getAllUserMetaView();
            message.setData(allUserMetaView);
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }


        return message;
    }
}
