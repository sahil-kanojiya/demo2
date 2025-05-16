package com.hello.basic.programs;

public class P2_BankAccountManagement {

    public static void main(String[] args) {

        BankAccount account = new BankAccount(854798545l, "Sahil Kanojiya", 10000);

        account.deposit(20000);

        try {
            account.withdraw(50000);
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        try {
            account.withdraw(10000);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}

class BankAccount {

    private long AccountNumber;
    private String holderName;
    private double balance;

    public BankAccount(long accountNumber, String holderName, double balance) {
        AccountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("amount deposited: " + amount);
    }

    public double withdraw(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("your balance is lower then amount");
        }
        balance -= amount;
        System.out.println();
        System.out.println("amount withdrawn: " + amount);
        return amount;
    }

    public long getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
