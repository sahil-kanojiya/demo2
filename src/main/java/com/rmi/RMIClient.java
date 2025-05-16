package com.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) {
        try {
            String serverIp = "192.168.0.84";
            Registry registry = LocateRegistry.getRegistry(serverIp, 1099);
            HelloService service = (HelloService) registry.lookup("HelloService");
            String response = service.sayHello();
            System.out.println("Received from server: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

interface HelloService extends Remote {
    String sayHello() throws RemoteException;
}
