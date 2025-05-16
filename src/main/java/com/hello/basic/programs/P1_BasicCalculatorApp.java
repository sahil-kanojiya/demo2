package com.hello.basic.programs;

import java.util.Scanner;

public class P1_BasicCalculatorApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("enter the num1:");
            float num1 = scanner.nextFloat();

            System.out.print("enter the num2:");
            float num2 = scanner.nextFloat();

            System.out.print("enter the operator:");
            String operator = scanner.next().trim();

            switch (operator) {
                case "+" -> System.out.println("The Sum of num1 and num2 is: " + (num1 + num2));
                case "-" -> System.out.println("The Sum of num1 and num2 is: " + (num1 - num2));
                case "*" -> System.out.println("The Sum of num1 and num2 is: " + (num1 * num2));
                case "/" -> {
                    if (num2 == 0) {
                        System.out.println("num2 is zero divide by zero ex");
                        continue;
                    }
                    System.out.println("The Sum of num1 and num2 is: " + (num1 / num2));
                }
                default -> System.out.println("Invalid operator");
            }

            System.out.print("enter y to retry:");
            if (!scanner.next().equals("y")) {
                break;
            }
        }
    }

}