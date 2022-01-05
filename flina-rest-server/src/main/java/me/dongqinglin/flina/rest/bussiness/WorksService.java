package me.dongqinglin.flina.rest.bussiness;


import me.dongqinglin.flina.rest.data.schema.entity.WorksToStyleWithStatus;
import me.dongqinglin.flina.rest.data.schema.repository.WorksToRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorksService {

    @Autowired
    private WorksToRepo worksToRepoo;
//    @Autowired
//    private FlinaUserRepo userRepo;
//    @Autowired
//    private UserMetaRepo userMetaRepo;
//    @Autowired
//    private ActivityRepo activityRepo;
//    @Autowired
//    private GroupRepo worksGroupRepo;
//
//    @Autowired
//    private WorksWorksGroupRepo worksWorksGroupRepo;
//

    public List<WorksToStyleWithStatus> getAll() {
        List<WorksToStyleWithStatus> all = worksToRepoo.findAll();
        all.stream().forEach(e -> e.setContent(null));
        return all;
    }

    public WorksToStyleWithStatus findById(Long worksid) {
        Optional<WorksToStyleWithStatus> oneByWorks_id = worksToRepoo.findById(worksid);
        if(oneByWorks_id.isPresent()) {
            return oneByWorks_id.get();
        }
        return null;
    }
//    public void saveWorks(WorksSaveRequest request) {
//
//        if(request.getContent().trim().isEmpty()) throw new IllegalArgumentException("不允许空值");
//        if(request.getStyle().trim().isEmpty()) throw new IllegalArgumentException("不允许空值");
//        if(request.getTitle().trim().isEmpty()) throw new IllegalArgumentException("不允许空值");
//        if(request.getActivityId() <= 0) throw new IllegalArgumentException("不允许空值");
//        if(request.getUserId() <= 0 ) throw new IllegalArgumentException("不允许空值");
//        Optional<FlinaUser> byId = userRepo.findById(request.getUserId());
//        if(!byId.isPresent()) throw new IllegalArgumentException("找不到用户");
//        FlinaUser flinaUser = byId.get();
//        Optional<UserMeta> byUser = userMetaRepo.findByUser(flinaUser.getId());
//        if(!byUser.isPresent()) throw new IllegalArgumentException("未找到实名信息，请下滑进行实名认证");
//        UserMeta userMeta = byUser.get();
//        Optional<Activity> byId1 = activityRepo.findById(request.getActivityId());
//        if(!byId1.isPresent()) throw new IllegalArgumentException("找不到活动");
//        Activity activity = byId1.get();
//        List<WorksGroup> groups = worksGroupRepo.findAllByActivity(request.getActivityId());
//        Iterator<WorksGroup> iterator = groups.iterator();
//        WorksGroup theDefaultGroup = null;
//        while (iterator.hasNext()){
//            WorksGroup next = iterator.next();
//            if(next.getTitle().equals("默认")){
//                theDefaultGroup = next;
//                break;
//            }
//        }
//        if(theDefaultGroup == null) throw new IllegalArgumentException("找不到默认作品组");
//
//        if(request.getId() > 0) {
//            updateWorks(request);
//            return;
//        }
//        Works theWork = WorksConverter.fromReq(request);
//        theWork.setAuthorStuId(userMeta.getStudentId());
//        theWork.setUser(flinaUser);
//        theWork.setActivity(activity);
//        Works save = repo.save(theWork);
//
//        WorksWorksGroup worksWorksGroup = new WorksWorksGroup();
//        worksWorksGroup.setWorksGroup(theDefaultGroup.getId());
//        worksWorksGroup.setWorks(save.getId());
//
//        worksWorksGroupRepo.save(worksWorksGroup);
//    }
//
//    private void updateWorks(WorksSaveRequest request) {
//        Optional<Works> byId = repo.findById(request.getId());
//        if(!byId.isPresent()) throw new IllegalArgumentException("找不到作品");
//        Works works = byId.get();
//        if(!works.getEnable()) {
//            throw new IllegalArgumentException("违规操作，不允许删除作品");
//        }
//        WorksConverter.fromDateBase( works, request);
//        repo.save(works);
//    }
//
//    public List<Works> getAuthorWorks(int userId) {
//        List<Works> worksList = repo.findByUser_Id(userId);
//        return worksList;
//    }
//
//    @Transactional
//    public Works getWorks(int worksId) {
//        Optional<Works> byId = repo.findById(worksId);
//        if(!byId.isPresent()) throw new IllegalArgumentException("找不到作品");
//        return byId.get();
//    }
//
//    public void deleteWorks(int worksId, int authorId) {
//        // System.out.println(authorId);
//        Optional<Works> byId = repo.findById(worksId);
//        if(!byId.isPresent()) throw new IllegalArgumentException("找不到作品");
//        Works works = byId.get();
//        List<WorksWorksGroup> allByWorksId = worksWorksGroupRepo.findAllByWorks(works.getId());
//        worksWorksGroupRepo.deleteAll(allByWorksId);
//        if(!works.getEnable()) {
//            throw new IllegalArgumentException("违规操作，不允许删除作品");
//        }
//        if(works.getUser() != authorId) {
//            throw new IllegalArgumentException("违规操作，不允许删除作品");
//        }
//        repo.deleteById(worksId);
//    }
//
//    public List<String> findTitleByIds(Iterable<Integer> works) {
//        return this.repo.getTilteByIds(works);
//    }
//
//    public List<WorksPart> findTitleAndIdByIds(Iterable<Integer> works) {
//        return this.repo.getTilteAndIdByIds(works);
//    }
//
//    public Set<Integer> findIdByIds(Iterable<Integer> works) {
//        return this.repo.getIdByIds(works);
//    }
}
