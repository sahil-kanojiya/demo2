package com.hello.advanced.design_pattern.behavioral;

public class StrategyPattern {

    public static void main(String[] args) {
        PaymentStrategy paymentStrategy = new CashPayment();
        ShoppingCart shoppingCart = new ShoppingCart(paymentStrategy);
        shoppingCart.checkout(1000);
    }
}

interface PaymentStrategy {
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    private int cardNo;

    public CreditCardPayment(int cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public void pay(int amount) {
        System.out.println("credit card no:" + cardNo + "   pay:" + amount);
    }
}

class PaypalPayment implements PaymentStrategy {
    private String email;

    public PaypalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(int amount) {
        System.out.println("paypal   email:" + email + "   pay:" + amount);
    }
}

class CashPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Cash    pay:" + amount);
    }
}

class ShoppingCart {

    private PaymentStrategy paymentStrategy;

    public ShoppingCart(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void setPaymentStrategya(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(int amount) {
        paymentStrategy.pay(amount);
    }
}


