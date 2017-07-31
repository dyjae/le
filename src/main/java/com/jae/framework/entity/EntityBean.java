package com.jae.framework.entity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@MappedSuperclass
@JacksonXmlRootElement(localName="", namespace="")
public class EntityBean implements Serializable {
	
	private static final long serialVersionUID = -3076811241619904594L;

	public static final String DEFAULT_USER = "Sys";

	public EntityBean() {
	}

	public EntityBean(boolean enable, String remark) {
		this.enable = enable;
		this.remark = remark;
	}

	@JsonIgnore
	@Transient
	private Integer id;
	
	@Column(name = "REMARK", nullable = true, columnDefinition="varchar(20)  COMMENT '备注'")
	private String remark;

	
	@Column(name = "IS_ENABLE", columnDefinition="tinyint(1) default 1 COMMENT '启用(软删)标识'",  nullable = false)
	private boolean enable = true;

	@JsonIgnore
	@Transient
	private String updateBy;

	@JsonIgnore
	@Transient
	private Date updateTm;

	@JsonIgnore
	@Transient
	private String createBy;

	@JsonIgnore
	@Transient
	private Date createTm;

	 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Date updateTm) {
		this.updateTm = updateTm;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	/**
	 * @author sunjiahao
	 *
	 * 默认序列化类,如果需要格式化通用属性。需要在实体类重写此方法
	 */
	public static class EntityBeanJsonSerializer extends JsonSerializer<EntityBean> {
		@Override
		public void serialize(EntityBean entity, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
			if (null != entity) {
				EntityBean e = new EntityBean();
				e.setId(entity.getId());
				e.setRemark(entity.getRemark());
				e.setEnable(entity.isEnable());
				gen.writeObject(e);
			} else {
				gen.writeObject(null);
			}
		}
	}

	public static class ListEntityBeanJsonSerializer extends JsonSerializer<List<EntityBean>> {
		@Override
		public void serialize(List<EntityBean> list, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
			if (null != list && !list.isEmpty()) {
				List<EntityBean> ls = new ArrayList<EntityBean>(list.size());
				for (EntityBean entity : list) {
					EntityBean e = new EntityBean();
					e.setId(entity.getId());
					e.setEnable(entity.isEnable());
					e.setRemark(entity.getRemark());
					ls.add(e);
				}
				gen.writeObject(ls);
			} else {
				gen.writeObject(null);
			}
		}
	}
}