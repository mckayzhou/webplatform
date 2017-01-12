package com.mckay.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mckay.dao.UserDao;
import com.mckay.entity.User;
import com.mckay.util.MD5;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	private static final Logger log=Logger.getLogger(UserService.class);
	
	public boolean logIn(User userInfo){
		
		return userDao.login(userInfo);
	}
	
	public boolean addUser(User userInfo){
		try {
			String passwd=MD5.getMD5(userInfo.getPassword());
			userInfo.setPassword(passwd);
			userDao.save(userInfo);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
}		
