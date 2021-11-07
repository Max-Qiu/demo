package com.maxqiu.demo;

import org.junit.jupiter.api.Test;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;

/**
 * 摘要加密
 *
 * MD5 SHA-1
 *
 * 其他 MD2 SHA-256 SHA-384 SHA-512 类似
 *
 * @author Max_Qiu
 */
public class DigesterTest {
    @Test
    public void md5() {
        String text = "HelloWorld";

        // 第一种：创建Digester对象，执行加密
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String digestHex = md5.digestHex(text);
        System.out.println(digestHex);

        // 第二种：执行使用DigestUtil
        String md5Hex = DigestUtil.md5Hex(text);
        System.out.println(md5Hex);
    }

    @Test
    public void sha1() {
        String text = "HelloWorld";

        // 第一种：创建Digester对象，执行加密
        Digester md5 = new Digester(DigestAlgorithm.SHA1);
        String digestHex = md5.digestHex(text);
        System.out.println(digestHex);

        // 第二种：执行使用DigestUtil
        String md5Hex = DigestUtil.sha1Hex(text);
        System.out.println(md5Hex);
    }
}
