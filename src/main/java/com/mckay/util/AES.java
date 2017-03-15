/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/3/15 21:29
 * @version: V1.0
 */
package com.mckay.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 *@Description :对称加密类工具，主要用于报文加密，效率高
 *@Author: 周林波
 *@Date :Created in 2017/3/15  21:29
 */
public class AES {

    private static final Logger logger = Logger.getLogger(AES.class);

    public static String entrypt(String entryptContent, String key)throws NoSuchAlgorithmException, UnsupportedEncodingException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        return entrypt(entryptContent, key, false);
    }

    /**
     * 加密
     *
     * @param entryptContent
     *            加密的内容
     * @param key
     *            加密的钥匙
     * @return
     */
    public static String entrypt(String entryptContent, String key, boolean useSHA256)
            throws NoSuchAlgorithmException, UnsupportedEncodingException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        String strPassword = key;
        if (null == entryptContent || entryptContent.trim().length() < 1) {
            return null;
        }

        if(useSHA256)
            strPassword = generateKeySHA256(strPassword);
        else
            strPassword = generateKey(strPassword);

        byte[] raw = strPassword.getBytes("UTF8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(entryptContent.getBytes());
        return Base64.encodeBase64String(encrypted);

    }

    /**
     * * 解密
     *
     * @param detryptContent
     *            解密的内容
     * @param key
     *            解密的钥匙
     * @return
     */
    public static String detrypt(String detryptContent, String key)throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IOException, IllegalBlockSizeException,
            BadPaddingException {
        return detrypt(detryptContent, key, false);
    }

    /**
     *
     * @param detryptContent--解密的内容
     * @param key--解密的钥匙
     * @param useSHA256--是否使用SHA256转换密钥，如不使用，默认使用MD5转换密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String detrypt(String detryptContent, String key, boolean useSHA256)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IOException, IllegalBlockSizeException,
            BadPaddingException {
        String strPassword = key;
        if (null == detryptContent || detryptContent.trim().length() < 1) {
            return null;
        }

        if(useSHA256)
            strPassword = generateKeySHA256(strPassword);
        else
            strPassword = generateKey(strPassword);

        byte[] raw = strPassword.getBytes("UTF8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted1 = org.apache.commons.codec.binary.Base64.decodeBase64(detryptContent);
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString;
    }

    /**
     * * BASE64编码
     *
     * @param str
     * @return
     */
    private static String generateKey(String str)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        java.security.MessageDigest md = java.security.MessageDigest
                .getInstance("MD5");
        md.update(str.getBytes("UTF8"));
//		String strret = (new sun.misc.BASE64Encoder()).encode(md.digest());
        String strret =  Base64.encodeBase64String(md.digest());
        while (strret.length() < 16) {
            strret += "%";
        }
        if (strret.length() > 16) {
            int nbegin = (strret.length() - 16) / 2;
            strret = strret.substring(nbegin, nbegin + 16);
        }
        return strret;
    }

    /**
     * * BASE64编码
     *
     * @param str
     * @return
     */
    private static String generateKeySHA256(String str)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        java.security.MessageDigest md = java.security.MessageDigest
                .getInstance("SHA-256");
        md.update(str.getBytes("UTF8"));
        String strret =  Base64.encodeBase64String(md.digest());
        while (strret.length() < 16) {
            strret += "%";
        }
        if (strret.length() > 16) {
            int nbegin = (strret.length() - 16) / 2;
            strret = strret.substring(nbegin, nbegin + 16);
        }
        return strret;
    }


}
