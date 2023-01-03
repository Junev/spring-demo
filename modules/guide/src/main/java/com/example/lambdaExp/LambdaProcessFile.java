package com.example.lambdaExp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LambdaProcessFile {
    public static String processFile(BufferedReaderProcessor brp) throws IOException {
        try (BufferedReader bf = new
                BufferedReader(new FileReader(LambdaProcessFile.class.getClassLoader()
                .getResource("example.txt")
                .getPath()))) {
            return brp.process(bf);
        }
    }
}
