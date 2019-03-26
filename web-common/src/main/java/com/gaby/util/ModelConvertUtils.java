package com.gaby.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class ModelConvertUtils {
	/**
	 * 得到一个UUID随机数
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();	
	}
	
	
	/**
	 *这个方法需要引入commons-beanutils.jar和logging.jar（依赖这两个包）
	 * 将map中的数据封装到javaBean中
	 * @param map
	 * @param beanClass
	 * @return
	 */
	public static <T> T toObject(Map map,Class<T> beanClass){
		try {
			
			//创建一个实例
			T bean=beanClass.newInstance();
			//将map数据封装到实例中
			BeanUtils.populate(bean, map);
			//返回实例
			return bean;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	/**
	 *这个方法需要引入commons-beanutils.jar和logging.jar（依赖这两个包）,对时间类的支持
	 * 将map中的数据封装到javaBean中
	 * @param map
	 * @param beanClass
	 * @return
	 */
	public static <T> T toBean(Map map,Class<T> beanClass){
		try {
			
			//创建一个实例
			T bean=beanClass.newInstance();
			 //处理时间格式
	        DateConverter dateConverter = new DateConverter();
	        //设置日期格式
	        dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"});
	        //注册格式
	        ConvertUtils.register(dateConverter, Date.class);
			//将map数据封装到实例中
			BeanUtils.populate(bean, map);
			//返回实例
			return bean;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}