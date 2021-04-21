package me.dongqinglin.bootstarter.Secure.controller;

import me.dongqinglin.bootstarter.Secure.bean.*;
import me.dongqinglin.bootstarter.Secure.config.ServerSecureConfigContrant;
import me.dongqinglin.bootstarter.Secure.converter.JwtState;
import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;
import me.dongqinglin.bootstarter.Secure.service.FlinaUserService;
import me.dongqinglin.bootstarter.global.bean.Message;
import me.dongqinglin.bootstarter.global.config.ServerConfigContrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ServerConfigContrant.PREFIX_URL + "/any/user")
public class FlinaUserController {

    @Autowired
    JwtState jwtUtil;

    @Autowired
    FlinaUserService userService;

    @PostMapping("/log-in")
    public Message logIn(@RequestBody UserLoginReq request) {
        Message message = Message.createSuccessMessage("登陆成功");
        FlinaUser user = userService.getUser(request.getUsername());
        if(user.getPassword() == request.getPassword().hashCode()) {
            FlinaUserDetail flinaUserDetail = new FlinaUserDetail(user);
            final String jwt = jwtUtil.generateToken(flinaUserDetail);

            message.setExtra(new UserLoginRes(jwt, user.getId()));
        }
        if(userService.auth(request.getUsername(), request.getPassword())) return message;
        return Message.createIllegalMessage("登陆失败");
    }

    @PostMapping("/log-up")
    public Message logUp(@RequestBody UserLogupReq request) {
        Message message = Message.createSuccessMessage("注册成功");
        if(userService.exists(request.getUsername())) return Message.createIllegalMessage("注册失败");
        userService.addUser(request.getUsername(), request.getPassword(), request.getEmail());
        return message;
    }

    @PostMapping("/reset-password")
    public Message resetPassword(@RequestBody UserResetPassReq request) {
        Message message = Message.createMessage("重设成功", 200);
        FlinaUser user = userService.getUser(request.getUsername());
        Boolean flag = request.getEmail().equals(user.getEmail()) && request.getCode().equals(ServerSecureConfigContrant.CODE);
        if(!flag) return Message.createIllegalMessage("重设失败");
        userService.saveUser(user, request.getPassword());
        return message;
    }

    @PostMapping("/verification-code")
    public Message getVerfication(@RequestBody UserGetVerficationReq request) {
        Message message = Message.createMessage(ServerSecureConfigContrant.CODE, 200);
        return message;
    }

}
