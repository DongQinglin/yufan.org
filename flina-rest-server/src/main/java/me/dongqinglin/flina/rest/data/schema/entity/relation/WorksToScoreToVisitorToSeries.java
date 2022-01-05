package me.dongqinglin.flina.rest.data.schema.entity.relation;

import me.dongqinglin.flina.rest.data.schema.entity.*;
import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class WorksToScoreToVisitorToSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "works_id")
    private WorksToStyleWithStatus works;

    @ManyToOne
    @JoinColumn(name = "score_id")
    private Score score;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    public Series getSeries() {
        return series;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public Score getScore() {
        return score;
    }

    public WorksToStyleWithStatus getWorks() {
        return works;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public void setWorks(WorksToStyleWithStatus works) {
        this.works = works;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setScore(Score score) {
        this.score = score;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
}
