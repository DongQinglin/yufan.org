package me.dongqinglin.bootstarter.works.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.sql.Date;


@Entity(name="score")
@Table(name="score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
    @GenericGenerator(name = "myid", strategy = "me.dongqinglin.bootstarter.global.config.HibernateInsertConfig")
    private Integer id;
    @Column(name="editor_id", nullable=true, insertable=true, updatable=true)
    private Integer editorId;
    @Column(name="editor_name", nullable=true, insertable=true, updatable=true)
    private String editorName;
    @Column(name="edited_time", nullable=true, insertable=true, updatable=true)
    private Date editedTime;
    @Column(name="editor_score", nullable=true, insertable=true, updatable=true)
    private Integer editorScore;
    @Column(name="editor_reason", nullable=true, insertable=true, updatable=true)
    private String editorReason;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="work_id")
    private Works works;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEditorId() {
        return editorId;
    }

    public void setEditorId(Integer editorId) {
        this.editorId = editorId;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public Date getEditedTime() {
        return editedTime;
    }

    public void setEditedTime(Date editedTime) {
        this.editedTime = editedTime;
    }

    public Integer getEditorScore() {
        return editorScore;
    }

    public void setEditorScore(Integer editorScore) {
        this.editorScore = editorScore;
    }

    public String getEditorReason() {
        return editorReason;
    }

    public void setEditorReason(String editorReason) {
        this.editorReason = editorReason;
    }

    public Works getWorks() {
        return works;
    }

    public void setWorks(Works works) {
        this.works = works;
    }
}
