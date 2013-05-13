package com.kifanle.daogen.obj2db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) 
@Retention(RetentionPolicy.RUNTIME) 

public @interface Table {
	public static enum Engine{INNODB("innodb"),MYSIAM("myisam");
		private String name;
		private Engine(String name){
			this.name = name;
		}
		public String toString(){
			return this.name;
		}
	};
	public static enum Charset{UTF8("utf8"),GBK("gbk");
		private String name;
		private Charset(String name){
			this.name = name;
		}
		public String toString(){
			return this.name;
		}
	};
	/**引擎选择，现在支持innodb,和myisam*/
	public Engine engine() default Engine.INNODB;
	/**字符选择，目前为utf8 和 gbk*/
	public Charset charset() default Charset.UTF8;
}
