/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/3/15 21:42
 * @version: V1.0
 */
package com.mckay.util;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *@Description :签名类，用于报文签名、验签
 *@Author: 周林波
 *@Date :Created in 2017/3/15  21:42
 */
public class Sign {


    public  static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
    *@description :签名方法
    *@param :
    *@return :
    */
    public static String sign(String content, String privateKey)
    {
        try
        {
            PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64.decode(privateKey) );
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update( content.getBytes());
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String sign(String content, PrivateKey privateKey)
    {
        try
        {
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKey);
            signature.update( content.getBytes());
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
    *@description :验签方法
    *@param :
    *@return :
    */
    public static boolean validate(String content, String sign, String publicKey) {
        try
        {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
           Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update( content.getBytes() );
            boolean verify = signature.verify( Base64.decode(sign) );
            return verify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validate(String content, String sign, PublicKey publicKey)
    {
        try
        {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(publicKey);
            signature.update( content.getBytes() );
            boolean verify = signature.verify( Base64.decode(sign) );
            return verify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
