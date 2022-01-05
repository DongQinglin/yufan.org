package me.dongqinglin.flina.rest.data.schema.entity.relation;

import me.dongqinglin.flina.rest.data.schema.entity.Activity;
import me.dongqinglin.flina.rest.data.schema.entity.Series;
import me.dongqinglin.flina.rest.data.schema.entity.Status;
import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The works list for series.
 */
@Entity
@Table(name = "series_to_activity")
public class SeriesToActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Series getSeries() {
        return series;
    }
}
