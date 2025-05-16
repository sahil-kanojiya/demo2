package com.hello.advanced.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaExprDemo {

    public static void main(String[] args) {
        //Lambda-generated classes are not considered anonymous classes by Java
        Supplier<Integer> sup = () -> 42;    //synthetic classes
        System.out.println("Is synthetic class? " + sup.getClass().isSynthetic());      //true
        System.out.println("Is anonymous class? " + sup.getClass().isAnonymousClass());    //false
        System.out.println("Class name: " + sup.getClass().getName());
        Supplier<Integer> sup2 = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 42;
            }
        };
        System.out.println("Is anonymous class? " + sup2.getClass().isAnonymousClass());
        System.out.println("Class name: " + sup2.getClass().getName());


        List<Person> list = new ArrayList<>(List.of(
                new Person("sahil", 20),
                new Person("sk", 18),
                new Person("RB", 19)
        ));

        System.out.println("list = " + list);
        list.sort((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));
        System.out.println("list = " + list);

        Comparator<Person> compareByName = (p1, p2) -> p1.getName().compareTo(p2.getName());
        Comparator<Person> compareByName2 = Comparator.comparing(Person::getName);

        list.sort(compareByName);
        System.out.println("list = " + list);


//        all intermediate operatins are lazy    and terminal op are eager
        Stream<String> stream = Stream.of("a", "b", "c")
                .map(s -> {
                    System.out.println("Mapping: " + s);
                    return s.toUpperCase();
                });
//        lazy only executed when eager op triggers stream then all prev lazy op cleared

//        List<String> collect = stream.collect(Collectors.toList());
//        System.out.println(collect);


    }
}


class Person {
    private String name;
    private Integer age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}