package me.dongqinglin.flina.rest.index;

import me.dongqinglin.flina.rest.data.Helper.TextHelper;
import me.dongqinglin.flina.rest.data.schema.entity.relation.VisitorToUserWithStatus;
import me.dongqinglin.flina.rest.data.payload.request.*;
import me.dongqinglin.flina.rest.bussiness.AuthService;
import me.dongqinglin.flina.rest.data.payload.response.Message;
import me.dongqinglin.flina.rest.helper.RandomHelper;
import me.dongqinglin.flina.rest.helper.TimeHelper;
import me.dongqinglin.flina.rest.middleware.jwt.JwtConfig;
import me.dongqinglin.flina.rest.middleware.jwt.JwtHelper;
import me.dongqinglin.flina.rest.middleware.mail.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(JwtConfig.ANY_URL_PREFIX +"auth")
public class AuthController {

    private static int THIRTY_MINUTES = 30 * 60 ;
    private static int NUMBER_OF_DIGITS = 6;

    @Autowired private StringRedisTemplate redisTemplate;
    @Autowired
    AuthService userService;
    @Autowired
    private IMailService mailService;
    @Autowired
    private TemplateEngine templateEngine;

    @PostMapping("/signin")
    public Message logIn(@RequestBody UserLoginReq request) {
        Message message = Message.createSuccessMessage("登陆成功");
        try {
            VisitorToUserWithStatus user = userService.getUser(request.getUsername());
            if(user.getVisitor().getPass() == request.getPassword().hashCode()) {
                String token = JwtHelper.createToken(user.getVisitor().getName());
                message.setData(new UserLoginRes(token, user.getVisitor().getId(), user.getVisitor().getEmail(), user.getVisitor().getName()));
            }
            if(userService.auth(request.getUsername(), request.getPassword())) return message;
            return Message.createIllegalMessage("登陆失败");
        } catch (Exception e) {
            return Message.createIllegalMessage(e.toString());
        }
    }

    @PostMapping("/signup")
    public Message logUp(@RequestBody UserLogupReq request) {
        Message message = Message.createSuccessMessage("注册成功");
        try {
            if (userService.exists(request.getUsername())) return Message.createIllegalMessage("注册失败");
            String code = redisTemplate.opsForValue().get(request.getEmail());
            if (code == null) {
                return Message.createIllegalMessage("验证码过期");
            }
            Boolean flag = code.equals(request.getCode());
            if(!flag) return Message.createIllegalMessage("邮箱或验证码不匹配");
            userService.addUser(request.getUsername(), request.getPassword(), request.getEmail());
        }catch (Exception e) {
            return Message.createIllegalMessage(e.toString());
        }
        return message;
    }

    @PutMapping("/pass")
    public Message resetPassword(@RequestBody UserResetPassReq request) {
        Message message = Message.createSuccessMessage("重设成功");
        try {
            VisitorToUserWithStatus user = userService.getUser(request.getUsername());
            String code = redisTemplate.opsForValue().get(request.getEmail());
            if(TextHelper.isEmpty(request.getEmail())) return Message.createIllegalMessage("邮箱不能为空");
            if (code == null) {
                return Message.createIllegalMessage("验证码过期");
            }
            Boolean flag = request.getEmail().equals(user.getVisitor().getEmail()) && request.getCode().equals(code);
            if(!flag) return Message.createIllegalMessage("邮箱或验证码不匹配");
            userService.saveUser(user.getVisitor(), request.getPassword());

            return message;
        }catch (Exception e) {
            return Message.createIllegalMessage(e.toString());
        }
    }

    // todo 406
    @GetMapping("/code")
    public Message getVerfication(String email) {
        try {
            Context context = new Context();
            String authCode = RandomHelper.getNumber(NUMBER_OF_DIGITS);

            context.setVariable("code", authCode);
            context.setVariable("date", TimeHelper.toTimeStamp(TimeHelper.getNow()));
            String emailContent = templateEngine.process("email", context);
            mailService.sendHtmlMail(email, "[君名]请查收您的验证码", emailContent);
            redisTemplate.opsForValue().set(email, authCode, THIRTY_MINUTES, TimeUnit.SECONDS);
        }catch (Exception ex){
            ex.printStackTrace();
            return Message.createIllegalMessage("邮件发送失败!!");
        }
        Message message = Message.createSuccessMessage("邮件发送成功!!");
        return message;
    }


    @GetMapping("/name")
    public Message getName(String email, String code) {
        Message message = Message.createSuccessMessage("获取成功");
        try {
            VisitorToUserWithStatus user = userService.getUserByEmail(email);
            String redisCode = redisTemplate.opsForValue().get(email);
            if (redisCode == null) {
                return Message.createIllegalMessage("验证码过期");
            }
            if(TextHelper.isEmpty(code)) {
                return Message.createIllegalMessage("不允许空值");
            }
            Boolean flag = email.equals(user.getVisitor().getEmail()) && code.equals(redisCode);
            if(!flag) return Message.createIllegalMessage("邮箱或验证码不匹配");
            message.setContent(user.getVisitor().getName());
        }catch (Exception ex){
            return Message.createIllegalMessage("获取失败");
        }
        return message;
    }

}
