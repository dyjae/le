package com.jae.program.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.jae.framework.entity.EntityBean;

@Entity
@Table(name="t_user")
public class User extends EntityBean{

	private static final long serialVersionUID = 2556102438303839104L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO) 
	private Integer id;
	
	private String name;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
