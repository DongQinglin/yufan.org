package me.dongqinglin.flina.rest.data.internal;

import java.sql.Timestamp;

public interface WorksPart {
    public Integer getId();
    public String getTitle();
    public String getStyle();
    public String getAuthorStuId();
    public Boolean getEnable() ;
    public Timestamp getUpTime();
    public Integer getUser();

}
