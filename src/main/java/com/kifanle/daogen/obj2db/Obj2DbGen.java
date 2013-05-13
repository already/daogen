package com.kifanle.daogen.obj2db;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.kifanle.daogen.ColBean;
import com.kifanle.daogen.DbConn;
import com.kifanle.daogen.Gen;
import com.kifanle.daogen.GenTable;
import com.kifanle.daogen.GlobalBean;
import com.kifanle.daogen.IndexBean;
import com.kifanle.daogen.Settings;
import com.kifanle.daogen.SqlType2Feild;
import com.kifanle.daogen.TableBean;
import com.kifanle.daogen.TableConfig;
import com.kifanle.daogen.obj2db.Column.Datatype;
import com.kifanle.daogen.util.ClassUtils;
import com.kifanle.daogen.util.FileUtil;
import com.kifanle.daogen.util.StringUtil;
import com.kifanle.daogen.util.VelocityTemplate;

public class Obj2DbGen {
	/** 日志记录 */
	private final static Logger logger = Logger.getLogger(Gen.class);
	//Source目录，从ClassPath中获取
	private final static String SOURCE_IN_PATH = ClassLoader.getSystemResource("").getPath();
	
	//生成的Maven结构的代码路径
	private final static String PATH_JAVA = "/src/main/java/";
	private final static String PATH_RESOURCES = "/src/main/resources/";
	
	private GenTable genTable;
	private GlobalBean globalBean;
	private Settings settings;
	
	public static Map<String,TableConfig> tconfig = new HashMap<String,TableConfig>();
	
