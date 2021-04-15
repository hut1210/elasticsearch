package com.example.demo.util;


import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密，公私钥、加密串等都是16进制编码
 *
 */
@SuppressWarnings("restriction")
public class RSAUtils {

    private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    /**
     * 公钥加密
     *
     * @param publicKey 公钥
     * @param src       明文
     * @param encode    编码方式
     * @return
     * @throws Exception
     */
    public static String encrypt(PublicKey publicKey, String src, String encode) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] data = src.getBytes(encode);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encodeBase64String(encryptedData);
    }

    /**
     * 私钥解密
     *
     * @param privateKey 私钥
     * @param data       密文
     * @param encode     编码方式
     * @return
     * @throws Exception
     */
    public static String decrypt(PrivateKey privateKey, String data, String encode) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedData = Base64.decodeBase64(data);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, encode);
    }

    /**
     * 得到私钥对象
     *
     * @param key 密钥字符串（经过16进制编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) {
        try {
            byte[] keyBytes = StringUtil.hexStrToBytes(key.trim());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            String info = "getPrivateKey failed: " + key + " | " + e.getMessage();
            logger.error(info, e);
            return null;
        }
    }

    /**
     * 得到公钥对象
     *
     * @param key 密钥字符串（经过16进制编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) {
        try {
            byte[] keyBytes = StringUtil.hexStrToBytes(key.trim());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            String info = "getPublicKey failed: " + key + " | " + e.getMessage();
            logger.error(info, e);
            return null;
        }
    }



    /**
     * 本方法用于产生1024位RSA公私钥对。
     *
     * @return 私钥、公钥
     */
    public static String[] genRSAKeyPair() {
        KeyPairGenerator rsaKeyGen = null;
        KeyPair rsaKeyPair = null;
        try {
            logger.error("Generating a pair of RSA key ... ");
            rsaKeyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = new SecureRandom();
            random.setSeed(("" + System.currentTimeMillis() * Math.random() * Math.random()).getBytes(Charset
                    .forName("UTF-8")));
            rsaKeyGen.initialize(1024, random);
            rsaKeyPair = rsaKeyGen.genKeyPair();
            PublicKey rsaPublic = rsaKeyPair.getPublic();
            PrivateKey rsaPrivate = rsaKeyPair.getPrivate();
            String privateAndPublic[] = new String[2];
            privateAndPublic[0] = StringUtil.bytesToHexStr(rsaPrivate.getEncoded());
            privateAndPublic[1] = StringUtil.bytesToHexStr(rsaPublic.getEncoded());
            logger.info("私钥:" + privateAndPublic[0]);
            logger.error("公钥:" + privateAndPublic[1]);
            logger.error("1024-bit RSA key GENERATED.");

            return privateAndPublic;
        } catch (Exception e) {
            logger.error("genRSAKeyPair error：" + e.getMessage(), e);
            return null;
        }
    }

}
