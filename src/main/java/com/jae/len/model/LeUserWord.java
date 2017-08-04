package com.jae.len.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jae.framework.entity.EntityBean;

@Table(name="le_user_word")
@Entity
public class LeUserWord extends EntityBean{

	private static final long serialVersionUID = 2059977345904040372L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name = "ID", columnDefinition="int COMMENT '主键'",nullable = false)
	private Integer id;
	
	@Column(name = "WORD_NAME", columnDefinition="varchar(100) COMMENT '词名'")
	private String wordName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id")
	private LeWord word;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private LeUser user;
	
	@Column(name = "STATE", columnDefinition="int default 0 COMMENT '状态 0：背诵中 1：已完成 2：重背中'")
	private Integer state;
	
	@Column(name = "TOTAL_TIMES",columnDefinition="int default 0 COMMENT '总背诵次数'")
	private Integer Totaltimes;
	
	@Column(name = "TOLTAL_ERROR_TIMES",columnDefinition="int default 0 COMMENT '总错误次数'")
	private Integer TolErrorTimes;
	
	@Column(name = "TOTAL_RIGHT_TIMES",columnDefinition="int default 0 COMMENT '总正确次数'")
	private Integer TolRightTimes;
	
	@Column(name = "ERROR_TIMES",columnDefinition="int default 0 COMMENT '错误次数'")
	private Integer errorTimes;
	
	@Column(name = "RIGHT_TIMES",columnDefinition="int default 0 COMMENT '正确次数'")
	private Integer rightTimes;
	
	private Date createTm;
	
	private Date updateTm;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	public LeWord getWord() {
		return word;
	}

	public void setWord(LeWord word) {
		this.word = word;
	}

	public LeUser getUser() {
		return user;
	}

	public void setUser(LeUser user) {
		this.user = user;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getTotaltimes() {
		return Totaltimes;
	}

	public void setTotaltimes(Integer totaltimes) {
		Totaltimes = totaltimes;
	}

	public Integer getTolErrorTimes() {
		return TolErrorTimes;
	}

	public void setTolErrorTimes(Integer tolErrorTimes) {
		TolErrorTimes = tolErrorTimes;
	}

	public Integer getTolRightTimes() {
		return TolRightTimes;
	}

	public void setTolRightTimes(Integer tolrightTimes) {
		TolRightTimes = tolrightTimes;
	}

	public Integer getErrorTimes() {
		return errorTimes;
	}

	public void setErrorTimes(Integer errorTimes) {
		this.errorTimes = errorTimes;
	}

	public Integer getRightTimes() {
		return rightTimes;
	}

	public void setRightTimes(Integer rightTimes) {
		this.rightTimes = rightTimes;
	}

	public Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	public Date getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Date updateTm) {
		this.updateTm = updateTm;
	}
	
	
	
}
