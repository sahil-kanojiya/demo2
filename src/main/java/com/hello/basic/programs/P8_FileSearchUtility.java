package com.hello.basic.programs;

import java.io.*;
import java.util.Scanner;

public class P8_FileSearchUtility {

    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./t1.txt"));

            System.out.println("start writing text here (enter x in new line to exit):");
            while (true) {
                String line = scanner.nextLine();
                if (line.equals("x")) {
                    break;
                } else {
                    bufferedWriter.append(line);
                }
            }
            bufferedWriter.close();

            System.out.print("enter the keyword:");
            String keyword = scanner.nextLine();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("./t1.txt"));
            String line;
            for (int i = 0; ((line = bufferedReader.readLine()) != null); i++) {
                int index = line.indexOf(keyword);
                if (index != -1) {
                    System.out.println("keyword available at line:" + i + " index:" + index);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}