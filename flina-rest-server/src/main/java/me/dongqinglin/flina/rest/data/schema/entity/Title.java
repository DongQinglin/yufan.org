package me.dongqinglin.flina.rest.data.schema.entity;

import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Table
@Entity
public class Title {




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @Column(name="title", nullable=false, insertable=true, updatable=true)
    private String title;


    public Title() {
    }

    public Title(String title) {
        this.title = title;
    }

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
}
