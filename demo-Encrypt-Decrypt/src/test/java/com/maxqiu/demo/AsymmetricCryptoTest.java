package com.maxqiu.demo;

import java.security.KeyPair;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * 非对称加密
 *
 * RSA
 *
 * @author Max_Qiu
 */
public class AsymmetricCryptoTest {
    /**
     * 私钥和公钥
     */
    private static String privateKey;
    private static String publicKey;

    private static String encryptByPublic;

    /**
     * 生成公私钥
     */
    @BeforeAll
    public static void genKey() {
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        privateKey = Base64.encode(pair.getPrivate().getEncoded());
        System.out.println("私钥\t" + privateKey);
        publicKey = Base64.encode(pair.getPublic().getEncoded());
        System.out.println("公钥\t" + publicKey);
    }

    @Test
    public void test() {
        String text = "HelloWorld";

        // 初始化对象
        // 第一个参数为加密算法，不传默认为 RSA/ECB/PKCS1Padding
        // 第二个参数为私钥（Base64字符串）
        // 第三个参数为公钥（Base64字符串）
        RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), privateKey, publicKey);

        // 公钥加密，私钥解密
        String encryptByPublic = rsa.encryptBase64(text, KeyType.PublicKey);
        System.out.println("公钥加密\t" + encryptByPublic);
        String decryptByPrivate = rsa.decryptStr(encryptByPublic, KeyType.PrivateKey);
        System.out.println("私钥解密\t" + decryptByPrivate);

        // 私钥加密，公钥解密
        String encryptByPrivate = rsa.encryptBase64(text, KeyType.PrivateKey);
        System.out.println("私钥加密\t" + encryptByPrivate);
        String decryptByPublic = rsa.decryptStr(encryptByPrivate, KeyType.PublicKey);
        System.out.println("公钥解密\t" + decryptByPublic);

        // 此处赋值为下一个测试用
        AsymmetricCryptoTest.encryptByPublic = encryptByPublic;
    }

    /**
     * 仅使用私钥解密公钥加密后的密文
     */
    @Test
    public void test2() {
        // 传入私钥以及对应的算法
        RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), privateKey, null);

        // 私钥解密公钥加密后的密文
        String decrypt = rsa.decryptStr(encryptByPublic, KeyType.PrivateKey);
        System.out.println(decrypt);
    }
}
