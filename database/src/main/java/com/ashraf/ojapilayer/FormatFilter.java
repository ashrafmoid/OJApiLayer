package com.ashraf.ojapilayer;

import liquibase.changelog.IncludeAllFilter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatFilter implements IncludeAllFilter {

    @Override
    public boolean include(String s) {
        String[] directories = s.split("/");
        String fileName = directories[directories.length - 1];
        if (fileName == null || fileName.length() == 0) {
            return false;
        }
        if (!fileName.matches("\\d{12}-.*.xml$")) {
            return false;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        try {
            LocalDateTime.parse(fileName.split("-")[0], dtf);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
