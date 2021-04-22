package me.dongqinglin.bootstarter.works.service;


import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;
import me.dongqinglin.bootstarter.Secure.repository.FlinaUserRepo;
import me.dongqinglin.bootstarter.user.entity.UserMeta;
import me.dongqinglin.bootstarter.user.repository.UserMetaRepo;
import me.dongqinglin.bootstarter.works.bean.WorksSaveRequest;
import me.dongqinglin.bootstarter.works.converter.WorksConverter;
import me.dongqinglin.bootstarter.works.entity.Activity;
import me.dongqinglin.bootstarter.works.entity.Works;
import me.dongqinglin.bootstarter.works.entity.WorksGroup;
import me.dongqinglin.bootstarter.works.repository.ActivityRepo;
import me.dongqinglin.bootstarter.works.repository.WorksGroupRepo;
import me.dongqinglin.bootstarter.works.repository.WorksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WorksService {
    @Autowired
    private WorksRepo repo;
    @Autowired
    private FlinaUserRepo userRepo;
    @Autowired
    private UserMetaRepo userMetaRepo;
    @Autowired
    private ActivityRepo activityRepo;
    @Autowired
    private WorksGroupRepo worksGroupRepo;

    public void saveWorks(WorksSaveRequest request) {
        Optional<FlinaUser> byId = userRepo.findById(request.getUserId());
        if(!byId.isPresent()) throw new IllegalArgumentException("找不到用户");
        FlinaUser flinaUser = byId.get();
        Optional<UserMeta> byUser = userMetaRepo.findByUser(flinaUser);
        if(!byUser.isPresent()) throw new IllegalArgumentException("找不到用户元数据");
        UserMeta userMeta = byUser.get();
        Optional<Activity> byId1 = activityRepo.findById(request.getActivityId());
        if(!byId1.isPresent()) throw new IllegalArgumentException("找不到活动");
        Activity activity = byId1.get();
        Set<WorksGroup> groups = worksGroupRepo.findByActivity(activity);
        Iterator<WorksGroup> iterator = groups.iterator();
        WorksGroup theDefaultGroup = null;
        while (iterator.hasNext()){
            WorksGroup next = iterator.next();
            if(next.getTitle().equals("默认")){
                theDefaultGroup = next;
            }
        }
        if(theDefaultGroup == null) throw new IllegalArgumentException("找不到默认作品组");
        Works theWork = WorksConverter.fromReq(request);
        theWork.setAuthorStuId(userMeta.getStudentId());
        theWork.setUser(flinaUser);
        theWork.setActivity(activity);
        theWork.setWorksGroups(theDefaultGroup);
        repo.save(theWork);
    }

    public List<Works> getAuthorWorks(int userId) {
        List<Works> worksList = repo.findByUser_Id(userId);
        return worksList;
    }

    public Works getWorks(int worksId) {
        Optional<Works> byId = repo.findById(worksId);
        if(!byId.isPresent()) throw new IllegalArgumentException("找不到作品");
        return byId.get();
    }

    public void deleteWorks(int worksId) {
        repo.deleteById(worksId);
    }
}
