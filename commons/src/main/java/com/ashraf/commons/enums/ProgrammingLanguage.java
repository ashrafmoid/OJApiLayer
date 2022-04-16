package com.ashraf.commons.enums;

public enum ProgrammingLanguage {
    C("c"),
    CPP("cpp"),
    JAVA("java");

    private final String value;

    ProgrammingLanguage(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
