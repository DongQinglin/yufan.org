package me.dongqinglin.bootstarter.Secure.service;

import me.dongqinglin.bootstarter.Secure.converter.FlinaUserConverter;
import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;
import me.dongqinglin.bootstarter.Secure.repository.FlinaUserRepo;
import me.dongqinglin.bootstarter.global.bean.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlinaUserService {
    @Autowired
    private FlinaUserRepo userRepo;
    
    public boolean auth(String username, String pass) {
        if(username.trim().isEmpty())  throw new IllegalArgumentException("不允许空值");
        if(pass.trim().isEmpty())  throw new IllegalArgumentException("不允许空值");

        Optional<FlinaUser> option = userRepo.findByUsername(username);
        if(!option.isPresent()) return false;
        FlinaUser flinaUser = option.get();
        if(pass.hashCode() == flinaUser.getPassword()) return true;
        return false;
    }

    public boolean exists(String username) {
        if(username.trim().isEmpty())  throw new IllegalArgumentException("不允许空值");
        Optional<FlinaUser> byUsername = userRepo.findByUsername(username);
        if(!byUsername.isPresent()) return false;
        return true;
    }

    public Message addUser(String username, String pass, String email) {
        if(FlinaUserConverter.isEmpty(username, pass,email)) throw new IllegalArgumentException("不允许空值");
        FlinaUser flinaUser = FlinaUserConverter.createFlinaUser(username, pass, email);
        System.out.println(flinaUser);
        userRepo.save(flinaUser);
        return Message.createSuccessMessage("注册成功");
    }

    public void saveUser(FlinaUser user, String pass) {
        user.setPassword(pass.hashCode());
        userRepo.save(user);
    }

    public FlinaUser getUser(String username) {
        if(username.trim().isEmpty())  throw new IllegalArgumentException("不允许空值");
        Optional<FlinaUser> option = userRepo.findByUsername(username);
        if(!option.isPresent()) throw new IllegalArgumentException("找不到USER");
        return option.get();
    }
}
