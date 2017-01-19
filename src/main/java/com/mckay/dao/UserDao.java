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
package com.dao;

import java.io.Serializable;

import com.entity.User;
import com.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

/** 
 * @ClassName: loginDao 
 * @Description: TODO
 * @author: Administrator
 * @date: 2016年12月18日 下午11:23:34  
 */
@Repository
public class UserDao extends HibernateBaseDao<User, Serializable> {
	
	public boolean login(User user){
		
		
		
		return true;
	}
	public boolean addUser(User user){
		saveOrUpdate(user);
		return true;
	}
	
	public boolean editUser(User user){
		
		return true;
	}
}
