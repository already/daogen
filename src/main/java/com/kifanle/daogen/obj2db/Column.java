package com.kifanle.daogen.obj2db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)   
@Retention(RetentionPolicy.RUNTIME)   
public @interface Column {
	public static enum Datatype{None("default"),Varchar("varchar"),Text("text");
	private String name;
	private Datatype(String name){
		this.name = name;
	}
	public String toString(){
		return this.name;
	}
};
    /**字段长度 */
	public int length() default 8;
	/** 字段小数位（如果为float,double时有） */
	public int scale() default 2;
	/**默认值 */
	public String defaultVal() default "";
	/**是否自增 */
	public boolean autoInc() default false;
	/**是否为主键*/
	public boolean primaryKey() default false;
	/**注释*/
	public String comment() default "";
	/**可否为空*/
	public boolean nullable() default true;
	/**特殊字段设置，比如pojo field 为String时，可能是varchar 或者 text*/
	public Datatype datatype() default Datatype.None;
}
