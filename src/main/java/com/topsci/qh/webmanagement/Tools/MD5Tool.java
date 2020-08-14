package com.topsci.qh.webmanagement.Tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by lzw.
 * 16-6-21
 */

@Component
public class MD5Tool {
    private Logger log = LoggerFactory.getLogger(MD5Tool.class);
    /**
     * 获取加密后的字符串
     * @param password
     * @return
     */
    public String getDigestPassword(String password) {
        String digestPassword = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            digestPassword= toHexString(md5.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            log.warn("转换MD5密码出错！");
        }
        return digestPassword;
    }

    /**byte 转换为字符串*/
    private static String toHexString(byte data[]) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        for (int i = 0; i < len; i++) {
            int b = data[i];
            if (b < 0) {
                b += 256;
            }
            String s = "";
            s = Integer.toHexString(b).toLowerCase();
            if (s.length() == 1) {
                sb.append('0');
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