	/**
	 * 对于dao的生成，暂时特殊定制，硬编码。key 为数据库表名，暂不全量配置
	 */
	static{
		tconfig.put("user", TableConfig.build("user")
				.setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
				.addQueryMethodAndCol("getUserInfoByEmail", new String[]{"email"})
				//.addQueryMethodAndCol("getUserInfoByEmailList", new String[]{"mobile"})
				);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		try{
		Obj2DbGen.doGenDB();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Obj2DbGen(DbConn dbConn){
		genTable = new GenTable(dbConn);
		globalBean = new GlobalBean();
		settings = dbConn.getSettings();
	}
	
	private static void doGenDB() throws Exception{
		logger.info("开始执行DAO代码生成===========================");
		//初始化系统环境
		Settings settings = new Settings();
		if(!settings.initSystemParam()){
			logger.error("系统初始化失败！");
			return;
		}
		
		//初始化数据库连接
		DbConn dbConn = new DbConn(settings);
		if(!dbConn.isInit()){
			logger.error("数据库连接创建失败！");
			return;
		}
		
		Obj2DbGen gen = new Obj2DbGen(dbConn);
		//设置工程的全局变量
		gen.globalBean.setNowDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));//设置系统生成时间
		gen.globalBean.setUserName(System.getenv().get("USERNAME"));//设置系统当前用户
		gen.globalBean.setPackageName(settings.getJavaPackage());//设置Java_Package路径
		
		//创建系统目录结构
		FileUtil.copyDirectiory(SOURCE_IN_PATH + settings.getTmplPath(), settings.getGenPath() + settings.getTmplPath());
		FileUtil.delExtFile(settings.getGenPath() + settings.getTmplPath(), ".vm");	//删除拷贝过去的VM文件

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		String pk = "com.kifanle.daogen.obj2db.pojos";
		String path = pk.replace(".", "/");
        URL url = classloader.getResource(path);  
        
        List<TableBean> tbList = new ArrayList<TableBean>();
		List<Class<?>> cl = ClassUtils.getClasses(new File(url.getFile()), pk);
		for(Class<?> c : cl){
			//验证类加上Table注解没有。
			Table table = c.getAnnotation(Table.class);
			if(null!=table){
				//构造表对象
				TableBean tb = TableBean.build2(ClassUtils.getLowerCaseNames(c.getSimpleName()));
				//设置engine
				tb.setEngine(table.engine().toString());
				//设置字符
				tb.setCharset(table.charset().toString());
				
				Field[] fs = c.getDeclaredFields();
				System.out.println(c.getName());
				if (!ArrayUtils.isEmpty(fs)) {
					for (Field f : fs) {
						f.setAccessible(true);
						try {
							ColBean cb = getColBeanByAnnotation(f);
							if (null != cb) {
								tb.addColBean(cb);
								if (cb.isPK()) {
									tb.setPkcol(cb);
									IndexBean ib = new IndexBean();
									ib.setPrimary(true);
									ib.setIndexColumns(cb.getColName());
									tb.addIndexBean(ib);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				tb = gen.doGenTable(tb);
				tbList.add(tb);
			}	
		}
		logger.info("实际生成" + tbList.size() + "个表的数据访问层！");
		//根据类对象列表，生成全局配置及基类代码文件
		gen.doGenCFG(tbList);
		
		//TODO 以后根据配置直接DB生产表。
		dbConn.closeConn();	//关闭数据库链接
	}
	
	/**
	 * 生成所有表的IbatisDAO配置及基类代码文件
	 * @param tbList
	 */
	private void doGenCFG(List<TableBean> tbList){
		VelocityContext ctxCfg = new VelocityContext();
		ctxCfg.put("tbbList", tbList);
		ctxCfg.put("gb", globalBean); //设置全局信息
		ctxCfg.put("sysInit", settings);	//设置系统信息
		ctxCfg.put("stringUtil", new StringUtil()); //设置StringUtil
		try {
			//生成SqlMap配置文件
			String sqlVm = settings.getTmplPath() + PATH_RESOURCES + "sql/mysql.sql.vm";
			String sqlDir = settings.getGenPath() + settings.getTmplPath() + PATH_RESOURCES + "sql/";
			VelocityTemplate.mergeTemplate(sqlVm, sqlDir + "mysql.sql", ctxCfg);
		
			//生成Java基类代码
			String javaVmDir = SOURCE_IN_PATH + settings.getTmplPath() + PATH_JAVA;
			String javaDir = settings.getGenPath() + settings.getTmplPath() + PATH_JAVA;
			List<String> javaVmList = FileUtil.getFileListWithExt(javaVmDir,".vm");
			String createFilename,packageDir = "",packageStr;
			for(String vmFilename :javaVmList){
				if(!vmFilename.startsWith("Base")) continue;	 //非基类代码跳过
				createFilename = FileUtil.getFilenameWithoutExt(vmFilename);
				packageStr = FileUtil.findLine(javaVmDir + "/" + vmFilename, "package");
				if(StringUtils.isNotBlank(packageStr)){
					packageStr = packageStr.substring(packageStr.indexOf("$!{gb.packageName}"),packageStr.indexOf(";"));
					packageDir = packageStr.replace("$!{gb.packageName}", globalBean.getPackageName()).replace(".", "/");
				}
				FileUtil.mkDirs(javaDir + packageDir);
				VelocityTemplate.mergeTemplate(settings.getTmplPath() + PATH_JAVA + "/" + vmFilename, javaDir + packageDir + "/" + createFilename, ctxCfg);
			}
		} catch (Exception e) {
			logger.error("生成所有表的IbatisDAO配置及基类代码文件，异常是:",e);
		}
	}
	
	/**
	 * 生成指定表的数据访问层
	 * @param tablename
	 * @return 表类名
	 */
	private TableBean doGenTable(TableBean tableBean) {
		VelocityContext ctx = new VelocityContext();
		ctx.put("tbb", tableBean);	//设置表对象
		ctx.put("gb", globalBean); //设置全局信息
		ctx.put("sysInit", settings);	//设置系统信息
		ctx.put("stringUtil", new StringUtil()); //设置StringUtil
		try {
			//生成Java代码
			String javaVmDir = SOURCE_IN_PATH + settings.getTmplPath() + PATH_JAVA;
			String javaDir = settings.getGenPath() + settings.getTmplPath() + PATH_JAVA;
			List<String> javaVmList = FileUtil.getFileListWithExt(javaVmDir,".vm");
			String createFilename,packageDir = "",packageStr;
			for(String vmFilename:javaVmList){
				if(vmFilename.startsWith("Base")) continue;	 //基类代码跳过
				if(vmFilename.startsWith("Result")) { this.doSpecialVM(ctx, vmFilename, javaVmDir, javaDir); continue;}
				createFilename = FileUtil.getFilenameWithoutExt(vmFilename);
				if(createFilename.startsWith("DO.")){
					createFilename = createFilename.replace("DO", "");
			    }
				packageStr = FileUtil.findLine(javaVmDir + "/" + vmFilename, "package");
				if(StringUtils.isNotBlank(packageStr)){
					packageStr = packageStr.substring(packageStr.indexOf("$!{gb.packageName}"),packageStr.indexOf(";"));
					packageDir = packageStr.replace("$!{gb.packageName}", globalBean.getPackageName()).replace(".", "/");
				}
				FileUtil.mkDirs(javaDir + packageDir);
				VelocityTemplate.mergeTemplate(settings.getTmplPath() + PATH_JAVA + "/" + vmFilename, javaDir + packageDir + "/" + tableBean.getClassName() + createFilename, ctx);
				logger.info(tableBean.getClassName() + createFilename);
			}
			//生成SqlMap配置文件
			String sqlmapVm = settings.getTmplPath() + PATH_RESOURCES + "sqlmap/-sqlmap.xml.vm";
			String sqlmapDir = settings.getGenPath() + settings.getTmplPath() + PATH_RESOURCES + "sqlmap/";
			VelocityTemplate.mergeTemplate(sqlmapVm, sqlmapDir + tableBean.getTableName() + "-sqlmap.xml", ctx);
		} catch (Exception e) {
			logger.error("表[" + tableBean.getTableName() +"]生成出错，异常是:",e);
		}
		return tableBean;
	}
	
	private void doSpecialVM(VelocityContext ctx,String vmFilename,String javaVmDir,String javaDir){
		String packageDir = "";
		String createFilename = FileUtil.getFilenameWithoutExt(vmFilename);
		String packageStr = FileUtil.findLine(javaVmDir + "/" + vmFilename, "package");
		if (StringUtils.isNotBlank(packageStr)) {
			packageStr = packageStr.substring(
					packageStr.indexOf("$!{gb.packageName}"),
					packageStr.indexOf(";"));
			packageDir = packageStr.replace("$!{gb.packageName}",
					globalBean.getPackageName()).replace(".", "/");
		}
		FileUtil.mkDirs(javaDir + packageDir);
		VelocityTemplate.mergeTemplate(settings.getTmplPath() + PATH_JAVA + "/" + vmFilename, javaDir + packageDir + "/" + createFilename, ctx);
	}
	
	public static ColBean getColBeanByAnnotation(Field field){
		ColBean col = new ColBean();
		Column c = field.getAnnotation(Column.class);
		if(null!=c){
			if(c.primaryKey()) {
				col.setAutoIncrement(c.autoInc());
				col.setPK(c.primaryKey());
			}
			col.setColComment(c.comment());
			col.setDefaultValue(c.defaultVal().isEmpty()?null:c.defaultVal());
			col.setPrecision(c.length());
			col.setNullable(c.nullable());
			col.setScale(c.scale());
			col.setColName(ClassUtils.getLowerCaseNames(field.getName()));

			col.setPropertyName(ColBean.getPropName(col.getColName()));
			col.setMethodName(ColBean.getMethodName(col.getPropertyName()));
			String sqlType = null;
			if(c.datatype()==Datatype.None){
				sqlType = field.getGenericType().toString().replace("class ", "");
				col.setColType(getColType(sqlType));
			}else{
				sqlType = c.datatype().toString();
				col.setColType(sqlType);
			}
			col.setColSQLType(SqlType2Feild.javaType2sqlType(sqlType));
			col.setPropertyType(SqlType2Feild.mapJavaType(col.getColSQLType()));
			
			return col;
		}else{
			return null;
		}		
	}
	
	public static String getColType(String sqlType){
		return SqlType2Feild.getColType(sqlType);
	}
}
