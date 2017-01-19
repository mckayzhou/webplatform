/**   
 * Copyright © 2016 本代码版权归周林波所有，严禁未经许可使用。
 * 
 * @Title: RSAEncrypt.java 
 * @Prject: springweb
 * @Package: com.mckay.util 
 * @Description:
 * @author:
 * @date: 2016年12月21日 下午2:10:26 
 * @version: V1.0   
 */
package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


/** 
 * @ClassName: RSAEncrypt 
 * @Description: RSA加解密
 * @author: 周林波
 * @date: 2016年12月21日 下午2:10:26  
 */
public class RSAEncrypt {
	

	  
    // RSA公钥文件
    private static final String DEFAULT_PUBLIC_KEY=  
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/Az9765Z64bIA6km1u2S2B5Gr" + "\r" +
        "hpIBzBxs2woTltXH6nKXvtGEiEFLM/rxJD0ce8jDL5FezcDJK+brk4OFWH60DxqM" + "\r" +
        "oGJib1kNCHdHd6SzN2nbzBAS+8dVxU5FLMYaoQ/+plKVBdlph+5SzeeBs9apr8no" + "\r" +
        "UOKIVUN0+IcYp5r/CQIDAQAB" + "\r";
    
    // RSA私钥文件
    private static final String DEFAULT_PRIVATE_KEY= 
        "MIICXAIBAAKBgQC/Az9765Z64bIA6km1u2S2B5GrhpIBzBxs2woTltXH6nKXvtGE" + "\r" +
        "iEFLM/rxJD0ce8jDL5FezcDJK+brk4OFWH60DxqMoGJib1kNCHdHd6SzN2nbzBAS" + "\r" +
        "+8dVxU5FLMYaoQ/+plKVBdlph+5SzeeBs9apr8noUOKIVUN0+IcYp5r/CQIDAQAB" + "\r" +
        "AoGAAypMnQMUZUjkKDu5qPdH/fQ2J799H+Z/+hXPHu/67uslpvoUgru3SUW9PM26" + "\r" +
        "7YPnmcRqBkYmka7WjsTNAyKqRs4zFCRqbOQZC4swAhToDimkQlQLq+Lj/qK78SeX" + "\r" +
        "3Z8Mu2+mxS1xqNonLrzKT97CkyP6cSlBVf6p5iFRYayvK9kCQQD1M87OStIH3IgJ" + "\r" +
        "VMB+VJra9M5nsEKZkSmCJ4UGhCU/rKIZ/pmADGkzEFMk3VYpAKDqJL2/wXHLRjqM" + "\r" +
        "dJ4NpByvAkEAx2yN2fkVOy9JESfisSXiNxzlt9JkwmqdtgzYxk/Q3NN0B8zzdRpA" + "\r" +
        "BaeSSgeoOaQVXvdm/AhHGVYlZCwsy709xwJBAK51lw319Cv9v5iMAY2g2rEw/u6N" + "\r" +
        "XUVwIA1n2uEBApOkW42NNWAC9JKNaerPxG7V8KfHTm5xz2om5J9slcv02ScCQBnX" + "\r" +
        "KxpPeDzPB7y9WlZx/jn9+JY1IPFAN2dbistHlFzI2YQZPoKyABG7gO0uyNGPPWWs" + "\r" +
        "0R9fQzc5PmcYgkV0JAkCQCqRXwHv3tx7NA9+l60E7+2rOXS78Bfe9DBaw8ZYfDAZ" + "\r" +
        "hSiT+dyg6STFxzmsnzjUkWMyi2OD1M0xGUrnTpPS40I=" + "\r";
    
    /** 
     * 私钥 
     */  
    private RSAPrivateKey privateKey;  
  
    /** 
     * 公钥 
     */  
    private RSAPublicKey publicKey;  
      
    private static RSAEncrypt rsaEncrypt;
    
    public static RSAEncrypt getRSAEncrypt() throws Exception{
        if(rsaEncrypt == null){
            rsaEncrypt = new RSAEncrypt();
            // 加载公钥
            rsaEncrypt.loadPublicKey(RSAEncrypt.DEFAULT_PUBLIC_KEY);
            // 加载私钥
            rsaEncrypt.loadPrivateKey(RSAEncrypt.DEFAULT_PRIVATE_KEY);
        }
        return rsaEncrypt;
    }
    
    
    /** 
     * 获取私钥 
     * @return 当前的私钥对象 
     */  
    public RSAPrivateKey getPrivateKey() {  
        return privateKey;  
    }  
  
