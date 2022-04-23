package com.ashraf.ojapilayer.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
public class FileUtil {
    public static String getFileAsString(String filepath) throws IOException {
        File file = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String result = "";

        // Declaring a string variable
        String st;
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null) {
            result = result.concat(st + "\n");
        }
        return result;
    }
    public static boolean areFileContentsSame(File file1, File file2) {
        try {
            InputStream inputStream1 = new FileInputStream(file1);
            InputStream inputStream2 = new FileInputStream(file2);
            return IOUtils.contentEquals(inputStream1, inputStream2);
        } catch (IOException e) {
            throw new RuntimeException("Exception while comparing files!!", e);
        }
    }
}
