package me.dongqinglin.flina.rest.data.payload.request;


public class AddGroupReq {
    private Integer sourceGroupId;

    private String groupName;

    public Integer getSourceGroupId() {
        return sourceGroupId;
    }

    public void setSourceGroupId(Integer sourceGroupId) {
        this.sourceGroupId = sourceGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
