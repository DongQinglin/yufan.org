package me.dongqinglin.flina.rest.data.schema.entity;

import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "prodworks")
public class ProdWorks {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @Column(name="title", nullable=false, insertable=true, updatable=true)
    private String title;
    @Column(name="content", nullable=false, insertable=true, updatable=true, columnDefinition="text")
    private String content;
    @Column(name="author_stu_id", nullable=false, insertable=true, updatable=true, columnDefinition="text")
    private String author_stu_id;
    @Column(name="style", nullable=false, insertable=true, updatable=true, columnDefinition="text")
    private String style;
    @Column(name="author_id", nullable=false, insertable=true, updatable=true, columnDefinition="text")
    private String author_id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor_stu_id() {
        return author_stu_id;
    }

    public void setAuthor_stu_id(String author_stu_id) {
        this.author_stu_id = author_stu_id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }
}
