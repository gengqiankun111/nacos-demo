package com.example.jwt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;
 
public class Md5 {
  /**
   * MD5加密
   * @param content  加密内容
   * @return 加密结果
   */
  public static  String getMd5Str(String content){
    return DigestUtils.md5Hex(content);
  }

  /**
   * 获取md5加密后的token
   */
  public static String encoder(String userName, String password) {
    try {
      return encoderByMd5(password);
    } catch (Exception e) {
      return "1";
    }
  }
  
   /**利用MD5进行加密*/
  public static String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    //确定计算方法
    MessageDigest md5=MessageDigest.getInstance("MD5");
    BASE64Encoder base64en = new BASE64Encoder();
    //加密后的字符串
    String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
    return newstr;
  }
   
  /**判断用户密码是否正确
   *newpasswd 用户输入的密码
   *oldpasswd 正确密码*/
  public boolean checkpassword(String newpasswd,String oldpasswd) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    if(encoderByMd5(newpasswd).equals(oldpasswd))
      return true;
    else
      return false;
  }
}