package com.kifanle.daogen;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.kifanle.daogen.util.FileUtil;
import com.kifanle.daogen.util.StringUtil;
import com.kifanle.daogen.util.VelocityTemplate;

/**
 * 代码生成入口
 * @author yingyang & microboss
 * @since 2011-10-14
 */
public class Gen {
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
	
	public Gen(DbConn dbConn){
		genTable = new GenTable(dbConn);
		globalBean = new GlobalBean();
		settings = dbConn.getSettings();
	}
	
	/**
	 * 对于dao的生成，暂时特殊定制，硬编码。key 为数据库表名，暂不全量配置
	 */
	static{
		tconfig.put("userinfo", TableConfig.build("userinfo")
				.setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
				.addQueryMethodAndCol("getUserInfoByEmail", new String[]{"mobile","gender"})
				//.addQueryMethodAndCol("getUserInfoByEmailList", new String[]{"mobile"})
				);
//		tconfig.put("passport", TableConfig.build("passport")
//				.setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
//				.addQueryMethodAndCol("getUserPassportByEmail", new String[]{"email"})
//				.addQueryMethodAndCol("getUserPassportByMobile", new String[]{"mobile"})
//				.addQueryMethodAndCol("getUserPassportByLoginname", new String[]{"loginname"})
//				.addQueryMethodAndCol("getUserPassportByNickname", new String[]{"nickname"})
//				);
	}

	/**
	 * 生成入口
	 * @param args
	 */
	public static void main(String[] args) {
		//生成DAO代码
		Gen.doGenDB();
	}

	private static void doGenDB() {
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
		Gen gen = new Gen(dbConn);
		//设置工程的全局变量
		gen.globalBean.setNowDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));//设置系统生成时间
		gen.globalBean.setUserName(System.getenv().get("USERNAME"));//设置系统当前用户
		gen.globalBean.setPackageName(settings.getJavaPackage());//设置Java_Package路径
		//生成指定数据库的指定表或所有表数据访问层代码
		String tabName;
		List<String> tableList = gen.genTable.getTableName();
		//创建系统目录结构
		FileUtil.copyDirectiory(SOURCE_IN_PATH + settings.getTmplPath(), settings.getGenPath() + settings.getTmplPath());
		FileUtil.delExtFile(settings.getGenPath() + settings.getTmplPath(), ".vm");	//删除拷贝过去的VM文件
		// 循环生成所有表数据访问代码，返回类对象并设置类对象列表
		logger.info("共有" + tableList.size() + "个表需要生成数据访问层.");
		List<TableBean> tbList = new ArrayList<TableBean>();
		TableBean tb;
		for (int i = 0; i < tableList.size(); i++) {
			tabName = tableList.get(i);
			logger.info("第" + (i+1) + "个表["+tabName+"]数据访问层正在生成中...");
			tb = gen.doGenTable(tabName);
			if(tb!=null)tbList.add(tb);
		}
		logger.info("实际生成" + tbList.size() + "个表的数据访问层！");
		//根据类对象列表，生成全局配置及基类代码文件
		gen.doGenCFG(tbList);
		
		dbConn.closeConn();	//关闭数据库链接
		
		logger.info("所映射的字段有：");
		if(!gen.genTable.as.isEmpty()){
			Iterator<Entry<Integer, String>> it = gen.genTable.as.entrySet().iterator();
			while(it.hasNext()){
				Entry<Integer, String> entry = it.next();
				logger.info("sqltype:" + entry.getKey()+" -> "+entry.getValue());
			}
		}
		logger.info("结束执行DAO代码生成===========================");

	}

	/**
	 * 生成指定表的数据访问层
	 * @param tablename
	 * @return 表类名
	 */
	private TableBean doGenTable(String tablename) {
		TableBean tableBean = genTable.getTableBean(tablename);
		if(tableBean==null){
			return null;
		}
		
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
				packageStr = FileUtil.findLine(javaVmDir + "/" + vmFilename, "package");
				if(StringUtils.isNotBlank(packageStr)){
					packageStr = packageStr.substring(packageStr.indexOf("$!{gb.packageName}"),packageStr.indexOf(";"));
					packageDir = packageStr.replace("$!{gb.packageName}", globalBean.getPackageName()).replace(".", "/");
				}
				FileUtil.mkDirs(javaDir + packageDir);
				VelocityTemplate.mergeTemplate(settings.getTmplPath() + PATH_JAVA + "/" + vmFilename, javaDir + packageDir + "/" + tableBean.getClassName() + createFilename, ctx);
			}
			//生成SqlMap配置文件
			String sqlmapVm = settings.getTmplPath() + PATH_RESOURCES + "sqlmap/-sqlmap.xml.vm";
			String sqlmapDir = settings.getGenPath() + settings.getTmplPath() + PATH_RESOURCES + "sqlmap/";
			VelocityTemplate.mergeTemplate(sqlmapVm, sqlmapDir + tableBean.getTableName() + "-sqlmap.xml", ctx);
		} catch (Exception e) {
			logger.error("表[" + tablename +"]生成出错，异常是:",e);
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
}
