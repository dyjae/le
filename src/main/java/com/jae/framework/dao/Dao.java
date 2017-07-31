package com.jae.framework.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.jae.framework.entity.EntityPageQuery;
import com.jae.framework.entity.EntityQuery;

/**
 * 实体对象Dao接口
 * @author Jae
 *
 * @param <T>
 */
public interface Dao<T> {
	/**
	 * 获取当前Session
	 * @return
	 */
	Session getSession();
	/**
	 * 根据ID获取实体对象
	 * @param id
	 * @return
	 */
	T getById(Serializable id, Class<T> clazz);

	/**
	 * @date 2017-07-26
     * @author  yj
     * 根据id取enable为1的
	 * @param id
	 * @param clazz
	 * @return
	 */
	public T getByIdEnable(Serializable id, Class<T> clazz) ;

	/***
	 * 执行原始sql
	 * @param sql
	 * @return
	 */
	int executeBySql(String sql);
	/**
	 * 插入新实体对象
	 * @param t
	 * @return
	 */
	T save(T t);
	
	/**
	 * 插入或更新实体对象
	 * @param t
	 * @return
	 */
	T saveOrUpdate(T t);

	/**
	 * 更新实体对象
	 * @param t
	 * @return
	 */
	T update(T t);
	
	/**
	 * 批量插入
	 * @param iterable
	 * @return
	 */
	 Iterable<T> batchSave(Iterable<T> iterable);
	
	/**
	 * 批量插入或更新
	 * @param iterable
	 * @return
	 */
	 Iterable<T> batchSaveOrUpdate(Iterable<T> iterable);
	
	/**
	 * 批量删除
	 * @param iterable
	 * @return
	 */
	 Iterable<T> batchDelete(Iterable<T> iterable);
	
	/**
	 * 批量物理删除
	 * @param iterable
	 * @return
	 */
	 Iterable<T> batchRemove(Iterable<T> iterable);
	
	/**
	 * 批量更新
	 * @param iterable
	 * @return
	 */
	 Iterable<T> batchUpdate(Iterable<T> iterable);
	
	/**
	 * 查询实体对象，返回多个结果
	 * @param hql
	 * @param args
	 * @return
	 */
	 List<T> queryByHql(String hql, Object... args);
	
	/**
	 * 根据模板查询
	 * @param t 模板实体
	 * @return
	 */
	 List<T> queryByExample(T t);
	
	/**
	 * 根据模板查询
	 * @param example
	 * @return
	 */
	 List<T> queryByExample(Example example, Class<T> clazz);
	
	/**
	 * 根据条件查询
	 * @param criteria
	 * @return
	 */
	 List<T> queryByCriteria(Criteria criteria);
	
	/**
	 * SQL查询，返回多个结果
	 * @param hql
	 * @param args
	 * @return
	 */
	List<Object[]> queryBySql(String sql, Object... args);

	/**
	 * 获取所有实体对象
	 * @return
	 */
	 List<T> getAll(Class<T> clazz);
	
	/**
	 * 返回单个普通对象，一般用于聚合函数查询
	 * @param hql
	 * @param args
	 * @return
	 */
	Object uniqueObjectByHql(String hql, Object... args);
	
	/**
	 * 返回单个实体对象
	 * @param hql
	 * @param args
	 * @return
	 */
	T uniqueRuesultByHql(String hql, Object... args);
	
	/**
	 * 返回单个普通对象，一般用于聚合函数查询
	 * @param sql
	 * @param args
	 * @return
	 */
	Object uniqueObjectBySql(String sql, Object... args);
	
	/**
	 * 返回单个实体对象
	 * @param t 模板实体
	 * @return
	 */
	T uniqueRuesultByExample(T t);
	
	/**
	 * 返回单个实体对象
	 * @param criteria
	 * @return
	 */
	T uniqueRuesultByCriteria(Criteria criteria);
	
	/**
	 * 返回单个实体对象
	 * @param example
	 * @return
	 */
	T uniqueRuesultByExample(Example example, Class<T> clazz);
	
	/**
	 * 创建Criteria条件
	 * @return
	 */
	Criteria createCriteria(Class<T> clazz);
	
	/**
	 * 删除
	 * @param t
	 */
	void delete(T t);
	
	/**
	 * 物理删除
	 * @param t
	 */
	void remove(T t);
	
	/**
	 * 根据ID删除
	 * @param t
	 */
	void deleteById(Serializable id, Class<T> clazz);
	
	/**
	 * 根据ID物理删除
	 * @param t
	 */
	void removeById(Serializable id, Class<T> clazz);
	
	/**
	 * 创建查询模板
	 * @param hql
	 * @param args
	 * @return
	 */
	EntityQuery createQueryModelByHql(String hql, Object... args);

	/**
	 * 创建查询模板
	 * @param sql
	 * @param args
	 * @return
	 */
	EntityQuery createQueryModelBySql(String sql, Object... args);

	/**
	 * 创建查询模板
	 * @param criteria
	 * @return
	 */
	EntityQuery createQueryModelByCriteria(Criteria criteria);
	
	/**
	 * 创建查询模板
	 * @param example
	 * @param clazz
	 * @return
	 */
	EntityQuery createQueryModelByExample(Example example, Class<T> clazz);
	
	/**
	 * 创建分页查询模板
	 * @param hql
	 * @param args
	 * @return
	 */
	EntityPageQuery<T> createPageQueryModelByHql(String hql, Object... args);
	
	/**
	 * 创建分页查询模板
	 * @param sql
	 * @param args
	 * @return
	 */
	EntityPageQuery<T> createPageQueryModelBySql(String sql, Object... args);
	
	/**
	 * 创建分页查询模板
	 * @param criteria
	 * @return
	 */
	EntityPageQuery<T> createPageQueryModelByCriteria(Criteria criteria);
	
	/**
	 * 创建分页查询模板
	 * @param example
	 * @param clazz
	 * @return
	 */
	EntityPageQuery<T> createPageQueryModelByExample(Example example, Class<T> clazz);
	
	/**
	 * 原生sql物理删除
	 * @param example
	 * @param clazz
	 * @return
	 */
	int removeBySql(String sql);
}
