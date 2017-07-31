package com.jae.framework.entity;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 分页查询模板
 * @author sunjiahao
 *
 */
public class EntityPageQuery<T>{
	private final Logger logger = LoggerFactory.getLogger(EntityPageQuery.class);
	/** 默认分页页码 */
	public static final int DEFAULT_PAGE = 1;
	/** 默认分页行数 */
	public static final int DEFAULT_ROWS = 10;
	
	private SessionFactory sessionFactory;
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	private Query query;
	private Query totalQuery;
	private SQLQuery sqlQuery;
	private SQLQuery totalSqlQuery;
	private Criteria criteria;
	private Example example;
	private Class<?> clazz;
	
	int page = DEFAULT_PAGE;
	int rows = DEFAULT_ROWS;
	
	public EntityPageQuery(SessionFactory sessionFactory, Query query, Query totalQuery){
		this.sessionFactory = sessionFactory;
		this.query = query;
		this.totalQuery = totalQuery;
	}
	public EntityPageQuery(SessionFactory sessionFactory, SQLQuery sqlQuery, SQLQuery totalSqlQuery){
		this.sessionFactory = sessionFactory;
		this.sqlQuery = sqlQuery;
		this.totalSqlQuery = totalSqlQuery;
	}
	public EntityPageQuery(SessionFactory sessionFactory, Criteria criteria){
		this.sessionFactory = sessionFactory;
		this.criteria = criteria;
	}
	public EntityPageQuery(SessionFactory sessionFactory, Example example, Class<?> clazz){
		this.sessionFactory = sessionFactory;
		this.example = example;
		this.clazz = clazz;
	}
	
	public int getPage() {
		return page;
	}
	
	public int getRows() {
		return rows;
	}

	public EntityPageQuery<T> setPage(int page) {
		try {
			if(page>0){
				this.page = page;
			}else{
				throw new NumberFormatException("page must be greater than 0");
			}
		} catch (NumberFormatException e) {
			 
			logger.warn(e.getMessage());
		}
		
		return this;
	}

	public EntityPageQuery<T> setRows(int rows) {
		try {
			if(rows>0){
				this.rows = rows;
			}else{
				throw new NumberFormatException("rows must be greater than 0");
			}
		} catch (NumberFormatException e) {
			logger.warn(e.getMessage());
		}
		
		return this;
	}

	public EntityPageQuery<T> setPage(String page) {
		try {
			int i = Integer.parseInt(page);
			if(i>0){
				this.page= i ;
			}else{
				throw new NumberFormatException("page must be greater than 0");
			}
		} catch (NumberFormatException e) {
	 
			logger.warn(e.getMessage());
		}
		
		return this;
	}

	public EntityPageQuery<T> setRows(String rows) {
		try {
			int i = Integer.parseInt(rows);
			if(i>0){
				this.rows= i ;
			}else{
				throw new NumberFormatException("rows must be greater than 0");
			}
		} catch (NumberFormatException e) {
			 
			logger.warn(e.getMessage());
		}
		return this;
	}
	public Pagination<T> query() {
		if (null != query) {
			return pageQueryByHql();
		} else if (null != sqlQuery) {
			return pageQueryBySql();
		} else if (null != criteria) {
			return pageQueryByCriteria();
		} else if (null != example) {
			return pageQueryByExample();
		} else {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	private Pagination<T> pageQueryByHql() {
		// 设置分页
		query.setFirstResult((page - 1) * rows);
		query.setMaxResults(rows);
		
		int totalCount = ((Long) totalQuery.uniqueResult()).intValue();
		int totalPage = 0==totalCount ? 0 :totalCount%rows==0 ? totalCount/rows : totalCount/rows + 1;
		// 分页结果
		return Pagination.createPagination(totalCount, totalPage, query.list());
	}

	@SuppressWarnings("unchecked")
	private Pagination<T> pageQueryBySql() {
		// 设置分页
		sqlQuery.setFirstResult((page - 1) * rows);
		sqlQuery.setMaxResults(rows);
		// 统计分页
		int totalCount = ((BigDecimal) totalSqlQuery.uniqueResult()).intValue();
		int totalPage = 0==totalCount ? 0 :totalCount%rows==0 ? totalCount/rows : totalCount/rows + 1;
		// 分页结果
		return Pagination.createPagination(totalCount, totalPage, query.list());
	}

	@SuppressWarnings("unchecked")
	private Pagination<T> pageQueryByExample() {
		// 分页结果集
		List<T> resultList = getSession().createCriteria(clazz).add(example).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		// 统计记录总数
		Criteria criteria = getSession().createCriteria(clazz).add(example);
		criteria.setProjection(Projections.rowCount());
		
		Long totalCount = (Long)criteria.uniqueResult();
		Long totalPage = 0==totalCount ? 0 :totalCount%rows==0 ? totalCount/rows : totalCount/rows + 1;
		// 分页结果
		return Pagination.createPagination(totalCount.intValue(), totalPage.intValue(), resultList);
	}

	@SuppressWarnings("unchecked")
	private Pagination<T> pageQueryByCriteria() {
		// 分页结果集
		List<T> resultList = criteria.setFirstResult((page - 1) * rows).setMaxResults(rows).list();

		// 统计记录总数
		criteria.setProjection(Projections.rowCount());
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		
		Long totalCount = (Long)criteria.uniqueResult();
		Long totalPage = 0==totalCount ? 0 :totalCount%rows==0 ? totalCount/rows : totalCount/rows + 1;
		
		// 分页结果
		return Pagination.createPagination(totalCount.intValue(), totalPage.intValue(), resultList);
	}
}
