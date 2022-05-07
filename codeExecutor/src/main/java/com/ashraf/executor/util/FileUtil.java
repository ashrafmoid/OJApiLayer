package com.ashraf.executor.util;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
public class FileUtil {
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

    public static void WriteToFile(String text, String absoluteFilePath) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(absoluteFilePath));
            writer.write(text);
        } catch (IOException e) {
            log.error("Error while writing to file");
            throw new RuntimeException(e);
        } finally {
            if (Objects.nonNull(writer))
                writer.close();
        }
    }
}
