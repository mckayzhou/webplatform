/**   
 * Copyright © 2016 本代码版权归周林波所有，严禁未经许可使用。
 * 
 * @Title: UserInfo.java 
 * @Prject: springweb
 * @Package: com.mckay.entity 
 * @Description:
 * @author:
 * @date: 2016年12月30日 下午10:45:01 
 * @version: V1.0   
 */
package com.mckay.entity;

import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * @ClassName: UserInfo 
 * @Description: TODO
 * @author: 周林波
 * @date: 2016年12月30日 下午10:45:01  
 */
@Entity
@Table(name="tbl_user")
public class User {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="pass_word")
	private String password;
	
	@Column(name="status")
	private String status;
	
	@Column(name="rec_crt_ts")
	private Timestamp recCrtTs;
	
	@Column(name="rec_upd_ts")
	private Timestamp recUpdTs ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(Timestamp recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public Timestamp getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(Timestamp recUpdTs) {
		this.recUpdTs = recUpdTs;
	}
	
	
}
