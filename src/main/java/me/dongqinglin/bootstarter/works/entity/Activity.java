package me.dongqinglin.bootstarter.works.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity(name="activity")
@Table(name="activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
    @GenericGenerator(name = "myid", strategy = "me.dongqinglin.bootstarter.global.config.HibernateInsertConfig")
    private Integer id;
    @Column(name="title", nullable=false, insertable=true, updatable=true)
    private String title;
    @Column(name="start_time", nullable=false, insertable=true, updatable=true)
    private Date startTime;
    @Column(name="end_time", nullable=false, insertable=true, updatable=true)
    private Date endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
