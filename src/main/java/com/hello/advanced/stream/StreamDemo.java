package com.hello.advanced.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamDemo {

    public static void main(String[] args) {
//        Filter even numbers from a list of integers.
//        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
//        List<Integer> evenNumbers = numbers.stream().filter(num -> num % 2 == 0).collect(Collectors.toList());
//        System.out.println("evenNumbers = " + evenNumbers);

//        Convert a list of strings to uppercase using map
//        List<String> words = List.of("hello", "world");
//        List<String> upperCaseWords = words.stream().map(String::toUpperCase).collect(Collectors.toList());
//        System.out.println("upperCaseWords = " + upperCaseWords);

//        Count how many strings have length > 3.
//        List<String> words = List.of("apple", "banana" , "*");
//        long count = words.stream().filter(str -> str.length() > 3).count();
//        System.out.println("count = " + count);

//        Sort a list of names alphabetically using streams.
//        List<String> words = List.of("apple", "banana", "hello", "world");
//        List<String> sortedList = words.stream().sorted().collect(Collectors.toList());
//        System.out.println("sortedList = " + sortedList);

//        Find the first string that starts with "A" (case-insensitive).
//        List<String> words = List.of("apple", "banana", "hello", "world");
//        List<String> list = words.stream().filter(str -> str.charAt(0) == 'a' || str.charAt(0) == 'A').collect(Collectors.toList());
//        System.out.println("list = " + list);

//        From a list of words, create a list of unique characters using flatMap.
//        List<String> words = List.of("apple", "banana");
//        List<Character> list = words.stream().flatMap(str -> str.chars().mapToObj(c -> (char) c)).distinct().collect(Collectors.toList());
//        System.out.println("list = " + list);

//        Group a list of strings by their length using Collectors.groupingBy().
//        List<String> words = Arrays.asList("cat", "dog", "apple", "bat", "car", "bear", "zebra");
//        Map<Integer, List<String>> map = words.stream().collect(Collectors.groupingBy(word -> word.length()));
//        System.out.println("map = " + map);

//        Count the occurrences of each character in a string using streams.
//        String s = "HELLO_WORLD";
//        Map<Character, Long> map = s.chars().mapToObj(ch -> (char) ch).dropWhile(ch -> !Character.isAlphabetic(ch)).collect(Collectors.groupingBy(Character::charValue,Collectors.counting()));
//        System.out.println("map = " + map);

//        Find the longest string in a list.
//        List<String> words = Arrays.asList("cat", "dog", "apple", "bat", "car", "bear", "zebra", "worlds");
//        Optional<String> max = words.stream().max(Comparator.comparingInt(String::length));
////        Optional<String> max = words.stream().max((str1, str2) -> Integer.compare(str1.length(), str2.length()));
//        System.out.println(max.isPresent() ? max.get() : "not present");

//        Remove duplicate integers and collect them in descending order
//        List<Integer> words = Arrays.asList(1,2,3);
//        List<Integer> descendingList = words.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
//        System.out.println("descendingList = " + descendingList);

//        Given a list of Person objects, group them by age and count how many people are in each group
//        List<Person> personList = new ArrayList<>(List.of(new Person("sahil", 20), new Person("SK1", 20), new Person("SK", 18)));
//        Map<Integer, Long> collect = personList.stream().collect(Collectors.groupingBy(person -> person.age, Collectors.counting()));
//        System.out.println("collect = " + collect);

//        From a list of Employee objects, create a Map<Department, List<Employee>>.
//        List<Employee> employees = Arrays.asList(
//                new Employee("Alice", "HR"),
//                new Employee("Bob", "IT"),
//                new Employee("Charlie", "HR"),
//                new Employee("David", "IT"),
//                new Employee("Eve", "Sales")
//        );
//        Map<String, List<Employee>> list = employees.stream().collect(Collectors.groupingBy(employee -> employee.department));
//        System.out.println("list = " + list);

//        Use partitioningBy to split a list of integers into even and odd.
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
//        Map<Boolean, List<Integer>> collect = numbers.stream().collect(Collectors.partitioningBy(num -> num % 2 == 0));
//        System.out.println(collect);

//        sum of numbers
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        Integer sum = numbers.stream().reduce(0, (n1, n2) -> n1 + n2);
        System.out.println(sum);

    }

    static class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    static class Employee {
        String name;
        String department;

        Employee(String name, String department) {
            this.name = name;
            this.department = department;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
