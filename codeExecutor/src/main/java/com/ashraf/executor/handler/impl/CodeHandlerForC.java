package com.ashraf.executor.handler.impl;

import com.ashraf.executor.handler.CodeHandler;
import com.ashraf.executor.model.CodeExecutionRequest;
import com.ashraf.executor.model.CodeExecutionResponse;
import com.ashraf.executor.util.FileReaderUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
@Component("c")
public class CodeHandlerForC implements CodeHandler {
    @Override
    public CodeExecutionResponse executeCode(CodeExecutionRequest request) {
        log.info("Starting to execute code for C programme");
        File dir = new File(request.getCodeFilePath());
        String fileName = request.getCodeFilePath().substring(request.getCodeFilePath().lastIndexOf("/")+1);
        String exeName = fileName.substring(0, fileName.length() - 2);
        try {
            String compileCmd = "gcc " + dir.getParent() +"/" + fileName + " -o " + dir.getParent() + "/" + exeName;
            CommandLine compileCmdLine = CommandLine.parse(compileCmd);
            DefaultExecutor executor = new DefaultExecutor();
            executor.execute(compileCmdLine);
            String runCmd = dir.getParent() + "/" + exeName;
            CommandLine runCmdLine = CommandLine.parse(runCmd);
            String inputArg = FileReaderUtil.getFileAsString(request.getTestFilePath());
            ByteArrayInputStream input = new ByteArrayInputStream(inputArg.getBytes(StandardCharsets.ISO_8859_1));
            FileOutputStream output = new FileOutputStream(request.getCodeFilePath().substring(0, request.getCodeFilePath().lastIndexOf("/")) + "/output.txt");
            executor.setStreamHandler(new PumpStreamHandler(output, null, input));
            executor.execute(runCmdLine);
        } catch (IOException e) {
            log.error("Exception while executing program!!");
            throw new RuntimeException(e);
        }
        return null;
    }
}