package com.kifanle.daogen.obj2db.pojos;

import java.util.Date;

import com.kifanle.daogen.obj2db.Column;
import com.kifanle.daogen.obj2db.Table;
import com.kifanle.daogen.obj2db.Table.Charset;
import com.kifanle.daogen.obj2db.Table.Engine;

@Table(engine=Engine.INNODB,charset=Charset.UTF8)
public class FoodInfo {
	@Column(autoInc=true,comment="主键",primaryKey=true,nullable=false)
	private long id;
	@Column(comment="食物信息",length=255,nullable=true)
	private long foodName;
	@Column(comment="创建日期",nullable=false)
	private Date creatTime;
}
