package com.hello.advanced.security;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public class SignatureDemo {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String message = "hello this is a private message";

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(keyPair.getPrivate());

        signature.update(message.getBytes(StandardCharsets.UTF_8));

        byte[] signedMessage = signature.sign();
        System.out.println("sign :" + Base64.getEncoder().encodeToString(signedMessage));

        signature.initVerify(keyPair.getPublic());
        signature.update(message.getBytes(StandardCharsets.UTF_8));
        System.out.println("Verify :"+signature.verify(signedMessage));
    }

}
