package com.mckay.service;


import com.mckay.dao.UserDao;
import com.mckay.entity.TblUserInfEntity;
import com.mckay.util.MD5;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    private static final Logger log = Logger.getLogger(UserService.class);

    public TblUserInfEntity getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public boolean addUser(TblUserInfEntity userInfo) {
        try {
            String passwd = MD5.getMD5(userInfo.getPassword());
            userInfo.setPassword(passwd);
            userDao.addUser(userInfo);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    public  boolean editUser(TblUserInfEntity userInfEntity){
        try {
            userDao.editUser(userInfEntity);
            return true;
        }catch (Exception e){
            log.error(e);
            return false;
        }
    }
    public boolean isExistUser(String name){
        return  userDao.isExistUser(name);
    }

}		
