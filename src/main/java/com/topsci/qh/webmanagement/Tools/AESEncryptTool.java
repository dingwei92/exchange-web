package com.topsci.qh.webmanagement.Tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by lzw.
 * 16-7-1
 */
@Component
public class AESEncryptTool {
    private static String DES = "AES"; // optional value AES/DES/DESede
    public static Logger logger = LoggerFactory.getLogger(EncryptTool.class);
    private static String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding"; // optional value AES/DES/DESede
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    public static String encrypt(String sSrc, String sKey) {
        byte[] encrypted = new byte[0];
        try {
            if (sKey == null) {
                logger.error("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                logger.error("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, DES);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        } catch (Exception ex) {
            logger.error("加密错误",  ex);
        }
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String decrypt(String sSrc, String sKey) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                logger.error("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                logger.error("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, DES);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;

        } catch (Exception ex) {
            logger.warn("解密错误: {}", sSrc);
        }
        return null;
    }

    public static String getCurrentDayKey() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = calendar.getTime();
        String key = simpleDateFormat.format(date);
        return key + key;
    }

    public static void main(String[] args)
    {
        String test = encrypt("f69c06012a4141e1bc8e87124900e851_0",getCurrentDayKey());
        System.out.println(test);
        test = decrypt("L/skfyUbwS4fp2GxLhEiSTbMYeQaXOkvYAnL9YM3jZOi/e3NNtUyy5dQo5pebKMK",getCurrentDayKey());
        System.out.println(test);
    }
}
