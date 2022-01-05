package me.dongqinglin.flina.rest.data.schema.entity;

import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
public class Level{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @Column(name="name", nullable=false, insertable=true, updatable=false)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Level() {
    }

    public Level(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}