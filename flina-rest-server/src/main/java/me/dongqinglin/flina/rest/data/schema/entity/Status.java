package me.dongqinglin.flina.rest.data.schema.entity;

import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
public class Status{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    public Long id;

    @Column(name="name", nullable=false, insertable=true, updatable=true, columnDefinition = "varchar(255)")
    private String name;

    public enum StatusEnum {
        DISABLE, ENABLE
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
