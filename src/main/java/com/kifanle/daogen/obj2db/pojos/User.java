package com.kifanle.daogen.obj2db.pojos;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;

import com.kifanle.daogen.obj2db.Column;
import com.kifanle.daogen.obj2db.Column.Datatype;
import com.kifanle.daogen.obj2db.Table;
import com.kifanle.daogen.obj2db.Table.Charset;
import com.kifanle.daogen.obj2db.Table.Engine;


@Table(engine=Engine.INNODB,charset=Charset.UTF8)
public class User {
	@Column(autoInc=true,comment="主键",primaryKey=true,nullable=false)
	private int id = 2678;
	@Column(length=255,comment="姓名")
	private String name;
	@Column(length=255,comment="昵称")
	private String nickName;
	@Column(length=255,comment="家庭住址",datatype=Datatype.Text,nullable=true)
	private String address;
	@Column(defaultVal="0",comment="测试字段iid",length=20,nullable=true)
	private Long iid;
	@Column(defaultVal="0",comment="测试字段iid2",length=20,nullable=false)
	private Long iid2;
	@Column(length=100,comment="邮件",nullable=false)
	private String email;
	@Column(length=4,comment="性别",nullable=false,defaultVal="0")
	private byte gender;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User u = new User();
		Annotation[] a = User.class.getAnnotations();
		System.out.println(((Table)a[0]).engine());
		System.out.println(((Table)a[0]).charset());
		//System.out.println(a[0].annotationType().getAnnotation(User.class));
		Field[] fs = u.getClass().getDeclaredFields();
		if(!ArrayUtils.isEmpty(fs)){
			for(Field f : fs){
				try {
					f.getDeclaredAnnotations();
					System.out.println(f.getGenericType());
					f.get(u);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
	}

}
