package me.dongqinglin.bootstarter.works.entity;

import org.hibernate.annotations.GenericGenerator;
import java.util.*;
import javax.persistence.*;

@Entity(name="works_group")
@Table(name="works_group")
public class WorksGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
    @GenericGenerator(name = "myid", strategy = "me.dongqinglin.bootstarter.global.config.HibernateInsertConfig")
    private Integer id;
    @Column(name="title", nullable=false, insertable=true, updatable=true)
    private String title;

    // @ManyToOne(fetch=FetchType.LAZY)
    // @JoinColumn(name="work_id")
    // private Works works;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="work_list", joinColumns={@JoinColumn(referencedColumnName="id")}
            , inverseJoinColumns={@JoinColumn(referencedColumnName="id")})
    private Set<Works> worksSet;



    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="activity_id")
    private Activity activity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Works> getWorksSet() {
        return worksSet;
    }

    public void setWorksSet(Set<Works> worksSet) {
        this.worksSet = worksSet;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
