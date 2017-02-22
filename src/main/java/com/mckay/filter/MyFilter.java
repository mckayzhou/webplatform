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

import com.mckay.constants.UserConstants;
import com.mckay.entity.TblUserInfEntity;

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
 * @Description: 自定义过滤类
 * @author: Administrator
 * @date: 2016年12月19日 下午10:00:49  
 */
public class MyFilter implements Filter {


    private static final String[] INGNORE_URI={"login","resources/"};

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("this system only deal with HTTP resources");
        }
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if(checkURI(req)){
            fc.doFilter(request,response);
            return;
        }
        HttpSession session = req.getSession();
        TblUserInfEntity user=(TblUserInfEntity) session.getAttribute(UserConstants.LOGIN_USER_INFO);
        if (user==null){
            String contextName=req.getContextPath();
            resp.sendRedirect(contextName+"/user/login");
            return;
        }


        fc.doFilter(request, response);
    }

    public boolean checkURI(HttpServletRequest request){
        String requestURI=request.getRequestURI();
        for (String uri:INGNORE_URI){
            if (requestURI.toLowerCase().contains(uri.toLowerCase())){
                return  true;
            }
        }
        return  false;
    }

    public void destroy() {}

    public void init(FilterConfig arg0) throws ServletException {}
}
