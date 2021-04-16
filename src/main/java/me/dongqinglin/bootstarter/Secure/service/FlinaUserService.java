package me.dongqinglin.bootstarter.Secure.service;

import me.dongqinglin.bootstarter.Secure.converter.FlinaUserConverter;
import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;
import me.dongqinglin.bootstarter.Secure.repository.FlinaUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlinaUserService {
    @Autowired
    private FlinaUserRepo userRepo;
    
    public boolean auth(String username, String pass) {
        Optional<FlinaUser> option = userRepo.findByUsername(username);
        if(!option.isPresent()) return false;
        FlinaUser flinaUser = option.get();
        if(pass.hashCode() == flinaUser.getPassword()) return true;
        return false;
    }

    public boolean exists(String username) {
        return false;
    }

    public void addUser(String username, String pass, String email) {
        FlinaUser flinaUser = FlinaUserConverter.createFlinaUser(username, pass, email);
        System.out.println(flinaUser);
        userRepo.save(flinaUser);
    }

    public void saveUser(FlinaUser user, String pass) {
        user.setPassword(pass.hashCode());
        userRepo.save(user);
    }

    public FlinaUser getUser(String username) {
        Optional<FlinaUser> option = userRepo.findByUsername(username);
        if(!option.isPresent()) throw new IllegalArgumentException("找不到USER");
        return option.get();
    }
}
