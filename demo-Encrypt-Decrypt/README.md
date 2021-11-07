- `Hutool`官方文档：[加密解密（Hutool-crypto）](https://www.hutool.cn/docs/#/crypto/%E6%A6%82%E8%BF%B0)
- 尚硅谷加密解密原理视频教程：[尚硅谷_Java安全密码学详解](http://www.atguigu.com/download_detail.shtml?v=288)

# 说明

## POM

使用`Hutool`加密解密工具时，引入如下依赖

```xml
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-crypto</artifactId>
    <version>5.7.15</version>
</dependency>
```

## 对称加密与非对称加密

![](https://cdn.maxqiu.com/upload/ba715c0f5acc4772b4c8d6b0359ce7ad.jpg)

# 对称加密

## 加密算法

- 采用单钥密码系统的加密方法，同一个密钥可以同时用作信息的加密和解密，这种加密方法称为对称加密，也称为单密钥加密。
- 常见加密算法
    - `DES(Data Encryption Standard)`：即数据加密标准，是一种使用密钥加密的块算法，1977年被美国联邦政府的国家标准局确定为联邦资料处理标准（FIPS），并授权在非密级政府通信中使用，随后该算法在国际上广泛流传开来。
    - `AES(Advanced Encryption Standard)`：高级加密标准.在密码学中又称Rijndael加密法，是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用。
- 特点
    - 加密速度快, 可以加密大文件
    - 密文可逆, 一旦密钥文件泄漏，就会导致数据暴露
    - 加密后编码表找不到对应字符，出现乱码，一般结合Base64使用

## 加密模式

- `ECB(Electronic codebook)`：电子密码本。需要加密的消息按照块密码的块大小被分为数个块，并对每个块进行独立加密<br>![](https://cdn.maxqiu.com/upload/abf9ffeb919c490ca7c8640f14b37ba5.jpg)
    - 优点：可以并行处理数据
    - 缺点：同样的原文生成同样的密文，不能很好的保护数据
    - 同时加密，原文是一样的，加密出来的密文也是一样的
- `CBC(Cipher-block chaining)`：密码块链接。每个明文块先与前一个密文块进行异或后，再进行加密，每个密文块都依赖于它前面的所有明文块<br>![](https://cdn.maxqiu.com/upload/27e7b31cc349486ca5c47852f374dc7e.jpg)
    - 优点：同样的原文生成的密文不一样
    - 缺点：串行处理数据

## 填充模式

当需要按块处理的数据, 数据长度不符合块处理需求时, 按照一定的方法填充满块长的规则

- `NoPadding`不填充
    - 在`DES`加密算法下, 要求原文长度必须是`8byte`的整数倍
    - 在`AES`加密算法下, 要求原文长度必须是`16byte`的整数倍
- `PKCS5Padding`
    - 数据块的大小为8位, 不够就补足

> Tips：默认情况下, 加密模式和填充模式为：`ECB/PKCS5Padding`。如果使用`CBC`模式，需要增加参数初始化向量`IV`

## `DES`与`AES`示例代码

```java
public class SymmetricCryptoTest {
    @Test
    public void des() {
        String text = "HelloWorld";

        // key：DES模式下，key必须为8位
        String key = "12345678";
        // iv：偏移量，ECB模式不需要，CBC模式下必须为8位
        String iv = "12345678";

        // DES des = new DES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
        DES des = new DES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());

        String encrypt = des.encryptBase64(text);
        System.out.println(encrypt);

        String decrypt = des.decryptStr(encrypt);
        System.out.println(decrypt);
    }

    @Test
    public void aes() {
        String text = "HelloWorld";

        // key：AES模式下，key必须为16位
        String key = "1234567812345678";
        // iv：偏移量，ECB模式不需要，CBC模式下必须为16位
        String iv = "1234567812345678";

        // AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());

        // 加密并进行Base转码
        String encrypt = aes.encryptBase64(text);
        System.out.println(encrypt);

        // 解密为字符串
        String decrypt = aes.decryptStr(encrypt);
        System.out.println(decrypt);
    }
}
```

# 非对称加密

## 简介

- 非对称加密算法又称现代加密算法。
- 非对称加密是计算机通信安全的基石，保证了加密数据不会被破解。
- 与对称加密算法不同，非对称加密算法需要两个密钥：公开密钥`(publickey)`和私有密钥`(privatekey)`
    - 公开密钥和私有密钥是一对
    - 如果用公开密钥对数据进行加密，只有用对应的私有密钥才能解密。
    - 如果用私有密钥对数据进行加密，只有用对应的公开密钥才能解密。
- 因为加密和解密使用的是两个不同的密钥，所以这种算法叫作非对称加密算法。
- 特点
    - 加密和解密使用不同的密钥
    - 处理数据的速度较慢, 因为安全级别高
- 常见算法
    - RSA
    - ECC

## `RSA`示例

```java
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
```

# 摘要加密

## 简介

- 消息摘要`Message Digest`又称为数字摘要`Digital Digest`
- 它是一个唯一对应一个消息或文本的固定长度的值，它由一个单向`Hash`加密函数对消息进行作用而产生
- 使用数字摘要生成的值是不可以篡改的，为了保证文件或者值的安全
- 特点：
    - 无论输入的消息有多长，计算出来的消息摘要的长度总是固定的。例如
        - 用`MD5`算法摘要的消息有128个比特位
        - 用`SHA-1`算法摘要的消息最终有160比特位
    - 只要输入的消息不同，对其进行摘要以后产生的摘要消息也必不相同；但相同的输入必会产生相同的输出
    - 消息摘要是单向、不可逆的
- 常见算法
    - MD5
    - SHA1
    - SHA256
    - SHA512

## `MD5`和`SHA-1`示例

```kava
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
```
