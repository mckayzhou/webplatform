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
import com.mckay.model.Data;
import com.mckay.service.UserService;
import com.mckay.util.MD5;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @ClassName: loginController
 * @Description: 登录控制器
 * @author: 周林波
 * @date: 2016年12月19日 下午10:07:09  
 */
@Controller
@RequestMapping(value = "")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    private Logger log=Logger.getLogger(UserController.class);

    @RequestMapping(value = "login")
    public String  login(HttpServletRequest request, HttpServletResponse response) {
        return "user/login";
    }

    @RequestMapping(value = "login.do")
    public void loginCheck(HttpServletRequest request,HttpServletResponse response,
                              @RequestParam(value = "name") String name,@RequestParam(value = "password") String password){
        TblUserInfEntity user=userService.getUserByName(name);
        String pwd;
        try {
            pwd=MD5.MD5(password);
        }catch (Exception e){
            return;
        }
        if(user==null){
            log.debug("user is null");
            writeResponseStr(UserConstants.USER_NOT_REGISTER,response);
        }else if (MD5.validPassword(password,pwd)){
            HttpSession session=request.getSession();
            session.setAttribute(UserConstants.LOGIN_USER_INFO,user);
            log.info("user login in success");
            writeResponse(UserConstants.LOGIN_SUCCESS ,response);
        }else {
            log.debug("login failure");
            writeResponse(UserConstants.LOGIN_FAILURE ,response);
        }
    }

    @RequestMapping(value = "register")
    public String registerView(HttpServletRequest request, HttpServletResponse response){
        return "user/register";
    }

    @RequestMapping(value = "register.do")
    @ResponseBody
    public String addUser(HttpServletRequest request,HttpServletResponse response,TblUserInfEntity user){

        String name=user.getName();
        boolean isExist=userService.isExistUser(name);
        if (isExist){
            return UserConstants.USER_EXIST;
        }else {
            user.setStatus("0");
            user.setPassword(MD5.MD5(user.getPassword()));
            user.setRecCrtTs(new Timestamp((new Date()).getTime()));
            user.setRecUpdTs(null);
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
    @ResponseBody
    public String editUser(HttpServletRequest request, HttpServletResponse response,TblUserInfEntity user){
        String name=user.getName();
        boolean isExist=userService.isExistUser(name);
        if (isExist){
            try {
                user.setPassword(MD5.MD5(user.getPassword()));
            }catch (Exception e){
                return UserConstants.EDIT_FAILURE;
            }
            boolean editStatus=userService.editUser(user);
            if (editStatus){
                return UserConstants.EDIT_SUCCESS;
            }
        }
        return UserConstants.EDIT_FAILURE;
    }
}
