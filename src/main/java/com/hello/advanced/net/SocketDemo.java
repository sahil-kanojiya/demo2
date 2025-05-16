package com.hello.advanced.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketDemo {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("Hello Server");
            String response = in.readLine();
            System.out.println("Server replied: " + response);
            for (int i = 0; i < 10; i++) {
                Thread.sleep(5000);
                out.println("Hello Server    HeartBeat:" + i);
                System.out.println("Heartbeat sent: " + i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
