package com.hello.advanced.crypto;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CipherDemo {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        String msg = "hello world";
        System.out.println("msg = " + msg);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedMsgArray = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
        String encryptedMsgStr = Base64.getEncoder().encodeToString(encryptedMsgArray);
        System.out.println("encryptedMsgStr = " + encryptedMsgStr);

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedMsgArray = cipher.doFinal(Base64.getDecoder().decode(encryptedMsgStr));
        System.out.println("decryptedMsgStr = " + new String(decryptedMsgArray));
    }

}
