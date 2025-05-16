package com.hello.advanced.generics;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenericsDemo {

    public static void main(String[] args) {

////        List<Pair<Character, Long>> pairList = List.of(
////                new Pair<Character, Long>('a', 10l),
////                new Pair<Character, Long>('b', 25l),
////                new Pair<Character, Long>('c', 15l)
////        );
////        System.out.println("pairList = " + pairList);
//
//        List<NumberPair<Character, Integer>> pairList = List.of(
//                new NumberPair<>('a', 10),
//                new NumberPair<>('b', 25),
//                new NumberPair<>('c', 15)
//        );
//        System.out.println("pairList = " + pairList);
//        NumberPair<Character, Integer> numberPair = pairList.get(0);
//        Integer value = numberPair.getValue();
////        Integer value = numberPair.<Integer>getValue();   //no need to explicitly specify
//        System.out.println(value);

    }

    static class Pair<KEY, VALUE> {
        private KEY key;
        private VALUE value;

        public KEY getKey() {
            return key;
        }

        public void setKey(KEY key) {
            this.key = key;
        }

        public VALUE getValue() {
            return value;
        }

        public void setValue(VALUE value) {
            this.value = value;
        }

        public Pair(KEY key, VALUE value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }


    static class NumberPair<KEY, VALUE extends Number> {

        private KEY key;
        private VALUE value;

        public KEY getKey() {
            return key;
        }

        public void setKey(KEY key) {
            this.key = key;
        }

        public VALUE getValueDefault() {
            return value;
        }

        public <TYPE> TYPE getValue() {
            return (TYPE) value;
        }

        public void setValue(VALUE value) {
            this.value = value;
        }

        public NumberPair(KEY key, VALUE value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }


}

