/**   
 * Copyright © 2016 本代码版权归周林波所有，严禁未经许可使用。
 * 
 * @Title: Myfilter.java 
 * @Prject: springweb
 * @Package: com.mckay.filter 
 * @Description: TODO
 * @author: zhoulinbo   
 * @date: 2016年12月19日 下午10:00:49 
 * @version: V1.0   
 */
package com.mckay.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** 
 * @ClassName: Myfilter 
 * @Description: TODO
 * @author: Administrator
 * @date: 2016年12月19日 下午10:00:49  
 */
public class MyFilter implements Filter{

	/* (non Javadoc) 
	 * @Title: destroy
	 * @Description: TODO 
	 * @see javax.servlet.Filter#destroy() 
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/* (non Javadoc) 
	 * @Title: doFilter
	 * @Description: TODO
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws IOException
	 * @throws ServletException 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain) 
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
			throws IOException, ServletException {
		
		if (!(request instanceof HttpServletRequest)||!(response instanceof HttpServletResponse) ) {
			throw new ServletException("this system only deal with HTTP resources");
		}
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		
		HttpSession session=req.getSession();
		
		
		fc.doFilter(request, response);
	
		
	}

	/* (non Javadoc) 
	 * @Title: init
	 * @Description: TODO
	 * @param arg0
	 * @throws ServletException 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig) 
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
