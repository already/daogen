package com.kifanle.test.common;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author microboss
 * @since 2013-05-04
 */
public class Result<T> implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean isSuccess = true;
	private List<T> list;
	private int count;
	private String errorMsg;
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * 覆盖toString方法，打印对象
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this) ;
	}
}
