package me.dongqinglin.flina.rest.data.schema.entity.relation;

import me.dongqinglin.flina.rest.data.schema.entity.Status;
import me.dongqinglin.flina.rest.data.schema.entity.Visitor;
import me.dongqinglin.flina.rest.data.schema.entity.WorksToStyleWithStatus;
import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * who has the special works
 */
@Entity
public class WorksToVisitorWithStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "works_id")
    private WorksToStyleWithStatus works;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public Status getStatus() {
        return status;
    }

    public Visitor getVisitor() {
        return visitor;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public WorksToStyleWithStatus getWorks() {
        return works;
    }

    public void setWorks(WorksToStyleWithStatus works) {
        this.works = works;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
