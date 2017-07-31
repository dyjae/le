package com.jae.len.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jae.framework.entity.EntityBean;

@Table(name="le_tran")
@Entity
public class LeTran extends EntityBean{

	private static final long serialVersionUID = 4903036652152752577L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer id;
	
	private String part;
	
	private String means;
	
	private String wordName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tans_id")
	private LeWord word;

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

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getMeans() {
		return means;
	}

	public void setMeans(String means) {
		this.means = means;
	}

	public LeWord getWord() {
		return word;
	}

	public void setWord(LeWord word) {
		this.word = word;
	}
	
}
