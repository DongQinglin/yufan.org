package me.dongqinglin.flina.rest.data.schema.entity;


import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "produser_meta")
public class PropUserMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @Column(name="college", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String college;
    @Column(name="concat", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String concat;
    @Column(name="name", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String name;
    @Column(name="studentId", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String studentId;
    @Column(name="author_id", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private Long author_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getConcat() {
        return concat;
    }

    public void setConcat(String concat) {
        this.concat = concat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }
}
