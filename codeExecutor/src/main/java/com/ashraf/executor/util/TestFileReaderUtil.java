package com.ashraf.executor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestFileReaderUtil {
    public static String getTestFileAsString(String filepath) throws IOException {
        File file = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String result = "";

        // Declaring a string variable
        String st;
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null) {
            result.concat(st + "\n");
        }
        return result;
    }
}
