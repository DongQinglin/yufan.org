package me.dongqinglin.flina.rest.data.schema.entity;

import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * user authority info
 */
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = HibernateInsertConfig.STRATEGY_NAME)
    @GenericGenerator(name = HibernateInsertConfig.STRATEGY_NAME, strategy = HibernateInsertConfig.STRATEGY_PATH)
    private Long id;

    @Column(name="name", nullable=false, insertable=true, updatable=true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    public enum RoleEnum {
        MANAGER, EDITOR, VISITOR
    }

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public enum LevelEnum {
        one, two, three, four, five, six, seven, eight, nine, ten
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
