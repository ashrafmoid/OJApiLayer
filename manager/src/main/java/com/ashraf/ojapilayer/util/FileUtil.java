package com.ashraf.ojapilayer.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
public class FileUtil {
    public static String getFileAsString(String filepath) throws IOException {
        if (!Files.exists(Path.of(filepath))) {
            return "";
        }
        File file = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String result = "";

        // Declaring a string variable
        String st;
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null) {
            if (!result.isEmpty()) result = result.concat("\n");
            result = result.concat(st);
        }
        return result;
    }
    public static boolean areFileContentsSame(File file1, File file2) {
        try {
            Reader reader1 = new BufferedReader(new FileReader(file1));
            Reader reader2 = new BufferedReader(new FileReader(file2));
            return IOUtils.contentEqualsIgnoreEOL(reader1, reader2);
        } catch (IOException e) {
            throw new RuntimeException("Exception while comparing files!!", e);
        }
    }
}
