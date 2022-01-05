package me.dongqinglin.flina.rest.index;


import me.dongqinglin.flina.rest.bussiness.UserMetaService;
import me.dongqinglin.flina.rest.data.payload.response.IUserMetaViewResponse;
import me.dongqinglin.flina.rest.data.payload.response.Message;
import me.dongqinglin.flina.rest.data.payload.response.UserMetaViewResponse;
import me.dongqinglin.flina.rest.data.schema.entity.User;
import me.dongqinglin.flina.rest.middleware.jwt.JwtConfig;
import me.dongqinglin.flina.rest.middleware.role.HasRole;
import me.dongqinglin.flina.rest.middleware.tomcat.ServerConfigContrant;
import me.dongqinglin.flina.rest.bussiness.AuthService;
import me.dongqinglin.flina.rest.data.payload.request.ChangeRoleReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(JwtConfig.AUTH_URL_PREFIX + "user")
public class UserController {

    @Autowired
    AuthService authService;
    @Autowired
    UserMetaService userMetaService;

    @GetMapping("/roles")
    public Message getAllRole() {
        Message message = Message.createSuccessMessage("查询成功");
        try {
            List<User> allRole = authService.getAllRole();
            message.setData(allRole);
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }
        System.out.println(message);
        return message;
    }

    @GetMapping("/all")
    public Message getAllUser() {
        Message message = Message.createSuccessMessage("查询成功");
        try {
            Collection<UserMetaViewResponse> allUserMetaView = userMetaService.getAllUserMetaView();
            message.setData(allUserMetaView);
        } catch (Exception e) {
            message = Message.createIllegalMessage(e.toString());
        }
        System.out.println(message);
        return message;
    }

}
