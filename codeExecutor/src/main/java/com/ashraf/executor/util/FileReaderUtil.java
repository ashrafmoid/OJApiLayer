package com.ashraf.executor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtil {
    public static List<String> getFileAsString(String filepath) throws IOException {
        File file = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> result = new ArrayList<>();

        // Declaring a string variable
        String st;
        String line = "";
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null) {
            if (st.isEmpty()) {
                result.add(line);
                line = "";
            } else {
                line = line.concat(st + "\n");
            }
        }
        if(!line.isEmpty()) {
            result.add(line);
        }
        return result;
    }
}
