package me.dongqinglin.zuulservice.entity;

import javax.persistence.*;

@Entity(name="user")
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name="username", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String username;
    @Column(name="password", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String password;
    // 角色授权 插入时以ROLE_前缀开始，使用时controller不需要前缀
    // 权限授权，不需要前缀一致便好
    @Column(name="roles", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String roles;
    @Column(name="enable", nullable=false, insertable=true, updatable=true, columnDefinition="bit")
    private boolean enable;
    public void setId(int id){this.id = id;}
    public int getId(){return this.id;}
    public void setUsername(String username){this.username = username;}
    public String getUsername(){return this.username;}
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}
    public void setRoles(String roles){this.roles = roles;}
    public String getRoles(){return this.roles;}
    public void setEnable(boolean enable){this.enable = enable;}
    public boolean getEnable(){return this.enable;}
}