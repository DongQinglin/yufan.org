package me.dongqinglin.auto.entity;
import javax.persistence.*;
import java.math.*;
import java.sql.*;
@Entity(name="tb_adm_info")
@Table(name="tb_adm_info")
public class TbAdmInfo {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	// GenerationType有四种可能值分别是AUTO,INDENTITY,SEQUENCE 和 TABLE。
	private Integer id;
	@Column(name="s_adm_name", nullable=true, insertable=true, updatable=true, columnDefinition="varchar")
	private String sAdmName;
	@Column(name="s_sup_org", nullable=true, insertable=true, updatable=true, columnDefinition="varchar")
	private String sSupOrg;
	@Column(name="s_area_code", nullable=true, insertable=true, updatable=true, columnDefinition="varchar")
	private String sAreaCode;
	@Column(name="s_addr", nullable=true, insertable=true, updatable=true, columnDefinition="varchar")
	private String sAddr;
	@Column(name="org_id", nullable=true, insertable=true, updatable=true, columnDefinition="bigint")
	private Long orgId;
	@Column(name="s_post_code", nullable=true, insertable=true, updatable=true, columnDefinition="char")
	private String sPostCode;
	@Column(name="s_tel", nullable=true, insertable=true, updatable=true, columnDefinition="varchar")
	private String sTel;
	@Column(name="s_remarks", nullable=true, insertable=true, updatable=true, columnDefinition="varchar")
	private String sRemarks;
	@Column(name="s_status", nullable=true, insertable=true, updatable=true, columnDefinition="char")
	private String sStatus;
	@Column(name="use_date", nullable=true, insertable=true, updatable=true, columnDefinition="`use_date`")
	private Date useDate;
	@Column(name="s_user", nullable=true, insertable=true, updatable=true, columnDefinition="varchar")
	private String sUser;
	@Column(name="s_user_org", nullable=true, insertable=true, updatable=true, columnDefinition="varchar")
	private String sUserOrg;
	@Column(name="ts_sysupdate", nullable=false, insertable=true, updatable=true, columnDefinition="`ts_sysupdate`")
	private Date tsSysupdate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getsAdmName() {
		return sAdmName;
	}

	public void setsAdmName(String sAdmName) {
		this.sAdmName = sAdmName;
	}

	public String getsSupOrg() {
		return sSupOrg;
	}

	public void setsSupOrg(String sSupOrg) {
		this.sSupOrg = sSupOrg;
	}

	public String getsAreaCode() {
		return sAreaCode;
	}

	public void setsAreaCode(String sAreaCode) {
		this.sAreaCode = sAreaCode;
	}

	public String getsAddr() {
		return sAddr;
	}

	public void setsAddr(String sAddr) {
		this.sAddr = sAddr;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getsPostCode() {
		return sPostCode;
	}

	public void setsPostCode(String sPostCode) {
		this.sPostCode = sPostCode;
	}

	public String getsTel() {
		return sTel;
	}

	public void setsTel(String sTel) {
		this.sTel = sTel;
	}

	public String getsRemarks() {
		return sRemarks;
	}

	public void setsRemarks(String sRemarks) {
		this.sRemarks = sRemarks;
	}

	public String getsStatus() {
		return sStatus;
	}

	public void setsStatus(String sStatus) {
		this.sStatus = sStatus;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getsUser() {
		return sUser;
	}

	public void setsUser(String sUser) {
		this.sUser = sUser;
	}

	public String getsUserOrg() {
		return sUserOrg;
	}

	public void setsUserOrg(String sUserOrg) {
		this.sUserOrg = sUserOrg;
	}

	public Date getTsSysupdate() {
		return tsSysupdate;
	}

	public void setTsSysupdate(Date tsSysupdate) {
		this.tsSysupdate = tsSysupdate;
	}

	@Override
	public String toString() {
		return "TbAdmInfo{" +
				"id=" + id +
				", sAdmName='" + sAdmName + '\'' +
				", sSupOrg='" + sSupOrg + '\'' +
				", sAreaCode='" + sAreaCode + '\'' +
				", sAddr='" + sAddr + '\'' +
				", orgId=" + orgId +
				", sPostCode='" + sPostCode + '\'' +
				", sTel='" + sTel + '\'' +
				", sRemarks='" + sRemarks + '\'' +
				", sStatus='" + sStatus + '\'' +
				", useDate=" + useDate +
				", sUser='" + sUser + '\'' +
				", sUserOrg='" + sUserOrg + '\'' +
				", tsSysupdate=" + tsSysupdate +
				'}';
	}
}