/**
 * Copyright © 2016 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Title: XSSfilter.java
 * @Prject: springweb
 * @Package: com.mckay.filter
 * @Description:
 * @author:
 * @date: 2016年12月19日 下午11:23:39
 * @version: V1.0
 */
package com.mckay.filter;

import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.mckay.constants.XSSRexConstants;

/**
 * @ClassName: XSSfilter
 * @Description: 跨站脚本攻击检测filter
 * @author: 周林波
 * @date: 2016年12月19日 下午11:23:39
 */
public class XSSCheckFilter implements Filter {  // 黑名单，要求全部小写
    // 可疑关键字、函数、标签、事件,函数加左括号，标签加左尖括号，事件加等号。
    private static final String blackList[] = new String[] {
            // 可疑关键字
            "`", "javascript", "@import", "x:script", "window.location", "jscript", "vbscript",
            "http-equiv", "![cdata[", "with(document)",
            // 可疑标签
            "<iframe", "<frameset", "<marquee", "<form", "<object", "<applet", "<embed", "<meta", "<value",
            "<comment>", "<!doctype", "<script",
            // 可疑函数
            "$.get(", "$.getscript(", "alert(", "unescape(", "execscript(", "eval(", "prompt(", "msgbox(",
            "fromcharcode(", "encodeuri(", "encodeuricomponent(", "confirm(",
            // 可疑事件
            "autofocus=", "onabort=", "onactivate=", "onafterprint=", "onafterupdate=",
            "onbeforeactivate=", "onbeforecopy=", "onbeforecut=", "onbeforedeactivate=", "onbeforeeditfocus=",
            "onbeforepaste=", "onbeforeprint=", "onbeforeunload=", "onbeforeupdate=", "onblur=", "onbounce=",
            "oncellchange=", "onchange=", "onclick=", "oncontextmenu=", "oncontrolselect=", "oncopy=", "oncut=",
            "ondataavailable=", "ondatasetchanged=", "ondatasetcomplete=", "ondblclick=", "ondeactivate=", "ondrag=",
            "ondragend=", "ondragenter=", "ondragleave=", "ondragover=", "ondragstart=", "ondrop=", "onerror=",
            "onerrorupdate=", "onfilterchange=", "onfinish=", "onfocus=", "onfocusin=", "onfocusout=", "onhelp=",
            "onkeydown=", "onkeypress=", "onkeyup=", "onlayoutcomplete=", "onload=", "onlosecapture=", "onmousedown=",
            "onmouseenter=", "onmouseleave=", "onmousemove=", "onmouseout=", "onmouseover=", "onmouseup=",
            "onmousewheel=", "onmove=", "onmoveend=", "onmovestart=", "onpaste=", "onpropertychange=",
            "onreadystatechange=", "onreset=", "onresize=", "onresizeend=", "onresizestart=", "onrowenter=",
            "onrowexit=", "onrowsdelete=", "onrowsinserted=", "onscroll=", "onselect=", "onselectionchange=",
            "onselectstart=", "onstart=", "onstop=", "onsubmit=", "onunload=" };
    /**
     * Default constructor.
     */
    public XSSCheckFilter() {

    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {

    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        request.setCharacterEncoding("UTF-8");

        Enumeration<?> enu = request.getParameterNames();
        // 遍历参数
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String[] values = request.getParameterValues(name);
            if (values != null) {
                for (String value : values) {
                    System.out.println("value:" + value);
                    if (!isXssSafe(value)) {
                        httpResponse.sendRedirect("error.jsp");
                        return;
                    }
                }

            }
        }
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {

    }

    /**
     * 根据输入key和value判断value是否为xss payload
     * 目前的实现没有用到key值，若需要根据key来特殊化规则，可以传入key值并在filter方法中添加个性化实现 isXssSafe(String
     * key, String value)
     *
     * @param key
     *            http请求参数中的key
     * @param value
     *            http请求参数中的value
     * @return 若判断value为xss payload，则返回false，否则返回true
     *
     */
    /**
     * @param value
     * @param value
     * @return
     */
    public static boolean isXssSafe(String value) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        /**** 长度限制 ****/
        // 若可能，请根据key来限制value的输入长度
        /**** 格式校验 ****/
        // 若可能，请根据key对value做格式校验

        /**** 规范化 ****/
        /**
         * 全角转半角
         *
         */
        value = Normalizer.normalize(value, Form.NFC);
        // html entity解码
        // 包含实体名称解码、实体编号解码(实体编号后面不带;则不会解码)
        value = StringEscapeUtils.unescapeHtml4(value);
        // 多行转为单行
        value = toSingleLine(value);
        // 转换为小写
        value = value.toLowerCase();

        /**** Payload特征，假设正常报文不会出现 ****/
        // 注释
        if (value.matches(XSSRexConstants.REX_COMMENT)) {
            return false;
        }

        // /正则/.source
        if (value.matches(XSSRexConstants.REX_REX_SOURCE)) {
            return false;
        }

        // &{()}
        if (value.matches(".*&\\{.*\\}.*")) {
            return false;
        }

        /**** 编码特征，假设正常报文不会出现 ****/
        // 十进制编码
        if (getCountByRex(value, "&?#\\d{1,7};?", true) > 0) {
            return false;
        }

        // 十六进制编码
        if (getCountByRex(value, "&#[x|X][0-9a-f]{1,};?", true) > 0) {
            return false;
        }
        if (getCountByRex(value, "\\\\[x|X][0-9a-fA-F]{1,}", true) > 0) {
            return false;
        }

        // unicode编码
        if (getCountByRex(value, "\\\\u?[0-9a-fA-F]{1,}", true) > 0) {
            return false;
        }
        if (getCountByRex(value, "%u[0-9a-fA-F]{4}", true) > 0) {
            return false;
        }
        if (getCountByRex(value, "\\\\u\\{.*\\}", true) > 0) {
            return false;
        }

        // url编码
        if (getCountByRex(value, "%[0-9a-fA-F]{2}", true) > 0) {
            return false;
        }

        // ascii转换
        if (getCountByRex(value, "chr\\(\\d+\\)", true) > 0) {
            return false;
        }

        // jjencode
        if (getCountByRex(value, "\\[.*?\\]", false) > 5/* magic number */) {// 使用非贪心的正则匹配[]、[code]
            return false;
        }

        /**** 净化 ****/
        // 移除特殊字符
        value = value.replaceAll("/", ""); // 移除'/'
        value = value.replaceAll("\\\\", ""); // 移除'\'
        value = value.replaceAll("\\+", ""); // 移除'+'
        value = value.replaceAll("\'", ""); // 移除'''
        value = value.replaceAll("\"", ""); // 移除'"'

        /**** 关键字过滤 ****/
        // 黑名单过滤
        for (String keyword : blackList) {
            if (value.contains(keyword)) {
                return false;
            }
        }

        // 特定场景过滤
        // data+base64
        if (value.matches(XSSRexConstants.REX_DATA_BASE64)) {
            return false;
        }
        // src
        if (value.matches(XSSRexConstants.REX_SRC)) {
            return false;
        }
        // href
        if (value.matches(XSSRexConstants.REX_HREF)) {
            return false;
        }
        // style
        if (value.matches(XSSRexConstants.REX_STYLE)) {
            return false;
        }
        // document.cookie with(document)cookie document.location
        if (value.matches(XSSRexConstants.REX_DOCUMENT)) {
            return false;
        }
        // location.hash
        if (value.matches(XSSRexConstants.REX_LOCATION_HASH)) {
            return false;
        }
        // js expression
        if (value.matches(XSSRexConstants.REX_JS_EXPRESSION)) {
            return false;
        }

        // 数组的join方法
        if (value.matches(XSSRexConstants.REX_JOIN)) {
            return false;
        }

        return true;
    }

    /**
     * 根据正则在字符串中查找，返回正则匹配到的次数
     *
     * @param str
     * @param pattern
     * @param stopFast
     *            若为true，则匹配到1次就返回，此时@return返回1
     * @return
     */
    private static int getCountByRex(String str, String pattern, boolean stopFast) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        int count = 0;
        while (m.find()) {
            count++;
            if (stopFast) {
                break;
            }
        }
        return count;
    }

    private static String toSingleLine(String value) {
        Pattern pattern = Pattern.compile("\\s"); // 匹配任何空白字符，包括空格、制表符、换页符等。与 [
        // \f\n\r\t\v] 等效。
        Matcher matcher = pattern.matcher(value);
        value = matcher.replaceAll("");
        return value;
    }
}
