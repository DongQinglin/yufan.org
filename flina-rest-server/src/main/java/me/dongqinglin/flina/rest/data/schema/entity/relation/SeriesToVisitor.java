package me.dongqinglin.flina.rest.data.schema.entity.relation;

import me.dongqinglin.flina.rest.data.schema.entity.Series;
import me.dongqinglin.flina.rest.data.schema.entity.Status;
import me.dongqinglin.flina.rest.data.schema.entity.Visitor;
import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The works list for series.
 */
@Entity
@Table(name = "series_to_visitor")
public class SeriesToVisitor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public Status getStatus() {
        return status;
    }

    public Series getSeries() {
        return series;
    }
}
