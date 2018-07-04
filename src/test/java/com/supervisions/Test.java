package com.supervisions;

import com.supervisions.common.utils.RSAUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * Created by humf.需要依赖 commons-codec 包
 */
public class Test {
    public static void main (String[] args) throws Exception {
        Map<String, String> keyMap = RSAUtils.createKeys(1024);
        String  publicKey = keyMap.get("publicKey");
        String  privateKey = keyMap.get("privateKey");
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);

        String str = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuk0047sm-nyR-3dtDhQztyiFB64Yb5A2HrCQSTOVgqv-fKlB_lf_3Tuo5pToyr3udn7bwRAjX4eyyriaXo22HF9E02HwjwjWKMGArjhTRGpVdnvh8qqady97Jdf5AsHYN0wEBZHoNiWa8WCG5BGnhH32dXvIIz1RgGpXsSyWrrwIDAQAB";
        System.out.println("\r明文：\r\n" + str);
        System.out.println("\r明文大小：\r\n" + str.getBytes().length);
        String encodedData = RSAUtils.publicEncrypt(str, RSAUtils.getPublicKey(publicKey));
        System.out.println("公钥加密密文：\r\n" + encodedData);
        String decodedData = RSAUtils.privateDecrypt(encodedData, RSAUtils.getPrivateKey(privateKey));
        System.out.println("私钥解密后文字: \r\n" + decodedData);
        String encodedData1 = RSAUtils.privateEncrypt(str, RSAUtils.getPrivateKey(privateKey));
        System.out.println("私钥加密密文：\r\n" + encodedData1);
        String decodedData1 = RSAUtils.publicDecrypt(encodedData1, RSAUtils.getPublicKey(publicKey));
        System.out.println("公钥解密后文字: \r\n" + decodedData1);


    }
}
