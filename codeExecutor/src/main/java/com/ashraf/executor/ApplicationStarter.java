package com.ashraf.executor;

import com.ashraf.executor.enums.ProgrammingLanguage;
import com.ashraf.executor.factory.CodeHandlerFactory;
import com.ashraf.executor.handler.CodeHandler;
import com.ashraf.executor.model.CodeExecutionRequest;

import java.io.IOException;

public class ApplicationStarter {

    public static void main(String[] args) throws IOException, InterruptedException {
        final String language = ProgrammingLanguage.C.getValue();
        final CodeHandler codeHandler = CodeHandlerFactory.getCodeHandler(language);
        codeHandler.executeCode(CodeExecutionRequest.builder()
                .codeFilePath("/usr/local/submission/58/Main.c")
                        .testFilePath("/usr/local/submission/58/test.txt")
                .build());
        System.out.println("executed class CodeExecutionService");
    }
}
