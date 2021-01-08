package com.paic.f2f.license.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 通过此类可以创建私钥库以及公钥库
 */
public class PKCS12Builder {
    private static final Logger LOGGER = LoggerFactory.getLogger(PKCS12Builder.class);

    private String ksPath;

    private String priAlias;

    private String keyPwd;

    private String storePwd;

    private String cerStorePath;

    private String cerAlias;

    private CertAndKeyGen certAndKeyGen;

    private X509Certificate x509Certificate;

    /**
     * 初始化时传入创建密钥库需要的参数
     *
     * @param ksPath 密钥库文件的存储路径，例如：d:/tools/f2f.p12
     * @param priAlas 私钥别名
     * @param keyPwd 私钥密码
     * @param storePwd 密钥库密码
     */
    private PKCS12Builder(String ksPath, String priAlas, String keyPwd, String storePwd) {
        this.ksPath = ksPath;
        this.priAlias = priAlas;
        this.keyPwd = keyPwd;
        this.storePwd = storePwd;
    }

    public static PKCS12Builder build(String ksPath, String priAlas, String keyPwd, String storePwd) {
        return new PKCS12Builder(ksPath, priAlas, keyPwd, storePwd);
    }

    public PKCS12Builder certAndKeyGen() {
        CertAndKeyGen certAndKeyGen = null;
        try {
            certAndKeyGen = new CertAndKeyGen("DSA", "SHA1withDSA");
            certAndKeyGen.generate(1024);
        } catch (Exception e) {
            LOGGER.error("PKCS12Builder->certAndKeyGen exception: {}", e);
        }

        this.certAndKeyGen = certAndKeyGen;

        return this;
    }

    public PKCS12Builder x509Certificate() {
        try {
            this.x509Certificate = certAndKeyGen.getSelfCertificate(new X500Name("CN=www.jryzt.com, OU=OneConnect Technology, O=gamma-f2f, L=SH, ST=SH, C=CN"), (long) 365 * 24 * 3600);
        } catch (CertificateException | InvalidKeyException | SignatureException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
            LOGGER.error("PKCS12Builder->x509Certificate exception: {}", e);
        }

        return this;
    }

    public void genPrivateKeyStore() {
        // 获取PKCS12实例
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            // 新建密钥库时，参数都传入null
            keyStore.load(null, null);
            Key key = certAndKeyGen.getPrivateKey();
            // 证书链，此时只有一个证书
            X509Certificate[] chain = new X509Certificate[1];
            chain[0] = x509Certificate;

            // 私钥存入密钥库
            keyStore.setKeyEntry(priAlias, key, keyPwd.toCharArray(), chain);

            // 生成密钥库文件
            keyStore.store(new FileOutputStream(ksPath), storePwd.toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            LOGGER.error("PKCS12Builder->genPrivateKeyStore exception: {}", e);
        }
    }

    public void exportCer() {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null);
            CertAndKeyGen certAndKeyGen = new CertAndKeyGen("DSA", "SHA1withDSA");
            // keysize为1024
            certAndKeyGen.generate(1024);
            keyStore.setCertificateEntry(cerAlias, x509Certificate);
            keyStore.store(new FileOutputStream(cerStorePath), storePwd.toCharArray());
        } catch (Exception e) {
            LOGGER.error("PKCS12Builder->exportCer exception: {}", e);
        }
    }

    /**
     * 设置证书的保存路径，例如：d:/tools/cerfile.cer
     *
     * @param cerStorePath
     * @return
     */
    public PKCS12Builder cerStorePath(String cerStorePath) {
        this.cerStorePath = cerStorePath;

        return this;
    }

    public PKCS12Builder cerAlias(String cerAlias) {
        this.cerAlias = cerAlias;

        return this;
    }

    public static void main(String[] args) {
        PKCS12Builder builder = PKCS12Builder.build("d:/tools/f2fkeypair.p12", "f2fkeypair", "f2fkp02er", "f2fsp09av");
        builder.certAndKeyGen().x509Certificate().genPrivateKeyStore();
        builder.cerAlias("f2fpubkey").cerStorePath("d:/tools/f2fpubkey.p12").exportCer();
    }
}
