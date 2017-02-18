/**
 * Copyright © 2016 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Title: loginDao.java
 * @Prject: springweb
 * @Package: com.mckay.dao
 * @Description: TODO
 * @author: zhoulinbo
 * @date: 2016年12月18日 下午11:23:34
 * @version: V1.0
 */
package com.mckay.dao;

import com.mckay.entity.TblUserInfEntity;
import com.mckay.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: loginDao
 * @Description: TODO
 * @author: Administrator
 * @date: 2016年12月18日 下午11:23:34  
 */
@Repository
@Transactional
public class UserDao extends HibernateBaseDao<TblUserInfEntity, String>  {


    /**
    *@description :获取所有用户信息
    *@param : 空
    *@return :用户信息
    */
    public List<TblUserInfEntity> getAllUser(){

        List<TblUserInfEntity> data=getAll();
       return  data;
    }

    public TblUserInfEntity getUserByName(String name) {
        TblUserInfEntity user=findUniqueByProperty("name",name);
        return  user;
    }

    public boolean addUser(TblUserInfEntity user) throws Exception {
        saveOrUpdate(user);
        return true;
    }

    public boolean editUser(TblUserInfEntity user) throws Exception {
        saveOrUpdate(user);
        return true;
    }

    public boolean isExistUser(String name){
        TblUserInfEntity user=findUniqueByProperty("name",name);
        if (user!=null){
            return  true;
        }
        return false;
    }
}
