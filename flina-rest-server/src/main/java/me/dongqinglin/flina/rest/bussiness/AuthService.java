package me.dongqinglin.flina.rest.bussiness;

import me.dongqinglin.flina.rest.data.Helper.TextHelper;
import me.dongqinglin.flina.rest.data.payload.response.Message;
import me.dongqinglin.flina.rest.data.payload.request.ChangeRoleReq;
import me.dongqinglin.flina.rest.data.schema.entity.*;
import me.dongqinglin.flina.rest.data.schema.entity.relation.VisitorToUserWithStatus;
import me.dongqinglin.flina.rest.data.schema.repository.LevelRepo;
import me.dongqinglin.flina.rest.data.schema.repository.StatusRepo;
import me.dongqinglin.flina.rest.data.schema.repository.UserRepo;
import me.dongqinglin.flina.rest.data.schema.repository.VisitorRepo;
import me.dongqinglin.flina.rest.data.schema.repository.relation.VisitorToUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private VisitorToUserRepo userToRepo;
    @Autowired
    private VisitorRepo visitorRepo;
    @Autowired
    private StatusRepo statusRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LevelRepo levelRepo;

    public boolean auth(String username, String pass) {
        if(TextHelper.isEmpty(username))  throw new IllegalArgumentException("不允许空值");
        if(TextHelper.isEmpty(pass))  throw new IllegalArgumentException("不允许空值");

        Optional<VisitorToUserWithStatus> option = userToRepo.findOneByVisitor_name(username);
        if(!option.isPresent()) return false;
        VisitorToUserWithStatus flinaUser = option.get();
        if(pass.hashCode() == flinaUser.getVisitor().getPass()) return true;
        return false;
    }

    public boolean exists(String username) {
        if(TextHelper.isEmpty(username))  throw new IllegalArgumentException("不允许空值");
        Optional<VisitorToUserWithStatus> option = userToRepo.findOneByVisitor_name(username);
        if(!option.isPresent()) return false;
        return true;
    }

    @Transactional
    public Message addUser(String username, String pass, String email) {
        if(TextHelper.isEmpty(username))  throw new IllegalArgumentException("不允许空值");
        if(TextHelper.isEmpty(pass))  throw new IllegalArgumentException("不允许空值");
        if(TextHelper.isEmpty(email))  throw new IllegalArgumentException("不允许空值");

        Visitor visitor = new Visitor();
        visitor.setName(username);
        visitor.setPass(pass.hashCode());
        visitor.setEmail(email);
        Visitor save = visitorRepo.save(visitor);
        User user = userRepo.findOneByName(User.RoleEnum.VISITOR.toString());
        Status status = statusRepo.findOneByName(Status.StatusEnum.ENABLE.toString());
        userToRepo.save(new VisitorToUserWithStatus(save, user, status));
        return Message.createSuccessMessage("注册成功");
    }


    @Transactional
    public void saveUser(Visitor user, String pass) {
        if(TextHelper.isEmpty(pass)) throw new IllegalArgumentException("不允许空值");
        user.setPass(pass.hashCode());
        visitorRepo.save(user);
    }

    public VisitorToUserWithStatus getUser(String username) {
        if(TextHelper.isEmpty(username))  throw new IllegalArgumentException("不允许空值");
        Optional<VisitorToUserWithStatus> option = userToRepo.findOneByVisitor_name(username);
        if(!option.isPresent()) throw new IllegalArgumentException("找不到USER");
        return option.get();
    }

    public VisitorToUserWithStatus getUserByEmail(String email) {
        if(TextHelper.isEmpty(email))  throw new IllegalArgumentException("不允许空值");
        Optional<VisitorToUserWithStatus> option = userToRepo.findOneByVisitor_email(email);
        if(!option.isPresent()) throw new IllegalArgumentException("找不到USER");
        return option.get();
    }


    @Transactional
    public void changeRole(ChangeRoleReq changeRoleReq) {
        Optional<VisitorToUserWithStatus> byId = userToRepo.findOneByVisitor_id(changeRoleReq.getUserId().longValue());
        if(byId.isPresent()) {
            VisitorToUserWithStatus flinaUser = byId.get();
            User user = flinaUser.getUser();
            user.setLevel(levelRepo.findOneByName(changeRoleReq.getLevel().toString()));
            User.RoleEnum roleEnum = User.RoleEnum.valueOf(changeRoleReq.getRole());
            if(
                    !flinaUser.getUser().getName().equals(changeRoleReq.getRole())
            ){
                User oneByName = userRepo.findOneByName(changeRoleReq.getRole());
                flinaUser.setUser(oneByName);
                Status stauts = statusRepo.findOneByName(Status.StatusEnum.ENABLE.toString());
                flinaUser.setStatus(stauts);
            }
            userToRepo.save(flinaUser);
        }
    }

    public List<User> getAllRole() {
        List<User> result = userRepo.findAll();
        return result;
    }
}
