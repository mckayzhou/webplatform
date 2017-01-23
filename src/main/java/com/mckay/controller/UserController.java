/**
 * Copyright © 2016 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Title: loginController.java
 * @Prject: springweb
 * @Package: com.mckay.controller
 * @Description:
 * @author: zhoulinbo
 * @date: 2016年12月19日 下午10:07:09
 * @version: V1.0
 */
package com.mckay.controller;

import com.mckay.entity.TblUserInfEntity;
import com.mckay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: loginController
 * @Description: 登录控制器
 * @author: 周林波
 * @date: 2016年12月19日 下午10:07:09  
 */
@Controller
@RequestMapping(value = "user/")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "login.do")
    public boolean login(HttpServletRequest request, HttpServletResponse response,
                         TblUserInfEntity userInfo) {

        return userService.logIn(userInfo);
    }
}
