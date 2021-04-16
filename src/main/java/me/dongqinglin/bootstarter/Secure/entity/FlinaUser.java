package me.dongqinglin.bootstarter.Secure.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name="user")
@Table(name="user")
public class FlinaUser {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
    @GenericGenerator(name = "myid", strategy = "me.dongqinglin.bootstarter.global.config.HibernateInsertConfig")
    private int id;
    @Column(name="username", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String username;
    @Column(name="password", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private int password;
    @Column(name="email", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String email;
    // 角色授权 插入时以ROLE_前缀开始，使用时controller不需要前缀
    // 权限授权，不需要前缀一致便好
    @Column(name="roles", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String roles;
    @Column(name="level", nullable=false, insertable=true, updatable=true)
    private int level;
    @Column(name="enable", nullable=false, insertable=true, updatable=true, columnDefinition="bit")
    private boolean enable;



    public int getId() {
        return id;
    }

    public FlinaUser setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public FlinaUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getPassword() {
        return password;
    }

    public FlinaUser setPassword(int password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public FlinaUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRoles() {
        return roles;
    }

    public FlinaUser setRoles(String roles) {
        this.roles = roles;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public FlinaUser setLevel(int level) {
        this.level = level;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public FlinaUser setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    @Override
    public String toString() {
        return "FlinaUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password=" + password +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", level=" + level +
                ", enable=" + enable +
                '}';
    }
}