    /** 
     * 获取公钥 
     * @return 当前的公钥对象 
     */  
    public RSAPublicKey getPublicKey() {  
        return publicKey;  
    }  
  
    /** 
     * 随机生成密钥对 
     */  
    public void genKeyPair(){  
        KeyPairGenerator keyPairGen= null;  
        try {  
            keyPairGen= KeyPairGenerator.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        keyPairGen.initialize(1024, new SecureRandom());  
        KeyPair keyPair= keyPairGen.generateKeyPair();  
        this.privateKey= (RSAPrivateKey) keyPair.getPrivate();  
        this.publicKey= (RSAPublicKey) keyPair.getPublic();  
    }  
  
    /** 
     * 从文件输入流中加载公钥 
     * @param in 公钥输入流 
     * @throws Exception 加载公钥时产生的异常 
     */  
    public void loadPublicKey(InputStream in) throws Exception{  
        try {  
            BufferedReader br= new BufferedReader(new InputStreamReader(in));  
            String readLine= null;  
            StringBuilder sb= new StringBuilder();  
            while((readLine= br.readLine())!=null){  
                if(readLine.charAt(0)=='-'){  
                    continue;  
                }else{  
                    sb.append(readLine);  
                    sb.append('\r');  
                }  
            }  
            loadPublicKey(sb.toString());  
        } catch (IOException e) {  
            throw new Exception("公钥数据流读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥输入流为空");  
        }  
    }  
  
  
    /** 
     * 从字符串中加载公钥 
     * @param publicKeyStr 公钥数据字符串 
     * @throws Exception 加载公钥时产生的异常 
     */  
    public void loadPublicKey(String publicKeyStr) throws Exception{  
        try {  
            byte[] buffer = Base64.decodeBase64(publicKeyStr);
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);  
            this.publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("公钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥数据为空");  
        }  
    }  
  
    /** 
     * 从文件中加载私钥 
     * @param keyFileName 私钥文件名 
     * @return 是否成功 
     * @throws Exception  
     */  
    public void loadPrivateKey(InputStream in) throws Exception{  
        try {  
            BufferedReader br= new BufferedReader(new InputStreamReader(in));  
            String readLine= null;  
            StringBuilder sb= new StringBuilder();  
            while((readLine= br.readLine())!=null){  
                if(readLine.charAt(0)=='-'){  
                    continue;  
                }else{  
                    sb.append(readLine);  
                    sb.append('\r');  
                }  
            }  
            loadPrivateKey(sb.toString());  
        } catch (IOException e) {  
            throw new Exception("私钥数据读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥输入流为空");  
        }  
    }  
  
    public void loadPrivateKey(String privateKeyStr) throws Exception{  
        try {  
            byte[] buffer = Base64.decodeBase64(privateKeyStr);
            RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(buffer));  
            RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());  
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            this.privateKey =  (RSAPrivateKey) keyFactory.generatePrivate(rsaPrivKeySpec); 
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("私钥非法");  
        } catch (IOException e) {  
            throw new Exception("私钥数据内容读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥数据为空");  
        }  
    }  
  
    /** 
     * 加密过程 
     * @param publicKey 公钥 
     * @param plainTextData 明文数据 
     * @return 
     * @throws Exception 加密过程中的异常信息 
     */  
    public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{  
        if(publicKey== null){  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher= null;  
        try {  
            cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output= cipher.doFinal(plainTextData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        }catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  
    }  
  
    /** 
     * 解密过程 
     * @param privateKey 私钥 
     * @param cipherData 密文数据 
     * @return 明文 
     * @throws Exception 解密过程中的异常信息 
     */  
    public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception{  
        if (privateKey== null){  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher= null;  
        try {  
            cipher= Cipher.getInstance("RSA/ECB/PKCS1Padding", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output= cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        }catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }         
    }  
  
    /**
     * 解密RSA公钥加密后的base64数据
     * @since 
     * @param encStr
     * @return
     * @throws Exception
     */
    public static String decrypt(String encStr) throws Exception {
        // 还原base64编码数据
        byte[] dec = Base64.decodeBase64(encStr);
        // 解密
        byte[] plainText = getRSAEncrypt().decrypt(rsaEncrypt.getPrivateKey(), dec);

        return new String(plainText);
    }
}
