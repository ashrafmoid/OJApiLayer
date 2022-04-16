package com.ashraf.executor.handler.impl;


import com.ashraf.commons.models.CodeExecutionRequest;
import com.ashraf.commons.models.CodeExecutionResponse;
import com.ashraf.executor.handler.CodeHandler;
import com.ashraf.executor.util.TestFileReaderUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
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
            String inputArg = TestFileReaderUtil.getTestFileAsString(request.getTestFilePath());
            ByteArrayInputStream input = new ByteArrayInputStream(inputArg.getBytes(StandardCharsets.ISO_8859_1));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            executor.setStreamHandler(new PumpStreamHandler(output, null, input));
            executor.execute(runCmdLine);
            System.out.println("result: " + output.toString(StandardCharsets.ISO_8859_1));
        } catch (IOException e) {
            log.error("Exception while executing program!!");
            throw new RuntimeException(e);
        }
        return null;
    }
}