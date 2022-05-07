package com.ashraf.executor.handler.impl;

import com.ashraf.executor.handler.CodeHandler;
import com.ashraf.executor.model.CodeExecutionRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

@Log4j2
@Component("c")
public class CodeHandlerForC extends CodeHandler {

    public CodeHandlerForC(DefaultExecutor defaultExecutor, ExecutorService executorService) {
        super(executorService, defaultExecutor);
    }

    @Override
    protected void compileCode(CodeExecutionRequest request) throws IOException {
        System.out.println("Compiling code for C programme");
        File dir = new File(request.getCodeFilePath());
        String fileName = request.getCodeFilePath().substring(request.getCodeFilePath().lastIndexOf("/")+1);
        String exeName = fileName.substring(0, fileName.length() - 2);
        String compileCmd = "gcc " + dir.getParent() +"/" + fileName + " -o " + dir.getParent() + "/" + exeName;
        CommandLine compileCmdLine = CommandLine.parse(compileCmd);
        executor.execute(compileCmdLine);
    }

    @Override
    protected void runCode(CodeExecutionRequest request) throws IOException {
        File dir = new File(request.getCodeFilePath());
        String fileName = request.getCodeFilePath().substring(request.getCodeFilePath().lastIndexOf("/")+1);
        String exeName = fileName.substring(0, fileName.length() - 2);
        String runCmd = dir.getParent() + "/" + exeName;
        CommandLine runCmdLine = CommandLine.parse(runCmd);
        executor.execute(runCmdLine);
    }
}