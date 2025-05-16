package com.hello.advanced.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;

public class MessageDigestDemo {

    public static void main(String[] args) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        Scanner sc = new Scanner(System.in);
        String password = "helloworld";

        byte[] hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

        System.out.print("Enter the password: ");
        String enteredPassword = sc.nextLine().trim();
        byte[] hashedEnteredPassword = messageDigest.digest(enteredPassword.getBytes(StandardCharsets.UTF_8));

        StringBuilder hashedPasswordStr = new StringBuilder();
        for (byte b : hashedPassword) {
            hashedPasswordStr.append(String.format("%02x", b));
        }

        StringBuilder hashedEnteredPasswordStr = new StringBuilder();
        for (byte b : hashedEnteredPassword) {
            hashedEnteredPasswordStr.append(String.format("%02x", b));
        }

        System.out.println(password.equals(enteredPassword));
        System.out.println(hashedPasswordStr.toString().equals(hashedEnteredPasswordStr.toString()));
        if (Arrays.equals(hashedPassword, hashedEnteredPassword)) {
            System.out.println("✅ Valid password");
        } else {
            System.out.println("❌ Invalid password");
        }
    }
}


