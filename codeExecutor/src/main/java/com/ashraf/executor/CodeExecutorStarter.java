package com.ashraf.executor;

import com.ashraf.executor.config.SpringConfig;
import com.ashraf.executor.factory.CodeHandlerFactory;
import com.ashraf.executor.handler.CodeHandler;
import com.ashraf.executor.model.CodeExecutionRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@Log4j2
public class CodeExecutorStarter {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        CodeHandlerFactory codeHandlerFactory = context.getBean(CodeHandlerFactory.class);
        final String submissionFolder = "/usr/local/submission/" + args[0];
        final String language = args[1];
        final Integer timeLimit = Integer.valueOf(args[2]);
        System.out.printf("submission folder %s and language %s%n", submissionFolder , language);
        final CodeHandler codeHandler = codeHandlerFactory.getCodeHandler(language);
        codeHandler.executeCode(CodeExecutionRequest.builder()
                .codeFilePath(submissionFolder + "/Main." + language)
                .testFilePath(submissionFolder + "/test.txt")
                .errorFilePath(submissionFolder + "/error.txt")
                .errorMsgFilePath(submissionFolder + "/error-msg.txt")
                .timeLimit(timeLimit)
                .reportingFilePath(submissionFolder + "/report.txt")
                .outputFilePath(submissionFolder + "/output.txt")
                .build());
        System.out.println("executed class CodeExecutionService");
        context.close();
    }
}
