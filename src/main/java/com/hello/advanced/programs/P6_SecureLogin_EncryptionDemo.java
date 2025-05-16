package com.hello.advanced.programs;

//Secure Login & Encryption Demo
//        Requirements:
//        Accept login credentials from user input and hash passwords using MessageDigest.
//        Encrypt sensitive data using AES (javax.crypto.Cipher).
//        Validate login and simulate secured user authentication.

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class P6_SecureLogin_EncryptionDemo {

    public static void main(String[] args) {
        SecurityModule securityModule = new SecurityModule();
        Scanner scanner = new Scanner(System.in);
        loop:
        while (true) {
            System.out.println(
                    "Login 1.\n" +
                            "Register 2.\n" +
                            "exit press 0"
            );
            System.out.print("Enter num:");
            int num = scanner.nextInt();
            scanner.nextLine();
            switch (num) {
                case 1 -> {
                    System.out.print("Enter username:");
                    String username = scanner.nextLine();
                    System.out.print("Enter password:");
                    String password = scanner.nextLine();
                    Optional<User> user = securityModule.login(username, password);
                    user.ifPresent(u -> System.out.println("User:" + u));
                    user.ifPresent(u -> securityModule.printUser(u));
                }
                case 2 -> {
                    System.out.print("Enter username:");
                    String username = scanner.nextLine();
                    System.out.print("Enter password:");
                    String password = scanner.nextLine();
                    System.out.print("Enter address:");
                    String address = scanner.nextLine();
                    System.out.print("Enter mobileNo:");
                    String mobileNo = scanner.nextLine();
                    securityModule.register(username, password, address, mobileNo);
                }
                case 0 -> {
                    break loop;
                }
                default -> System.out.println("Invalid input.");
            }
            System.out.println();
        }
    }
}

class SecurityModule {
    private List<User> userList;
    public SecretKey secretKey;

    public SecurityModule() {
        this.userList = new ArrayList<>();
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            this.secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void register(String username, String password, String address, String mobileNo) {
        try {
            if (userList.stream().anyMatch(u -> u.getUsername().equals(username)))
                throw new Exception("user with username:" + username + " already exists");
            User user = new User();
            user.setUsername(username);
            user.setPassword(this.hashString(password));
            user.setAddress(this.encryptString(address));
            user.setMobileNo(this.encryptString(mobileNo));
            userList.add(user);
            System.out.println("User with username:" + username + " registered");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<User> login(String username, String password) {
        try {
            User user = userList.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElseThrow(() -> new Exception("user with username:" + username + " doesn't exists"));
            if (!user.getPassword().equals(this.hashString(password)))
                throw new Exception("invalid password");
            System.out.println("User with username:" + username + " login");
            return Optional.of(user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void printUser(User user) {
        try {
            System.out.println("User{" +
                    "username='" + user.getUsername() + '\'' +
                    ", address='" + this.decryptString(user.getAddress()) + '\'' +
                    ", mobileNo='" + this.decryptString(user.getMobileNo()) + '\'' +
                    '}');
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String hashString(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] hashedBytes = messageDigest.digest(strBytes);
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes)
            hexString.append(String.format("%02x", b));
        return hexString.toString();
    }

    public String encryptString(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = cipher.doFinal(strBytes);
        String encodedString = Base64.getEncoder().encodeToString(encryptedBytes);
        return encodedString;
    }

    public String decryptString(String encodedString) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}

class User {
    private String username;
    private String password;
    private String address;
    private String mobileNo;

    public User() {
    }

    public User(int id, String username, String password, String address, String mobileNo) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.mobileNo = mobileNo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }
}