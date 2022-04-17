package com.ashraf.executor;

import com.ashraf.executor.enums.ProgrammingLanguage;
import com.ashraf.executor.factory.CodeHandlerFactory;
import com.ashraf.executor.handler.CodeHandler;

public class ApplicationStarter {

    public static void main(String[] args) throws InterruptedException {
        final String language = ProgrammingLanguage.C.getValue();
        final CodeHandler codeHandler = CodeHandlerFactory.getCodeHandler(language);
        codeHandler.executeCode(null);
        System.out.println("executed class CodeExecutionService");
        Thread.sleep(50000);
    }
}
