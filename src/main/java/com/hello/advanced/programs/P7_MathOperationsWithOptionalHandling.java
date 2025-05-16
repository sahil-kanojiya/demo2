package com.hello.advanced.programs;

//Math Operations with Optional Handling
//        Requirements:
//        Create functional interfaces for arithmetic operations like add, subtract.
//        Use Optional to avoid NullPointerException for missing inputs.
//        Demonstrate Optional.of(), Optional.empty(), orElse(), etc.

import java.util.Optional;

public class P7_MathOperationsWithOptionalHandling {

    public static void main(String[] args) {
        Sum<Integer> sum = (n1, n2) -> n1.orElse(0) + n2.orElse(0);
        Subtract<Integer> subtract = (n1, n2) -> n1.orElse(0) - n2.orElse(0);
        System.out.println("sum.execute(Optional.of(10),Optional.of(15)) = " + sum.execute(Optional.of(10), Optional.of(15)));
        System.out.println("subtract.execute(Optional.empty(),Optional.of(5)) = " + subtract.execute(Optional.empty(), Optional.of(5)));

        SumOp<Integer> sumOp = (n1, n2) -> Optional.of(n1.orElse(0) + n2.orElse(0));
        SubtractOp<Integer> subtractOp = (n1, n2) -> Optional.of(n1.orElse(0) - n2.orElse(0));
        System.out.println("subtractOp.execute(sumOp.execute(Optional.of(1000), Optional.of(2000)), sumOp.execute(Optional.of(500), Optional.of(250))).get() = " + subtractOp.execute(sumOp.execute(Optional.of(1000), Optional.of(2000)), sumOp.execute(Optional.of(500), Optional.of(250))).get());
        System.out.println("subtractOp.execute(sumOp.execute(Optional.empty(), Optional.of(2000)), sumOp.execute(Optional.of(500), Optional.of(250))).get() = " + subtractOp.execute(sumOp.execute(Optional.empty(), Optional.of(2000)), sumOp.execute(Optional.of(500), Optional.of(250))).get());
    }

}

interface Operation<T> {
    T execute(Optional<T> n1, Optional<T> n2);
}

interface Sum<T> extends Operation<T> {
    T execute(Optional<T> n1, Optional<T> n2);
}

interface Subtract<T> extends Operation<T> {
    T execute(Optional<T> n1, Optional<T> n2);
}

interface SumOp<T> {
    Optional<T> execute(Optional<T> n1, Optional<T> n2);
}

interface SubtractOp<T> {
    Optional<T> execute(Optional<T> n1, Optional<T> n2);
}