package me.dongqinglin.flina.rest.bussiness;

import me.dongqinglin.flina.rest.data.payload.request.UserMeteSaveRequest;
import me.dongqinglin.flina.rest.data.payload.response.UserMetaViewResponse;
import me.dongqinglin.flina.rest.data.schema.entity.Status;
import me.dongqinglin.flina.rest.data.schema.entity.Visitor;
import me.dongqinglin.flina.rest.data.schema.entity.relation.VisitorToStudentWithStatus;
import me.dongqinglin.flina.rest.data.schema.entity.relation.VisitorToUserWithStatus;
import me.dongqinglin.flina.rest.data.schema.repository.VisitorRepo;
import me.dongqinglin.flina.rest.data.schema.repository.relation.VisitorToStudentRepo;
import me.dongqinglin.flina.rest.data.schema.repository.relation.VisitorToUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserMetaService {

    @Autowired
    private VisitorToUserRepo visitorToUserRepo;
    @Autowired
    private VisitorToStudentRepo visitorToStudentRepo;

    @Autowired
    private VisitorRepo visitorRepo;
//    @Autowired
//    private UserMetaRepo repo;
//    @Autowired
//    private FlinaUserRepo userRepo;
//
//    public UserMeta getUserMeta(String userId) {
//        if(userId.trim().isEmpty()) throw new IllegalArgumentException("不允许空值");
//        Optional<FlinaUser> byId = userRepo.findById(Integer.parseInt(userId));
//        if(!byId.isPresent()) throw new IllegalArgumentException("找不到用户");
//        FlinaUser flinaUser = byId.get();
//        Optional<UserMeta> byUser = repo.findByUser(flinaUser.getId());
//        if(!byUser.isPresent()) throw new IllegalArgumentException("找不到用户元数据");
//        return byUser.get();

//    }

    public void saveUserMeta(UserMeteSaveRequest request) {
        if(request.getCollege().trim().isEmpty()) throw new IllegalArgumentException("不允许空值");
        if(request.getConcat().trim().isEmpty()) throw new IllegalArgumentException("不允许空值");
        if(request.getEmail().trim().isEmpty()) throw new IllegalArgumentException("不允许空值");
        if(request.getName().trim().isEmpty()) throw new IllegalArgumentException("不允许空值");

//        Optional<FlinaUser> byId = userRepo.findById(request.getUserId());
//        if(! byId.isPresent()) throw new IllegalArgumentException("找不到用户");
//        FlinaUser flinaUser = byId.get();
//        Optional<UserMeta> byUser = repo.findByUser(flinaUser.getId());
//
//        if(byUser.isPresent()) {
//            UserMeta userMeta = byUser.get();
//            userMeta = UserMetaConverter.formUserMeta(request, userMeta);
//            repo.save(userMeta);
//        }else {
//            UserMeta userMeta = UserMetaConverter.formReq(request);
//            userMeta.setUser(flinaUser.getId());
//            repo.save(userMeta);
//        }
    }


    public Collection<UserMetaViewResponse> getAllUserMetaView() {
        List<Visitor> all = visitorRepo.findAll();
        List<Long> allid = all.stream().map(Visitor::getId).collect(Collectors.toList());

        List<VisitorToUserWithStatus> allByVisitor_idIn = visitorToUserRepo.findAllByVisitor_idIn(allid);
        List<VisitorToStudentWithStatus> allByVisitor_idIn1 = visitorToStudentRepo.findAllByVisitor_idIn(allid);

        Map<Long, UserMetaViewResponse> idToView = new HashMap();
        allByVisitor_idIn.forEach(ele -> {
            if(Objects.nonNull(ele)  && Objects.nonNull(ele.getVisitor()) && Objects.nonNull(ele.getVisitor().getId())) {
                UserMetaViewResponse userMetaViewResponse = new UserMetaViewResponse();
                userMetaViewResponse.setUserId(ele.getVisitor().getId());
                userMetaViewResponse.setName(ele.getVisitor().getName());
                userMetaViewResponse.setEmail(ele.getVisitor().getEmail());
                if(Objects.nonNull(ele.getUser())) {
                    userMetaViewResponse.setRoles(ele.getUser().getRole().getName());
                    userMetaViewResponse.setLevel(ele.getUser().getLevel().getName());
                }

                idToView.put(ele.getVisitor().getId(), userMetaViewResponse);
            }
        });
        allByVisitor_idIn1.forEach(ele -> {
            if(Objects.nonNull(ele)  && Objects.nonNull(ele.getVisitor()) && Objects.nonNull(ele.getVisitor().getId())) {
                UserMetaViewResponse userMetaViewResponse = new UserMetaViewResponse();

                if(idToView.containsKey(ele.getVisitor().getId())) {
                    userMetaViewResponse = idToView.get(ele.getVisitor().getId());
                }
                if(Objects.nonNull(ele.getStudent())) {
                    userMetaViewResponse.setConcat(ele.getStudent().getContact());
                    userMetaViewResponse.setTrueName(ele.getStudent().getName());
                    userMetaViewResponse.setStudentId(ele.getStudent().getStudentId());
                    userMetaViewResponse.setCollege(ele.getStudent().getCollege());
                }

                if( Objects.nonNull(ele.getStatus())  && Status.StatusEnum.ENABLE.toString().equals(ele.getStatus().getName())) {
                    userMetaViewResponse.setEnable(true);
                }
            }
        });

        return idToView.values();
    }
}
