package de.cookiebook.restservice.config;

import javassist.bytecode.ByteArray;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Component
public class CookieCipher {
    private String keyValue = System.getenv("LIVE_POLL_JWT_COOKIE_KEY_VALUE");
    private SecretKeySpec secretKey;
    private byte[] key;

    void init(){
        try {
            key = MessageDigest.getInstance("SHA-1").digest(keyValue.getBytes(StandardCharsets.UTF_8));
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    String encrypt(String stringToEncrypt) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(stringToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error in cookieCipher");
        }
    }


    String decrypt(String strToDecrypt) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error in cookieCipher");
        }
    }
}
