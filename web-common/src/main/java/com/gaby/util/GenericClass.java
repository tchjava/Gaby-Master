package com.gaby.util;

import java.lang.reflect.ParameterizedType;
/**
*@discrption:泛型类型工具类
*@user:Gaby
*@createTime:2019-03-17 22:10
*/
public class GenericClass {

	/**
	 * 获取父类的泛型类型
	 * @param clazz
	 * @return
	 */
	public static Class getGenericClass(Class clazz) {
		ParameterizedType  type=(ParameterizedType)clazz.getGenericSuperclass();
		
		/*
		 * cn.itcast.crm.dao.impl.CommonDaoImpl<cn.itcast.crm.domain.SysUserGroup>
		 * CommonDaoImpl<SysUserGroup>
		 */
		//System.out.println("type  "+type);
		
		Class entityClass=(Class) type.getActualTypeArguments()[0];
		
		//cn.itcast.crm.domain.SysUserGroup
		//System.out.println("entityClass  "+entityClass);
		
		return entityClass;
	}

}
