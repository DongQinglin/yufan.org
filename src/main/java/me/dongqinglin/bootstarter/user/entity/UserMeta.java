package me.dongqinglin.bootstarter.user.entity;

import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;

import javax.persistence.*;

@Entity(name="user_meta")
@Table(name="user_meta")
public class UserMeta {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	// GenerationType有四种可能值分别是AUTO,INDENTITY,SEQUENCE 和 TABLE。
	private Integer id;
	@Column(name="name", nullable=true, insertable=true, updatable=true)
	private String name;

	@Column(name="student_id", nullable=true, insertable=true, updatable=true)
	private String studentId;
	@Column(name="college", nullable=true, insertable=true, updatable=true)
	private String college;
	@Column(name="concat", nullable=true, insertable=true, updatable=true)
	private String concat;
	@Column(name="enable", nullable=false, insertable=true, updatable=true, columnDefinition="bit")
	private boolean enable;


	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="author_id")
	private FlinaUser user;

	public UserMeta setId(Integer id){this.id = id;return this;}
	public Integer getId(){return this.id;}
	public UserMeta setName(String name){this.name = name;return this;}
	public String getName(){return this.name;}
	public UserMeta setStudentId(String studentId){this.studentId = studentId;return this;}
	public String getStudentId(){return this.studentId;}
	public UserMeta setCollege(String college){this.college = college;return this;}
	public String getCollege(){return this.college;}
	public UserMeta setConcat(String concat){this.concat = concat;return this;}
	public String getConcat(){return this.concat;}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	public FlinaUser getUser() {
		return user;
	}

	public void setUser(FlinaUser user) {
		this.user = user;
	}
}
