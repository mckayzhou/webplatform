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

import java.io.Serializable;

/**
 * @ClassName: loginDao
 * @Description: TODO
 * @author: Administrator
 * @date: 2016年12月18日 下午11:23:34  
 */
@Repository
public class UserDao extends HibernateBaseDao<TblUserInfEntity, Serializable> {

    public boolean login(TblUserInfEntity user) {


        return true;
    }

    public boolean addUser(TblUserInfEntity user) {
        saveOrUpdate(user);
        return true;
    }

    public boolean editUser(TblUserInfEntity user) {

        return true;
    }
}
