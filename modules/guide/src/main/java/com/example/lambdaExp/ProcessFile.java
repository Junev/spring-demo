package com.example.lambdaExp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProcessFile {
    public static void main(String[] args) throws IOException {
        String s = processFile1();
        System.out.println(s);


        String s1 = LambdaProcessFile.processFile((bufferedReader) -> {
            return bufferedReader.readLine() + bufferedReader.readLine();
        });
        System.out.println(s1);

        List<String> l = Arrays.asList("a", "c", "b", "d");
        List<String> b = l.stream().filter(c -> {
            System.out.println("filtering: " + c);
            return c.charAt(0) > 'a';
        }).map(c -> {
            System.out.println("mapping: " + c);
            return c + c;
        }).limit(3).collect(Collectors.toList());
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
