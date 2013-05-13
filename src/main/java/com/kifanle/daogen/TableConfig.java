package com.kifanle.daogen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 此类用来定制DAO方法产生
 * @author microboss
 *
 */
public class TableConfig {
	
	private String name;
	
	//get
	private boolean getByKey = true;
	private boolean getByKeys = true;
	private boolean needPage = false;
	
	//delete
	private boolean delByKey = false;
	private boolean delByKeys = false;
	
	//update
	private boolean updateObj = true;
	
	/**
	 * key 为方法名结尾为"_s",则生成list，String[]为过滤字段，关于排序和个数，limit请暂时另行添加
	 */
	private Map<String,String[]> selectMap = new HashMap<String,String[]>();
	
	/**
	 * key 为方法名结尾为，String[]为过滤字段，关于排序和个数，limit请暂时另行添加,建立一个三元map。
	 */
	//private Map<String,String[]> updateMap = new HashMap<String,String[]>();
	
	private Set<String[]> updateCols = new HashSet<String[]>();
	
	//queryPage
	private List<String> orderCol = new ArrayList<String>();
	
	private TableConfig(){}
	
	public static TableConfig build(String name){
		return new TableConfig().setName(name);
	}

	public String getName() {
		return name;
	}

	public TableConfig setName(String name) {
		this.name = name;
		return this;
	}
	
	public TableConfig addQueryMethodAndCol(String method,String[] wheres){
		this.selectMap.put(method, wheres);
		return this;
	}
	
	/**
	 * 增加排序规则，适用于分页
	 * @param colName
	 * @param o
	 * @return
	 */
	public TableConfig addQueryOrderBy(String colName){
		this.orderCol.add(colName);
		return this;
	}

	public boolean isGetByKey() {
		return getByKey;
	}

	public TableConfig setGetByKey(boolean getByKey) {
		this.getByKey = getByKey;
		return this;
	}

	public boolean isGetByKeys() {
		return getByKeys;
	}

	public TableConfig setGetByKeys(boolean getByKeys) {
		this.getByKeys = getByKeys;
		return this;
	}

	public boolean isNeedPage() {
		return needPage;
	}

	public TableConfig setNeedPage(boolean needPage) {
		this.needPage = needPage;
		return this;
	}

	public boolean isDelByKey() {
		return delByKey;
	}

	public TableConfig setDelByKey(boolean delByKey) {
		this.delByKey = delByKey;
		return this;
	}

	public boolean isDelByKeys() {
		return delByKeys;
	}

	public TableConfig setDelByKeys(boolean delByKeys) {
		this.delByKeys = delByKeys;
		return this;
	}

	public boolean isUpdateObj() {
		return updateObj;
	}

	public TableConfig setUpdateObj(boolean updateObj) {
		this.updateObj = updateObj;
		return this;
	}

	public Set<String[]> getUpdateCols() {
		return updateCols;
	}

	public void setUpdateCols(Set<String[]> updateCols) {
		this.updateCols = updateCols;
	}
	
	public List<String> getOrderCol() {
		return orderCol;
	}

	public Map<String, String[]> getSelectMap() {
		return selectMap;
	}
	
}
