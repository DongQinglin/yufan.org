package me.dongqinglin.flina.rest.data.schema.entity;


import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * Student info
 */
@Entity
@Table(name = "Student", uniqueConstraints = {
        @UniqueConstraint(name = "uc_student_studentid", columnNames = {"studentId"})
})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @Column(name="college", nullable=false, insertable=true, updatable=true, columnDefinition = "varchar(255)")
    private String college;
    @Column(name="studentId", nullable=false, insertable=true, updatable=true, columnDefinition = "varchar(255)")
    private String studentId;
    @Column(name="name", nullable=false, insertable=true, updatable=true, columnDefinition = "varchar(255)")
    private String name;
    @Column(name="contact", nullable=false, insertable=true, updatable=true, columnDefinition = "varchar(255)")
    private String contact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
