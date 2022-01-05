package me.dongqinglin.flina.rest.bussiness;


import org.springframework.stereotype.Service;

@Service
public class WorksGroupService {
//    @Autowired
//    private GroupRepo repo;
//    @Autowired
//    private WorksWorksGroupServzice worksWorksGroupServzice;
//
//    public List<WorksGroup> listGroup(Integer activityId) {
//        return this.repo.findAllByActivity(activityId);
//    }
//
//    public Optional<WorksGroup> findOne(Integer groupId) {
//        return this.repo.findById(groupId);
//    }
//
//    public void addGroup(AddGroupReq addGroupReq) {
//        Integer sourceGroupId = addGroupReq.getSourceGroupId();
//        Optional<WorksGroup> byId = this.repo.findById(sourceGroupId);
//        if(byId.isPresent()) {
//            WorksGroup worksGroup = byId.get();
//            WorksGroup worksGroup1 = new WorksGroup();
//
//            worksGroup1.setTitle(addGroupReq.getGroupName());
//            worksGroup1.setActivity(worksGroup.getActivity());
//            WorksGroup target = this.repo.save(worksGroup1);
//            this.worksWorksGroupServzice.addGroupCopy(sourceGroupId, target.getId());
//        }
//
//    }

//    public void deleteGroupWorks(DeleteGroupWorksReq deleteGroupWorksReq) {
//        this.worksWorksGroupServzice.deleteGroupWorks(deleteGroupWorksReq);
//    }
}
