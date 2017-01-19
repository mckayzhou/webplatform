package com.service;


import com.dao.UserDao;
import com.entity.User;
import com.util.MD5;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("userService")

public class UserService {

	@Autowired
	private UserDao userDao;
	
	private static final Logger log=Logger.getLogger(UserService.class);
	
	public boolean logIn(User userInfo){
		
		return userDao.login(userInfo);
	}
	
	public boolean addUser(User userInfo){
		try {
			String passwd= MD5.getMD5(userInfo.getPassword());
			userInfo.setPassword(passwd);
			userDao.save(userInfo);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
}		
