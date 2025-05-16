package com.hello.basic.programs;

public class P4_ShapeAreaCalculator {

    public static void main(String[] args) {

    }

}


interface Shape {

    double calculateArea(float num);

}

class Circle implements Shape {

    @Override
    public double calculateArea(float num) {
        return Math.PI * num * num;
    }

}

class Rectangle implements Shape {

    @Override
    public double calculateArea(float num) {
        return num * num;
    }

    public double calculateArea(float num1, float num2) {
        return num1 * num2;
    }

}