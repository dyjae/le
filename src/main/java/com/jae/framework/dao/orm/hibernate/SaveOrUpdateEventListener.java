package com.jae.framework.dao.orm.hibernate;

import java.util.Date;

import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

import com.jae.framework.entity.EntityBean;

/**
 * 保存和修改的监听器
 * 
 * @author jae
 * 
 */
//@Component(value="listener")
public class SaveOrUpdateEventListener implements PreInsertEventListener, PreUpdateEventListener {
	private static final long serialVersionUID = -2297408806609187435L;

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		if (event.getEntity() instanceof EntityBean) {
			EntityBean bean = (EntityBean) event.getEntity();
			bean.setCreateTm(new Date());
			bean.setUpdateTm(new Date());
		}
		return true;
	}

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		if (event.getEntity() instanceof EntityBean) {
			EntityBean bean = (EntityBean) event.getEntity();
			bean.setUpdateTm(new Date());
		}
		return true;
	}
}
