package me.dongqinglin.flina.rest.data.schema.entity;

import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
public class Log  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    public enum LogEnum {
        CREATE, MODIFY
    }

    private LogEnum logEnum;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    private Timestamp time;

    private String origin;

    private String result;

    public Visitor getVisitor() {
        return visitor;
    }
}
