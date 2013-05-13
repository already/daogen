package com.kifanle.daogen;

/**
 * 索引对象
 * @author yingyang
 * @since 2011-11-18
 */
public class IndexBean {

	/** ================索引结构信息====================== */
	/** 索引名称 */
	private String indexName;
	/** 排序的索引字段 */
	private String indexColumns;
	/** 是否唯一索引 */
	private boolean isUnique;
	/** 是否主键索引 */
	private boolean isPrimary;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexColumns() {
		return indexColumns;
	}

	public String getIndexColumnsForMysql() {
		return indexColumns.replace(",", "`,`");
	}

	public void setIndexColumns(String indexColumns) {
		this.indexColumns = indexColumns;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

}
