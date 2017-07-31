package com.jae.framework.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jae.framework.entity.EntityBean;
import com.jae.framework.entity.EntityPageQuery;
import com.jae.framework.entity.EntityQuery;


/**
 * 
 * @author Jae
 *
 * @param <T>
 */
@SuppressWarnings({ "rawtypes" })
@Repository(value = "dao")
public class DaoImpl<T> implements Dao<T> {
	// 批量处理数量
	private static int BATCH_SIZE = 30;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(Serializable id, Class<T> clazz) {
		return (T) getSession().get(clazz, id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public T getByIdEnable(Serializable id, Class<T> clazz) {
		T t = (T) getSession().get(clazz, id);
		if (t instanceof EntityBean) {
			if (((EntityBean) t).isEnable()){
				return  t;
			}
		}
		return null;
	}

	@Override
	public T saveOrUpdate(T t) {
		getSession().saveOrUpdate(updateBean(t));
		return t;
	}

	@Override
	public T save(T t) {
		getSession().save(updateBean(t));
		return t;
	}

	@Override
	public T update(T t) {
		getSession().update(updateBean(t));
		return t;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryByHql(String hql, Object... args) {
		Query query = getSession().createQuery(hql);
		applyParameter(query, args);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryByExample(Example example, Class<T> clazz) {
		return getSession().createCriteria(clazz).add(example).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryByCriteria(Criteria criteria) {
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryBySql(String sql, Object... args) {
		Query query = getSession().createSQLQuery(sql);
		applyParameter(query, args);

		return query.list();
	}

	@Override
	public Object uniqueObjectBySql(String hql, Object... args) {
		Query query = getSession().createSQLQuery(hql);
		applyParameter(query, args);

		return query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T uniqueRuesultByHql(String hql, Object... args) {
		Query query = getSession().createQuery(hql);
		applyParameter(query, args);

		return (T) query.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(Class<T> clazz) {
		return getSession().createCriteria(clazz).list();
	}

	@Override
	public Criteria createCriteria(Class<T> clazz) {
		return getSession().createCriteria(clazz);
	}

	@Override
	public void delete(T t) {
		if (t instanceof EntityBean) {
			((EntityBean) t).setEnable(false);
		}
		getSession().update(updateBean(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteById(Serializable id, Class<T> clazz) {
		T t = (T) getSession().get(clazz, id);
		if (t instanceof EntityBean) {
			((EntityBean) t).setEnable(false);
		}
		getSession().update(updateBean(t));
	}

	@Override
	public Iterable<T> batchSave(Iterable<T> iterable) {
		Session session = getSession();
		Iterator<T> it = iterable.iterator();
		for (int i = 0; it.hasNext(); i++) {
			session.save(updateBean(it.next()));
			if (i % BATCH_SIZE == 0) {
				session.flush();
				session.clear();
			}
		}
		return iterable;
	}

	@Override
	public Iterable<T> batchSaveOrUpdate(Iterable<T> iterable) {
		Session session = getSession();
		Iterator<T> it = iterable.iterator();
		for (int i = 0; it.hasNext(); i++) {
			session.saveOrUpdate(updateBean(it.next()));
			if (i % BATCH_SIZE == 0) {
				session.flush();
				session.clear();
			}
		}
		return iterable;
	}

	@Override
	public Iterable<T> batchDelete(Iterable<T> iterable) {
		Session session = getSession();
		Iterator<T> it = iterable.iterator();
		for (int i = 0; it.hasNext(); i++) {
			T t = updateBean(it.next());
			if (t instanceof EntityBean) {
				((EntityBean) t).setEnable(false);
			}
			session.update(t);
			if (i % BATCH_SIZE == 0) {
				session.flush();
				session.clear();
			}
		}
		return iterable;
	}

	@Override
	public Iterable<T> batchUpdate(Iterable<T> iterable) {
		Session session = getSession();
		Iterator<T> it = iterable.iterator();
		for (int i = 0; it.hasNext(); i++) {
			session.update(updateBean(it.next()));
			if (i % BATCH_SIZE == 0) {
				session.flush();
				session.clear();
			}
		}
		return iterable;
	}

	private void applyParameter(Query query, Object... args) {
		if (null != args && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
	}

	@Override
	public EntityQuery createQueryModelByHql(String hql, Object... args) {
		Query query = getSession().createQuery(hql);
		applyParameter(query, args);

		return new EntityQuery(sessionFactory, query);
	}

	@Override
	public EntityQuery createQueryModelBySql(String sql, Object... args) {
		SQLQuery query = getSession().createSQLQuery(sql);
		applyParameter(query, args);

		return new EntityQuery(sessionFactory, query);
	}

	@Override
	public EntityQuery createQueryModelByCriteria(Criteria criteria) {
		return new EntityQuery(sessionFactory, criteria);
	}

	@Override
	public EntityQuery createQueryModelByExample(Example example, Class<T> clazz) {
		return new EntityQuery(sessionFactory, example, clazz);
	}

	@Override
	public EntityPageQuery<T> createPageQueryModelByHql(String hql, Object... args) {
		Query query = getSession().createQuery(hql);
		applyParameter(query, args);

		String totalSql = hql.replaceFirst("^((select.*\\s+from)|(from))", "select count(*) from");
		Query totalQuery = getSession().createQuery(totalSql);
		applyParameter(totalQuery, args);

		return new EntityPageQuery<T>(sessionFactory, query, totalQuery);
	}

	@Override
	public EntityPageQuery<T> createPageQueryModelBySql(String sql, Object... args) {
		SQLQuery query = getSession().createSQLQuery(sql);
		applyParameter(query, args);

		String totalSql = sql.replaceFirst("^((select.*\\s+from)|(from))", "select count(*) from");
		SQLQuery totalQuery = getSession().createSQLQuery(totalSql);
		applyParameter(totalQuery, args);

		return new EntityPageQuery<T>(sessionFactory, query, totalQuery);
	}

	@Override
	public EntityPageQuery<T> createPageQueryModelByCriteria(Criteria criteria) {
		return new EntityPageQuery<T>(sessionFactory, criteria);
	}

	@Override
	public EntityPageQuery<T> createPageQueryModelByExample(Example example, Class<T> clazz) {
		return new EntityPageQuery<T>(sessionFactory, example, clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryByExample(T t) {
		return getSession().createCriteria(t.getClass()).add(Example.create(t)).list();
	}

	@Override
	public Object uniqueObjectByHql(String hql, Object... args) {
		Query query = getSession().createQuery(hql);
		applyParameter(query, args);

		return query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T uniqueRuesultByExample(T t) {
		return (T) getSession().createCriteria(t.getClass()).add(Example.create(t)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T uniqueRuesultByCriteria(Criteria criteria) {
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T uniqueRuesultByExample(Example example, Class<T> clazz) {
		return (T) getSession().createCriteria(clazz).add(example).uniqueResult();
	}

	private T updateBean(T t) {
		if (t instanceof EntityBean) {
			EntityBean bean = (EntityBean) t;
			if (null == bean.getCreateTm()) {
				bean.setCreateTm(new Date());
			}
			bean.setUpdateTm(new Date());
		}
		return t;
	}

	@Override
	public Iterable<T> batchRemove(Iterable<T> iterable) {
		Session session = getSession();
		Iterator it = iterable.iterator();
		for (int i = 0; it.hasNext(); i++) {
			session.delete(it.next());
			if (i % BATCH_SIZE == 0) {
				session.flush();
				session.clear();
			}
		}
		return iterable;
	}

	@Override
	public void remove(T t) {
		getSession().delete(t);
	}

	@Override
	public void removeById(Serializable id, Class<T> clazz) {
		getSession().delete(getSession().get(clazz, id));
	}

	@Override
	public int executeBySql(String sql) {
		return getSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public int removeBySql(String sql) {
		return getSession().createSQLQuery(sql).executeUpdate();
	}
}
