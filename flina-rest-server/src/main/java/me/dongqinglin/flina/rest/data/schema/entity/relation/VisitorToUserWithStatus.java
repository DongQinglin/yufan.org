package me.dongqinglin.flina.rest.data.schema.entity.relation;

import me.dongqinglin.flina.rest.data.schema.entity.Status;
import me.dongqinglin.flina.rest.data.schema.entity.User;
import me.dongqinglin.flina.rest.data.schema.entity.Visitor;
import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The visitor authority details and is it disable;
 */
@Entity
@Table
public class VisitorToUserWithStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public VisitorToUserWithStatus() {
    }

    public VisitorToUserWithStatus(Visitor visitor, User user, Status status) {
        this.visitor = visitor;
        this.user = user;
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Visitor getVisitor() {
        return visitor;
    }
}
