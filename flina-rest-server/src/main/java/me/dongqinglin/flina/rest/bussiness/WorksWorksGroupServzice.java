package me.dongqinglin.flina.rest.bussiness;

import org.springframework.stereotype.Service;

@Service
public class WorksWorksGroupServzice {

//    @Autowired
//    private WorksWorksGroupRepo repo;
//    @Autowired
//    private WorksService worksService;
//
//    public WorksGroup getWorksGroup(WorksGroup worksGroup) {
//        List<WorksWorksGroup> allByWorksGroup = this.repo.findAllByWorksGroupAndEnable(worksGroup.getId(), true);
//        Set<Integer> works = allByWorksGroup.stream().map(WorksWorksGroup::getWorks).collect(Collectors.toSet());
//        // List<String> allByIds = this.worksService.findTitleByIds(works);
//        // Set<Integer> idByIds = this.worksService.findIdByIds(works);
//        List<WorksPart> titleAndIdByIds = this.worksService.findTitleAndIdByIds(works);
//        worksGroup.setWorksList(titleAndIdByIds);
//        return worksGroup;
//    }
//
//
//    public void addGroupCopy(Integer sourceGroupId, Integer targetId) {
//        List<WorksWorksGroup> worksWorksGroupList = this.repo.findAllByWorksGroupAndEnable(sourceGroupId, true);
//        List<WorksWorksGroup> tempList = new ArrayList<>();
//        for (int i = 0; i < worksWorksGroupList.size(); i++) {
//            WorksWorksGroup temp = new WorksWorksGroup();
//            WorksWorksGroup worksWorksGroup = worksWorksGroupList.get(i);
//            temp.setWorksGroup(targetId);
//            temp.setEnable(true);
//            temp.setWorks(worksWorksGroup.getWorks());
//            tempList.add(temp);
//        }
//        this.repo.saveAll(tempList);
//    }
//
//    public void deleteGroupWorks(DeleteGroupWorksReq deleteGroupWorksReq) {
//        List<WorksWorksGroup> worksWorksGroupList = this.repo.findAllByWorksGroupAndEnable(deleteGroupWorksReq.getGroupId(), true);
//        List<Integer> worksIdList = deleteGroupWorksReq.getWorksIds();
//        for (int i = 0; i < worksWorksGroupList.size(); i++) {
//            WorksWorksGroup worksWorksGroup = worksWorksGroupList.get(i);
//            boolean flag = worksIdList.contains(worksWorksGroup.getWorks());
//            if(flag) {
//                worksWorksGroup.setEnable(false);
//            }
//        }
//        this.repo.saveAll(worksWorksGroupList);
//    }
}
