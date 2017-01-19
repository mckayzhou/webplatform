/**   
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 * 
 * @Title: MD5.java 
 * @Prject: springweb
 * @Package: com.mckay.util 
 * @Description:
 * @author:
 * @date: 2017年1月1日 下午9:01:34 
 * @version: V1.0   
 */
package com.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.log4j.Logger;


/** 
 * @ClassName: MD5 
 * @Description: MD5加密工具
 * @author: 周林波
 * @date: 2017年1月1日 下午9:01:34  
 */
public class MD5 {
	
	private static final Logger log =Logger.getLogger(MD5.class);
	public static String getMD5(String str) throws Exception {
	    try {
	        // 生成一个MD5加密计算摘要
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        // 计算md5函数
	        md.update(str.getBytes());
	        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	    	log.error("MD5加密异常"+e);
	        throw new Exception("MD5加密出现错误");
	    }
	}
}
