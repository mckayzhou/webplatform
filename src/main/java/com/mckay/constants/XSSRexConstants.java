/**   
 * Copyright © 2016 本代码版权归周林波所有，严禁未经许可使用。
 * 
 * @Title: XSSRexConstants.java 
 * @Prject: springweb
 * @Package: com.mckay.constrants 
 * @Description:
 * @author:
 * @date: 2016年12月31日 下午4:19:59 
 * @version: V1.0   
 */
package com.constants;

/** 
 * @ClassName: XSSRexConstants 
 * @Description: TODO
 * @author: 周林波
 * @date: 2016年12月31日 下午4:19:59  
 */
public class XSSRexConstants {

	public static final String REX_COMMENT = "(.*<!--.*-->.*)|(.*/\\*.*\\*/.*)";
	public static final String REX_DATA_BASE64 = ".*data.*;base64.*";
	public static final String REX_REX_SOURCE = ".*\\/.*\\/\\.source.*";
	public static final String REX_JS_EXPRESSION = ".*expression.*\\(.*\\).*";
	public static final String REX_JOIN = ".*join.*\\(.*\\).*";
	public static final String REX_HREF = ".*<.*href.*=.*>.*";
	public static final String REX_LOCATION_HASH = ".*location.*hash.*";
	public static final String REX_DOCUMENT = ".*document.*(cookie|location).*";
	public static final String REX_SRC = ".*<.*src.*";
	public static final String REX_STYLE = ".*<.*style.*";
	public static final String REX_JJ = ".*\\[.*?\\].*";
}
