package com.hello.basic.programs;

import java.util.Scanner;

public class P6_StringAnalyzerTool {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("countWords(\"hello w word    hello\") = " + countWords("  hello w word    hello      "));
        System.out.print("Enter the string:");
        String str = scanner.nextLine();
        System.out.println("countVowels(str) = " + countVowels(str));
        System.out.println("countWords(str) = " + countWords(str));
        System.out.println("getReverseString(str) = " + getReverseString(str));
        System.out.println("getLongestWord(str) = " + getLongestWord(str));
    }

    public static int countVowels(String str) {
        str = str.toLowerCase();
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count++;
            }
        }
        return count;
    }

    public static int countWords(String str) {
        int count = 0;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == ' ' && str.charAt(i - 1) != ' ') {
                count++;
            }
        }
        if (str.charAt(str.length() - 1) != ' ') {
            count++;
        }
        return count;
    }

    public static String getReverseString(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public static String getLongestWord(String str) {
        String max = "";
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == ' ') {
                max = current.length() > max.length() ? current.toString() : max;
                current.delete(0, current.length());
            } else {
                current.append(ch);
            }
        }
        max = current.length() > max.length() ? current.toString() : max;
        current.delete(0, current.length());
        return max;
    }

}
