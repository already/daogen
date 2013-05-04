package com.kifanle.test.query;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author microboss
 * @since 2013-05-04
 */
public class BaseQuery {
	public final static int DEFAULT_SIZE = 10;
	protected Integer pageSize = DEFAULT_SIZE;
	protected Integer startRow;//起始行
	protected Integer endRow;//结束行(闭合)
	protected Integer page;
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getEndRow() {
		return endRow;
	}
	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
	public void setPage(Integer page) {
		this.page = page;
		this.startRow = (page-1)*this.pageSize;
		this.endRow= this.startRow + this.pageSize - 1;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPage() {
		return page;
	}
}
