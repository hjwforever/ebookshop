package com.aruoxi.ebookshop;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(ApplicationTests.class);

    @Test
    public void druidEncrypt() throws Exception {
        //密码明文
        String password = "123456";
        log.info("明文密码: " + password);
        String[] keyPair = ConfigTools.genKeyPair(512);
        //私钥
        String privateKey = keyPair[0];
        //公钥
        String publicKey = keyPair[1];

        //用私钥加密后的密文
        password = ConfigTools.encrypt(privateKey, password);

        log.info("privateKey:" + privateKey);
        log.info("publicKey:" + publicKey);

        log.info("password:" + password);

        String decryptPassword = ConfigTools.decrypt(publicKey, password);
        log.info("解密后:" + decryptPassword);
    }
}