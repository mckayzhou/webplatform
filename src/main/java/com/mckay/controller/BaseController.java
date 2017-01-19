/**   
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 * 
 * @Title: BaseController.java 
 * @Prject: springweb
 * @Package: com.mckay.controller 
 * @Description:
 * @author:
 * @date: 2017年1月2日 下午5:27:35 
 * @version: V1.0   
 */
package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.entity.User;
import com.util.FileUploadUtil;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;


/** 
 * @ClassName: BaseController 
 * @Description: 基础控制类，用于获取用户信息，提供下载等基础服务
 * @author: 周林波
 * @date: 2017年1月2日 下午5:27:35  
 */
public abstract class BaseController {
	


	private Logger log=Logger.getLogger(BaseController.class);
	/**
	 * 获取当前登录用户信息
	 * 
	 * @param request
	 * @return
	 */
	public static User getUserInfo(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			User userInfo = (User) session.getAttribute(session
					.getId());
			return userInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 为response提供Json格式的返回数据
	 * 
	 * @param obj
	 *            任何对象
	 * @return void
	 */
	public void writeResponse(Object obj, HttpServletResponse response) {
		try {
			response.setContentType("text/json;charset=utf-8");

			String str = JSON.toJSONString(obj);

			PrintWriter writer;
			writer = response.getWriter();
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为response提供String数据的返回，字符集为utf-8
	 * 
	 * @param str
	 *            字符串
	 * @return void
	 */
	public void writeResponseStr(String str, HttpServletResponse response) {

		writeResponseStr(str, response, "utf-8");
	}

	/**
	 * 为response提供String数据的返回
	 * 
	 * @param str
	 *            字符串
	 * @param encoding
	 *            字符编码
	 */
	public void writeResponseStr(String str, HttpServletResponse response,
			String encoding) {
		try {
			response.setContentType("text/html;charset=" + encoding);

			PrintWriter writer;
			writer = response.getWriter();
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取当前登录用户的IP地址
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getIpAddr(HttpServletRequest request) throws Exception {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")
					|| ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(
			final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);

				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}

	/**
	 * 校验请求头中的csrfToken,用于防止跨站脚本攻击
	 * 
	 * @since
	 * @param request
	 * @return
	 */
	protected boolean isValidCsrfHeaderToken(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (request.getHeader("__RequestVerificationToken") == null
				|| session.getAttribute("csrfToken") == null
				|| !request.getHeader("__RequestVerificationToken").equals(
						session.getAttribute("csrfToken").toString())) {
			return false;
		}
		return true;
	}

	public void downloadFile(HttpServletRequest request,  HttpServletResponse response,
							 String fileName, String filePath){
		//下载文件
		File  file =new File(filePath+fileName);
		try{
				//如果文件存在
			if (file.exists()){
				response.reset();
				response.setContentType("application/x-msdownload");
				String fileDownName=new String(fileName.getBytes("gbk"),"iso8859-1");
				response.addHeader("Content-Disposition","attachment;filename=\""+fileDownName+"\"");
				int fileLength=(int) file.length();
				response.setContentLength(fileLength);

				if (fileLength!=0){
					//创建输入流
					InputStream inStream=new FileInputStream(file);
					byte[] buf =new byte[4096];
					//创建输出流
					ServletOutputStream servletOS=response.getOutputStream();
					int readLength;
					while (((readLength=inStream.read(buf))!=-1)){
							servletOS.write(buf,0,readLength);
					}
					servletOS.flush();
					inStream.close();
					servletOS.close();
				}
			}
		}catch (Exception e){
				log.error("下载文件出错："+e);
		}
	}
	public boolean uploadFile(HttpServletRequest request, HttpServletResponse response,
							  MultipartFile file,String fileNm,String filePath){
		if (!file.isEmpty()){
			FileUploadUtil fileUploadUtil=new FileUploadUtil();
			String fileType=fileUploadUtil.getFileType(file);
			try{
				if (fileUploadUtil.uploadFile(file,filePath,fileNm))
					log.info("上传文件成功:"+filePath+fileNm);
			}catch (Exception e){
					log.error("上传文件失败"+filePath+fileNm+ e);
					return false;
			}
		}
		return  true;
	}
}
