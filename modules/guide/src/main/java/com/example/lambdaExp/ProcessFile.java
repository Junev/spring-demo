package com.example.lambdaExp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProcessFile {
    public static void main(String[] args) throws IOException {
        String s = processFile1();
        System.out.println(s);


        String s1 = LambdaProcessFile.processFile((bufferedReader) -> {
            return bufferedReader.readLine() + bufferedReader.readLine();
        });
        System.out.println(s1);

    }

    public static String processFile1() {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(ProcessFile.class.getClassLoader()
                             .getResource(
                                     "example.txt")
                             .getPath()))) {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
