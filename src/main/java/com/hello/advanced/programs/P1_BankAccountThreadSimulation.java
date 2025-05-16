package com.hello.advanced.programs;

//Bank Account Thread Simulation
//        Requirements:
//        Simulate multiple users accessing a shared bank and (deposit/withdraw) simultaneously.
//        Use Thread, Runnable, and synchronized/ReentrantLock for thread safety.
//        Demonstrate usage of ExecutorService.

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class P1_BankAccountThreadSimulation {

    public static void main(String[] args) {
        Random random = new Random();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        BankAccount account = new BankAccount(100000);
        for (int i = 0; i < 25; i++) {
            if (random.nextBoolean()) {
                executorService.submit(() -> account.deposit(random.nextInt(100000)));
            } else {
                executorService.submit(() -> account.withdraw(random.nextInt(100000)));
            }
        }
        executorService.shutdown();
    }
}

class BankAccount {

    private double balance;

    public synchronized void deposit(double amount) {
        balance += amount;
        System.out.println("new balance after amount("+amount+") deposit:" + balance);
    }

    public synchronized void withdraw(double amount) {
        if (amount > balance) {
            throw new RuntimeException("amount(" + amount + ") not available.  \ncurrent balance is:(" + balance + ")");
        }
        balance -= amount;
        System.out.println("new balance after amount("+amount+") withdraw:" + balance);
    }

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getCurrentBalance() {
        return balance;
    }
}