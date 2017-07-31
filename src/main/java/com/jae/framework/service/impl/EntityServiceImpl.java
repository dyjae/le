package com.jae.framework.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.jae.framework.dao.Dao;
import com.jae.framework.entity.EntityPageQuery;
import com.jae.framework.entity.EntityQuery;
import com.jae.framework.service.EntityService;

/**
 * 实体对象的Service实现类
 * @author Jae
 *
 * @param <T>
 */
public abstract class EntityServiceImpl<T extends Serializable> implements
		EntityService<T> {// 泛型参数
	@SuppressWarnings("rawtypes")
	private Class clazz;
	
	@SuppressWarnings("unchecked")
	public EntityServiceImpl() {
		// 获取泛型参数
		clazz = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	@Autowired  
	protected Dao<T> dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		return dao.getAll(clazz);
	}

	@Override
	public T save(T t) {
		return (T) dao.save(t);
	}

	@Override
	public T update(T t) {
		return (T) dao.update(t);
	}

	@Override
	public T saveOrUpdate(T t) {
		return dao.saveOrUpdate(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(Serializable id) {
		return (T) dao.getById(id, clazz);
	}
	
	@Override
	public T getByIdEnable(Serializable id, Class<T> clazz){
		return (T) dao.getByIdEnable(id, clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByProperty(String propertyName,Object value){
		return (List<T> )createCriteria().add(Restrictions.eq(propertyName, value)).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getByPropertySingle(String propertyName,Object value){
		return (T) createCriteria().add(Restrictions.eq(propertyName, value)).uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getByPropertys(String[] propertyNames,Object[] values) throws Exception{
		if(propertyNames.length != values.length){
			throw new Exception("查询参数数量不匹配!");
		}
		if(propertyNames.length==0){
			return getAll();
		}
		Criteria criteria = createCriteria();
		for(int i = 0; i < propertyNames.length; i++){
			criteria.add(Restrictions.eq(propertyNames[i], values[i]));
		}
		return (List<T>)criteria.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T getByPropertysSingle(String[] propertyNames,Object[] values) throws Exception{
		if(propertyNames.length != values.length || propertyNames.length==0){
			throw new Exception("查询参数数量不匹配!");
		}
		Criteria criteria = createCriteria();
		for(int i = 0; i < propertyNames.length; i++){
			criteria.add(Restrictions.eq(propertyNames[i], values[i]));
		}
		return (T)criteria.uniqueResult();
	}
	
	@Override
	public Object uniqueObjectByHql(String hql, Object... args) {
		return dao.uniqueObjectByHql(hql, args);
	}

	@Override
	public T uniqueResultByHql(String hql, Object... args) {
		return dao.uniqueRuesultByHql(hql, args);
	}


	@Override
	public Iterable<T> batchSave(Iterable<T> list) {
		return dao.batchSave(list);
	}

	@Override
	public Iterable<T> batchSaveOrUpdate(Iterable<T> list) {
		return dao.batchSaveOrUpdate(list);
	}

	@Override
	public Iterable<T> batchDelete(Iterable<T> list) {
		return dao.batchDelete(list);
	}

	@Override
	public Iterable<T> batchUpdate(Iterable<T> list) {
		return dao.batchUpdate(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Criteria createCriteria() {
		return dao.createCriteria(clazz);
	}

	@Override
	public List<Object[]> queryBySql(String sql, Object... args) {
		return dao.queryBySql(sql, args);
	}
	
	@Override
	public List<T> queryByHql(String sql, Object... args) {
		return dao.queryByHql(sql, args);
	}

	@Override
	public EntityQuery createQueryModelByHql(String hql, Object... args) {
		return dao.createQueryModelByHql(hql, args);
	}

	@Override
	public EntityQuery createQueryModelBySql(String sql, Object... args) {
		return dao.createQueryModelBySql(sql, args);
	}

	@Override
	public EntityQuery createQueryModelByCriteria(Criteria criteria) {
		return dao.createQueryModelByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public EntityQuery createQueryModelByExample(Example example) {
		return dao.createQueryModelByExample(example, clazz);
	}

	@Override
	public EntityPageQuery<T> createPageQueryModelByHql(String hql, Object... args) {
		return dao.createPageQueryModelByHql(hql, args);
	}

	@Override
	public EntityPageQuery<T> createPageQueryModelBySql(String sql, Object... args) {
		return dao.createPageQueryModelBySql(sql, args);
	}

	@Override
	public EntityPageQuery<T> createPageQueryModelByCriteria(Criteria criteria) {
		return dao.createPageQueryModelByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public EntityPageQuery<T> createPageQueryModelByExample(Example example) {
		return dao.createPageQueryModelByExample(example, clazz);
	}

	@Override
	public Object uniqueObjectBySql(String sql, Object... args) {
		return dao.uniqueObjectBySql(sql, args);
	}

	@Override
	public void delete(T t) {
		dao.delete(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteById(Serializable id) {
		dao.deleteById(id, clazz);
	}

	@Override
	public void remove(T t) {
		dao.remove(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeById(Serializable id) {
		dao.removeById(id, clazz);
	}
	@Override
	public int executeBySql(String sql)
	{
 		return dao.executeBySql(sql);
	}
	
	public int removeBySql(String sql)
	{
		return dao.removeBySql(sql);
	}
}
