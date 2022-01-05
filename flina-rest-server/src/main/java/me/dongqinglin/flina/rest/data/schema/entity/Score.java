package me.dongqinglin.flina.rest.data.schema.entity;


import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity(name="score")
@Table(name="score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @Column(name="editor_score", nullable=true, insertable=true, updatable=true)
    private Long editorScore;
    @Column(name="editor_reason", nullable=true, insertable=true, updatable=true)
    private String editorReason;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEditorScore() {
        return editorScore;
    }

    public void setEditorScore(Long editorScore) {
        this.editorScore = editorScore;
    }

    public String getEditorReason() {
        return editorReason;
    }

    public void setEditorReason(String editorReason) {
        this.editorReason = editorReason;
    }
}
