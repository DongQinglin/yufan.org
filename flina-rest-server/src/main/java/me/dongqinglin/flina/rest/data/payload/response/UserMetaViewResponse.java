package me.dongqinglin.flina.rest.data.payload.response;

public class UserMetaViewResponse implements IUserMetaViewResponse {
    private Long userId;

    private String trueName;
    private String name;
    private String studentId;
    private String email;
    private String concat;
    private String college;
    private String roles;
    private String level;
    private Long usermetaId;
    // 注意，这是userMeta表中的可用性，主要表示黑名单
    private Boolean enable;


    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    @Override
    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getUsermetaId() {
        return usermetaId;
    }

    public void setUsermetaId(Long usermetaId) {
        this.usermetaId = usermetaId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getConcat() {
        return concat;
    }

    public void setConcat(String concat) {
        this.concat = concat;
    }

    @Override
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
