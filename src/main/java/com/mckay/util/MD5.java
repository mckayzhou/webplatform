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
package com.mckay.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import org.apache.log4j.Logger;


/**
 * @ClassName: MD5
 * @Description: MD5加密工具
 * @author: 周林波
 * @date: 2017年1月1日 下午9:01:34  
 */
public class MD5 {

    private static final Logger log = Logger.getLogger(MD5.class);
    // 16进制需要的字符串数据
    private static final String HEX_NUMS_STR = "0123456789ABCDEF";

    // 随机字符串字节数长度
    private static final Integer BYTE_LENGTH = 16;

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR
                    .indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 验证口令是否合法
     *
     * @param inputStr
     * @param strWithRandom
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static boolean validPassword(String inputStr, String strWithRandom) {
        boolean flag = false;
        try {
            // 将16进制字符串格式口令转换成字节数组
            byte[] pwdInDb = hexStringToByte(strWithRandom);
            // 声明一个随机数组变量
            byte[] randomByte = new byte[BYTE_LENGTH];
            // 将随机数组从数据库中保存的口令字节数组中提取出来，按其长度
            System.arraycopy(pwdInDb, 0, randomByte, 0, BYTE_LENGTH);
            // 创建消息摘要对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将随机数组据传入消息摘要对象
            md.update(randomByte);
            // 将口令的数据传给消息摘要对象
            md.update(inputStr.getBytes("UTF-8"));
            // 生成输入口令的消息摘要
            byte[] digest = md.digest();
            // 声明一个保存数据库中口令消息摘要的变量
            byte[] digestInDb = new byte[pwdInDb.length - BYTE_LENGTH];
            // 取得数据库中口令的消息摘要
            System.arraycopy(pwdInDb, BYTE_LENGTH, digestInDb, 0,
                    digestInDb.length);
            // 比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
            if (Arrays.equals(digest, digestInDb)) {
                // 口令正确返回口令匹配消息
                flag = true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 获得加密后的16进制形式口令
     *
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String MD5(String password) {
        // 声明加密后的口令数组变量
        byte[] pwd = null;
        // 随机数生成器
        SecureRandom random = new SecureRandom();
        // 声明随机数组变量
        byte[] randomByte = new byte[BYTE_LENGTH];
        try {
            // 将随机数放入随机数组变量中
            random.nextBytes(randomByte);

            // 声明消息摘要对象
            MessageDigest md = null;
            // 创建消息摘要
            md = MessageDigest.getInstance("MD5");
            // 将盐数据传入消息摘要对象
            md.update(randomByte);
            // 将口令的数据传给消息摘要对象
            md.update(password.getBytes("UTF-8"));
            // 获得消息摘要的字节数组
            byte[] digest = md.digest();

            // 因为要在口令的字节数组中存放随机数组密文，所以加上随机数组的字节长度
            pwd = new byte[digest.length + BYTE_LENGTH];
            // 将盐的字节拷贝到生成的加密口令字节数组的前相应该长度BYTE_LENGTH个字节，以便在验证口令时取出随机数组
            System.arraycopy(randomByte, 0, pwd, 0, BYTE_LENGTH);
            // 将消息摘要拷贝到加密口令字节数组从第BYTE_LENGTH个字节开始的字节
            System.arraycopy(digest, 0, pwd, BYTE_LENGTH, digest.length);
            // 将字节数组格式加密后的口令转化为16进制字符串格式的口令
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return byteToHexString(pwd);
    }

}
