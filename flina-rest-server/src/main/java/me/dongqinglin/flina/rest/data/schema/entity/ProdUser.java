package me.dongqinglin.flina.rest.data.schema.entity;


import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "produser")
public class ProdUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @Column(name="username", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String name;
    @Column(name="password", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private int pass;
    @Column(name="email", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
