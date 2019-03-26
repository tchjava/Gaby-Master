package com.gaby.web.plugin.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//获取用户请求的方法
		String method=request.getParameter("method");
		//判断method是否为空格或者null
		if(method==null||method.trim().isEmpty()) throw new RuntimeException("你请求的方法"+method+"不存在");
		//获得字节码文件对象
		Class c=this.getClass();
		try {
			Method m=c.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			//得到一个字符串uri
			String uri=(String)m.invoke(this,request,response);
			//判断一下uri具体是要干什么
			if(uri==null||uri.trim().isEmpty())return ;
			//判断uri中是否包含"：",如果没有，默认是转发.如果有的话向下执行
			if(!uri.contains(":"))request.getRequestDispatcher(uri).forward(request, response);
			//查看":"的位置
			int index=uri.indexOf(":");
			//是f的话就进行转发操作
			if("f".equalsIgnoreCase(uri.substring(0,index)))request.getRequestDispatcher(uri.substring(index+1)).forward(request, response);
			//如果是r的话 就进行重定向操作
			else if("r".equalsIgnoreCase(uri.substring(0, index)))response.sendRedirect(request.getContextPath()+uri.substring(index+1));
			//如果既不是r也不是f的话  那么就抛出一个异常
			else throw new RuntimeException("您的"+uri.substring(0,index)+"操作该版本不支持");
		} catch (Exception e) {
			throw new RuntimeException("你请求的方法"+method+"内部抛出了异常");
		}
		
	}
}
