package com.xula.base.utils;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5算法加密
 * 
 * @author xla
 *
 */
public class Md5Utils {

    /**
     * 使用md5的算法进行加密
     */
    public static String md5(String plainText) {
    	return DigestUtils.md5Hex(plainText);
    }
}
