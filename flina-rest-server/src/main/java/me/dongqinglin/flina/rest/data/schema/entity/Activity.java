package me.dongqinglin.flina.rest.data.schema.entity;

import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity(name="activity")
@Table(name="activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @Column(name="name", nullable=false, insertable=true, updatable=true)
    private String name;

    @Column(name="start", nullable=false, insertable=true, updatable=true)
    private Timestamp start;

    @Column(name="end", nullable=false, insertable=true, updatable=true)
    private Timestamp end;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
