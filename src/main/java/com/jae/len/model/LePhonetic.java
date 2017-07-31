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

@Table(name="le_phonetic")
@Entity
public class LePhonetic extends EntityBean{
	
	private static final long serialVersionUID = -6948050890634136439L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer id;
	
	private String phonetic;
	
	private String phMp3;
	
	private String typeName;
	
	private String wordName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id")
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

	public String getPhonetic() {
		return phonetic;
	}

	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}

	public String getPhMp3() {
		return phMp3;
	}

	public void setPhMp3(String phMp3) {
		this.phMp3 = phMp3;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public LeWord getWord() {
		return word;
	}

	public void setWord(LeWord word) {
		this.word = word;
	}
	
}
