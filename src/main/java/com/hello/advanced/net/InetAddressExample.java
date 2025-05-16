package com.hello.advanced.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressExample {

    public static void main(String[] args) throws IOException {
//        InetAddress address = InetAddress.getByName("www.example.com");
//        InetAddress address = InetAddress.getByName("www.google.com");
//        InetAddress address = InetAddress.getByName("www.apple.com");
//        System.out.println("Host Name: " + address.getHostName());
//        System.out.println("IP Address: " + address.getHostAddress());

        InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
        for (InetAddress addr : addresses) {
            System.out.println("Google IP: " + addr.getHostAddress());
        }

        InetAddress localAddress = InetAddress.getLocalHost();
        System.out.println("Local Host: " + localAddress.getHostName());
    }

}
