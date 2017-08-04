package com.jae.len.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jae.framework.entity.EntityBean;

@Table(name="LE_USER")
@Entity
public class LeUser extends EntityBean{
	
	private static final long serialVersionUID = 951900658953449810L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name = "ID",nullable = false, columnDefinition="int COMMENT '主键'")
	private Integer id;
	
	@Column(name = "USER_NAME", columnDefinition="varchar(100) COMMENT '用户名'")
	private String userName;
	
	@Column(name = "PASSWORD", columnDefinition="varchar(100) COMMENT '密码'")
	private String password;
	
	@Column(name = "MOBILE", columnDefinition="varchar(100) COMMENT '手机号'")
	private String mobile;
	
	@Column(name = "REAL_NAME", columnDefinition="varchar(100) COMMENT '真实姓名'")
	private String realName;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="user",cascade=CascadeType.ALL)
	private List<LeUserWord> words;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public List<LeUserWord> getWords() {
		return words;
	}

	public void setWords(List<LeUserWord> words) {
		this.words = words;
	}
	
}
