package me.dongqinglin.flina.rest.data.schema.entity.relation;

import me.dongqinglin.flina.rest.data.schema.entity.Series;
import me.dongqinglin.flina.rest.data.schema.entity.Status;
import me.dongqinglin.flina.rest.data.schema.entity.WorksToStyleWithStatus;
import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The works list for series.
 */
@Entity
@Table(name = "series_to_works")
public class SeriesToWorks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @ManyToOne
    @JoinColumn(name = "works_id")
    private WorksToStyleWithStatus works;


    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public WorksToStyleWithStatus getWorks() {
        return works;
    }

    public void setWorks(WorksToStyleWithStatus works) {
        this.works = works;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Series getSeries() {
        return series;
    }
}
