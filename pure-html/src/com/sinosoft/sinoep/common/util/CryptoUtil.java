package com.sinosoft.sinoep.common.util;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 加密解密工具类
 * @author 李利广
 * @Date 2019年05月21日 08:46:44
 */
public class CryptoUtil {

    private static Logger log = LoggerFactory.getLogger(CryptoUtil.class);

    private static byte[] SECRET_KEY = "0123456789abcdef".getBytes();

    public CryptoUtil() {
    }

    public static byte[] getKey() {
        return SECRET_KEY;
    }

    public static void setKey(String secretKey) {
        try {
            SECRET_KEY = secretKey.getBytes("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("设置KEY出错", e);
        }
    }

    /**
     * TODO AES加密
     * @author 李利广
     * @Date 2019年05月20日 21:38:48
     * @param enStr
     * @return java.lang.String
     */
    public static String encryptStr(String enStr) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, SECRET_KEY);
        return new String(aes.encryptBase64(enStr));
    }

    /**
     * TODO AES解密
     * @author 李利广
     * @Date 2019年05月20日 21:39:00
     * @param enStr
     * @return java.lang.String
     */
    public static String decryptStr(String enStr) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, SECRET_KEY);
        return aes.decryptStr(enStr);
    }

    public static void main(String[] args) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, SECRET_KEY);
        System.out.println(aes.encryptBase64("tjxm"));
    }

}
