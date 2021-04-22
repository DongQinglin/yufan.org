package me.dongqinglin.bootstarter.user.service;

import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;
import me.dongqinglin.bootstarter.Secure.repository.FlinaUserRepo;
import me.dongqinglin.bootstarter.user.bean.UserMeteSaveRequest;
import me.dongqinglin.bootstarter.user.converter.UserMetaConverter;
import me.dongqinglin.bootstarter.user.entity.UserMeta;
import me.dongqinglin.bootstarter.user.repository.UserMetaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMetaService {

    @Autowired
    private UserMetaRepo repo;
    @Autowired
    private FlinaUserRepo userRepo;

    public UserMeta getUserMeta(String userId) {
        Optional<FlinaUser> byId = userRepo.findById(Integer.parseInt(userId));
        if(!byId.isPresent()) throw new IllegalArgumentException("找不到用户");
        FlinaUser flinaUser = byId.get();
        Optional<UserMeta> byUser = repo.findByUser(flinaUser);
        if(!byUser.isPresent()) throw new IllegalArgumentException("找不到用户元数据");
        return byUser.get();

    }

    public void saveUserMeta(UserMeteSaveRequest request) {
        Optional<FlinaUser> byId = userRepo.findById(request.getUserId());
        if(! byId.isPresent()) throw new IllegalArgumentException("找不到用户");
        FlinaUser flinaUser = byId.get();
        Optional<UserMeta> byUser = repo.findByUser(flinaUser);

        if(byUser.isPresent()) {
            UserMeta userMeta = byUser.get();
            userMeta = UserMetaConverter.formUserMeta(request, userMeta);
            repo.save(userMeta);
        }else {
            UserMeta userMeta = UserMetaConverter.formReq(request);
            userMeta.setUser(flinaUser);
            repo.save(userMeta);
        }
    }
}
