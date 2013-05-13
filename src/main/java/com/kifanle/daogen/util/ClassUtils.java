package com.kifanle.daogen.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

public class ClassUtils {
	 /** 
     * 迭代查找类 
     *  
     * @param dir 
     * @param pk 
     * @return 
     * @throws ClassNotFoundException 
     */  
    public static List<Class<?>> getClasses(File dir, String pk){  
        List<Class<?>> classes = new ArrayList<Class<?>>();  
        if (!dir.exists()) {  
            return classes;  
        }
        try{
			for (File f : dir.listFiles()) {
				if (!f.isDirectory()) {
					String name = f.getName();
					if (name.endsWith(".class")) {
						classes.add(Class.forName(pk + "."
								+ name.substring(0, name.length() - 6)));
					}
				}
			}
        }catch(ClassNotFoundException e){
        	return Collections.EMPTY_LIST;
        }
        return classes;  
    }  
    
	public static String getLowerCaseNames(String name){
		StringBuilder sb = new StringBuilder();
		char[] cc = name.toCharArray();
		sb.append(CharUtil.toLowerCase(cc[0]));
		for(int i=1;i<cc.length;i++){
			if(CharUtil.isLowerCase(cc[i-1])&&CharUtil.isUpperCase(cc[i])){
				sb.append("_"+CharUtil.toLowerCase(cc[i]));
			}else{
				sb.append(CharUtil.toLowerCase(cc[i]));
			}
		};
		return sb.toString();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
