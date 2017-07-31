package com.jae.framework.entity;

import java.util.List;

/**
 * 分页
 * @author Jae
 *
 * @param <T>
 */
public class Pagination<T> {
	private int total;
	private int totalPage;
	private List<T> rows;

	public static <T> Pagination<T> createPagination(int totalCount, int totalPage, List<T> rows) {
		Pagination<T> page = new Pagination<T>();
		page.setTotal(totalCount);
		page.setRows(rows);
		page.setTotalPage(totalPage);
		return page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
