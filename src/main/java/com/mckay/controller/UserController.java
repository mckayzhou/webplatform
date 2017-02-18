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

import com.mckay.constants.UserConstants;
import com.mckay.entity.TblUserInfEntity;
import com.mckay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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


    @RequestMapping(value = "login")
    public String  login(HttpServletRequest request, HttpServletResponse response) {
        return "user/login";
    }

    @RequestMapping(value = "login.do")
    public String loginCheck(HttpServletRequest request,HttpServletResponse response,
                              @RequestParam(value = "name") String name,@RequestParam(value = "password") String password){
        TblUserInfEntity user=userService.getUserByName(name);
        if(user==null){
            return UserConstants.USER_NOT_REGISTER;
        }else if (user.getPassword().equals(password)){
            HttpSession session=request.getSession();
            session.setAttribute(UserConstants.LOGIN_USER_INFO,user);
            return UserConstants.LOGIN_SUCCESS;
        }else {
            return UserConstants.LOGIN_FAILURE;
        }
    }

    @RequestMapping(value = "register")
    public String registerView(HttpServletRequest request, HttpServletResponse response){
        return "user/register";
    }

    @RequestMapping(value = "register.do")
    public String addUser(HttpServletRequest request,HttpServletResponse response,TblUserInfEntity user){

        String name=user.getName();
        boolean isExist=userService.isExistUser(name);
        if (isExist){
            return UserConstants.USER_EXIST;
        }else {
            boolean addStatus=userService.addUser(user);
            if (addStatus){
                return UserConstants.REGISTER_SUCCESS;
            }
        }
        return UserConstants.REGISTER_FAILURE;
    }

    @RequestMapping(value = "edit")
    public String editView(HttpServletRequest request,HttpServletResponse response){
        return "user/edit";
    }
    @RequestMapping(value = "edit.do")
    public String editUser(HttpServletRequest request, HttpServletResponse response,TblUserInfEntity user){
        String name=user.getName();
        boolean isExist=userService.isExistUser(name);
        if (isExist){
            boolean editStatus=userService.editUser(user);
            if (editStatus){
                return UserConstants.EDIT_SUCCESS;
            }
        }
        return UserConstants.EDIT_FAILURE;
    }
}
