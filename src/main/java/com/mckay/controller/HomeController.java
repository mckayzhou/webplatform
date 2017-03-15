/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/2/4 22:06
 * @version: V1.0
 */
package com.mckay.controller;

import com.alibaba.fastjson.JSON;
import com.mckay.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;

/**
 *@Description :主页显示控制器
 *@Author: 周林波
 *@Date :Created in 2017/2/4  22:06
 */
@Controller
@RequestMapping(value = "")
public class HomeController extends  BaseController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value ={"/","/home"})
    public String welcome(HttpServletRequest request, HttpServletResponse response,Model model){
        return "index";
    }

    @RequestMapping(value = {"404"})
    public String error(HttpServletRequest request, HttpServletResponse response,Model model){
        return "404";
    }

    @RequestMapping(value = {"500"})
    public String fail(HttpServletRequest request, HttpServletResponse response,Model model){
        return "500";
    }
}
