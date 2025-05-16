package com.hello.advanced.design_pattern.creational;

public class BuilderPattern {

    public static void main(String[] args) {

        Car car = new Car.CarBuilder().setColor("red").setEngine("V12").setWheels(4).build();

    }

    // Product
    public static class Car {
        private String engine;
        int wheels;
        private String color;

//         Private constructor to enforce building through the Builder
        private Car(CarBuilder builder) {
            this.engine = builder.engine;
            this.wheels = builder.wheels;
            this.color = builder.color;
        }

        @Override
        public String toString() {
            return "Car with " + engine + " engine, " + wheels + " wheels, color " + color;
        }

        // Builder Class
        public static class CarBuilder {
            private String engine;
            private int wheels;
            private String color;

            public CarBuilder setEngine(String engine) {
                this.engine = engine;
                return this;
            }

            public CarBuilder setWheels(int wheels) {
                this.wheels = wheels;
                return this;
            }

            public CarBuilder setColor(String color) {
                this.color = color;
                return this;
            }

            public Car build() {
                return new Car(this);
            }
        }
    }
}


