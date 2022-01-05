package me.dongqinglin.flina.rest.data.payload.request;

import java.util.List;

public class DeleteGroupWorksReq {
    private Integer groupId;

    private List<Integer> worksIds;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<Integer> getWorksIds() {
        return worksIds;
    }

    public void setWorksIds(List<Integer> worksIds) {
        this.worksIds = worksIds;
    }
}
