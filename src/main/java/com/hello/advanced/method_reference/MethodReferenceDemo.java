package com.hello.advanced.method_reference;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MethodReferenceDemo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("Hello", "Expression", "Type", "isAnonymousClass", "Lambda", "expression"));
        list.forEach(System.out::println);

        System.out.println("list.stream().filter(s->!s.startsWith(\"e\")).collect(Collectors.toList()) = " + list.stream().filter(s -> !s.startsWith("e")).collect(Collectors.toList()));
        System.out.println("list.stream().filter(s -> s.length() <= 5).collect(Collectors.toList()) = " + list.stream().filter(s -> s.length() <= 5).collect(Collectors.toList()));


    }

}
