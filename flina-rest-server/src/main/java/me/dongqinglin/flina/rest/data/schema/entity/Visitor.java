package me.dongqinglin.flina.rest.data.schema.entity;

import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Visitor", uniqueConstraints = {
        @UniqueConstraint(name = "uc_visitor_email", columnNames = {"email"}),
        @UniqueConstraint(name = "uc_visitor_name", columnNames = {"name"})
})
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @Column(name="name", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String name;
    @Column(name="pass", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private int pass;
    @Column(name="email", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }
}
