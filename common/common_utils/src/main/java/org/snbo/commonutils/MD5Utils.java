package org.snbo.commonutils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author sunbo
 * @create 2022-04-06-17:31
 */
public class MD5Utils {

    public static String encrypt(String strSrc) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        //将字符串转换成字节数组
        byte[] bytes = strSrc.getBytes();
        try {
            //通过反射获取MD5加密Digest对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            //将字节数组传入
            md.update(bytes);
            //将字节数组改为加密后的字节数组
            bytes = md.digest();
            //获取字节数组的长度
            int j = bytes.length;
            //新建一个字符数组，长度为字节数组长度的二倍
            char[] chars = new char[j * 2];
            //定义一个变量，用来指定字符数组的下标
            int k = 0;
            //循环字节数组
            for (byte b : bytes) {
                /*
                依次获取字节数组中的值
                将字节右位移4次 然后对十六进制f进行位与运算加密
                */
                chars[k++] = hexChars[b >>> 4 & 0xf];
                //在每个字符的后面，跟上字节对十六进制f的位与运算加密值
                chars[k++] = hexChars[b & 0xf];
            }
            //最后将加密好的字符数组转换为字符串，返回
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);
        }

    }
}

