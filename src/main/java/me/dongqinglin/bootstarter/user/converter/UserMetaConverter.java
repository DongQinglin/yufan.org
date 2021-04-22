package me.dongqinglin.bootstarter.user.converter;

import me.dongqinglin.bootstarter.user.bean.UserMeteSaveRequest;
import me.dongqinglin.bootstarter.user.entity.UserMeta;

public class UserMetaConverter {


    public static UserMeta formReq(UserMeteSaveRequest request) {
        UserMeta userMeta = new UserMeta();
        userMeta.setName(request.getName());
        userMeta.setStudentId(request.getStudentId());
        userMeta.setCollege(request.getCollege());
        userMeta.setConcat(request.getConcat());
        userMeta.setEnable(true);
        return userMeta;
    }

    public static UserMeta formUserMeta(UserMeteSaveRequest request, UserMeta userMeta) {
        userMeta.setName(request.getName());
        userMeta.setStudentId(request.getStudentId());
        userMeta.setCollege(request.getCollege());
        userMeta.setConcat(request.getConcat());
        return userMeta;
    }
}
