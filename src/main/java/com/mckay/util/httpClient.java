/**
 * Copyright © 2016 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Title: httpClient.java
 * @Prject: springweb
 * @Package: com.mckay.util
 * @Description:
 * @author:
 * @date: 2016年12月19日 下午10:28:14
 * @version: V1.0
 */
package com.mckay.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: httpClient
 * @Description: 发起HTTP请求
 * @author: 周林波
 * @date: 2016年12月19日 下午10:28:14
 */
public class httpClient {

    /**
     *@Description:发送GET请求
     *@Param:
     *@Return:
     */
    public static BufferedReader sendGet(String url, HashMap<String, String> param) {

        Logger log = Logger.getLogger(httpClient.class);
        String result = "";
        BufferedReader in = null;
        try {
            String urlPara = "";
            URL realURL = new URL(urlPara);
            //打开和URL之间的连接
            URLConnection connection = realURL.openConnection();
            //设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            //建立连接
            connection.connect();

            //获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            //定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return in;
        } catch (Exception e) {
            log.error(e);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {

                }
            }

        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }
}
