package com.jae.len.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jae.framework.entity.EntityBean;

@Entity
@Table(name="le_word")
public class LeWord extends EntityBean{

	private static final long serialVersionUID = 6613674790726599103L;
	
	public LeWord() {
	}

	public LeWord(String word) {
		this.word = word;
	}

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer id;
	
	private String word;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="word",cascade=CascadeType.ALL)
	private List<LeTran> tans;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="word",cascade=CascadeType.ALL)
	private List<LePhonetic> phonetics;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public List<LeTran> getTans() {
		return tans;
	}

	public void setTans(List<LeTran> tans) {
		this.tans = tans;
	}

	public List<LePhonetic> getPhonetics() {
		return phonetics;
	}

	public void setPhonetics(List<LePhonetic> phonetics) {
		this.phonetics = phonetics;
	}
	
}
