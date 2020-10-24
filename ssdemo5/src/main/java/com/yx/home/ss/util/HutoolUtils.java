package com.yx.home.ss.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import javax.crypto.SecretKey;

public class HutoolUtils {
    public static String encryptWithSM4Base64(String key, String data) {
        System.out.println(key);

        SymmetricCrypto symmetricCrypto = SmUtil.sm4(Base64.decode(key));

        return symmetricCrypto.encryptBase64(data);
    }

    public static String encryptWithSM4Hex(String key, String data) {
        System.out.println(key);

        SymmetricCrypto symmetricCrypto = SmUtil.sm4(HexUtil.decodeHex(key));

        return symmetricCrypto.encryptHex(data);
    }

    public static void main(String[] args) {
        SecretKey key = SecureUtil.generateKey("SM4");
        String encryptBase64 = HutoolUtils.encryptWithSM4Base64(Base64.encode(key.getEncoded()), "1234567890");
        System.out.println(encryptBase64);

        String encryptHex = HutoolUtils.encryptWithSM4Hex(HexUtil.encodeHexStr(key.getEncoded()), "1234567890");
        System.out.println(encryptHex);
    }
}
