package me.dongqinglin.bootstarter.works.entity;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;

@Entity(name="works")
@Table(name="works")

public class Works {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "me.dongqinglin.bootstarter.global.config.HibernateInsertConfig")
	private Integer id;
	@Column(name="title", nullable=false, insertable=true, updatable=true)
	private String title;
	@Column(name="content", nullable=false, insertable=true, updatable=true, columnDefinition="text")
	private String content;
	@Column(name="style", nullable=false, insertable=true, updatable=true)
	private String style;
	@Column(name="author_stu_id", nullable=false, insertable=true, updatable=true)
	private String authorStuId;
	@Column(name="enable", nullable=false, insertable=true, updatable=true)
	private Boolean enable;
	@Column(name="up_time", nullable=false, insertable=true, updatable=true)
	private Date upTime;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="author_id")
	private FlinaUser user;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="activity_id")
	private Activity activity;

	@JsonIgnore
	@ManyToMany(mappedBy="worksSet")
	private Set<WorksGroup> worksGroups;


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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getAuthorStuId() {
		return authorStuId;
	}

	public void setAuthorStuId(String authorStuId) {
		this.authorStuId = authorStuId;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Date getUpTime() {
		return upTime;
	}

	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}

	public FlinaUser getUser() {
		return user;
	}

	public void setUser(FlinaUser user) {
		this.user = user;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Set<WorksGroup> getWorksGroups() {
		return worksGroups;
	}

	public void setWorksGroups(WorksGroup worksGroup) {
		if(worksGroups == null) worksGroups = new HashSet<>();
		this.worksGroups.add(worksGroup);
	}

	@Override
	public String toString() {
		return "Works{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", style='" + style + '\'' +
				", authorStuId='" + authorStuId + '\'' +
				", enable=" + enable +
				", upTime=" + upTime +
				", user=" + user +
				", activity=" + activity +
				", worksGroups=" + worksGroups +
				'}';
	}
}
