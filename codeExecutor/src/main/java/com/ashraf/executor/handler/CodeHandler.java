package com.ashraf.executor.handler;

import com.ashraf.executor.model.CodeExecutionRequest;
import com.ashraf.executor.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Log4j2
@RequiredArgsConstructor
public abstract class CodeHandler {
    protected final ExecutorService executorService;
    protected final DefaultExecutor executor;

    private static final String COMPILATION_ERROR = "COMPILATION_ERROR";

    public void executeCode(CodeExecutionRequest request) throws IOException {
        log.info("Starting to execute code");
        FileOutputStream output;
        FileOutputStream errorStream;
        try {
             output = new FileOutputStream(request.getCodeFilePath().substring(0, request.getCodeFilePath().lastIndexOf("/")) + "/output.txt");
             errorStream = new FileOutputStream(request.getErrorMsgFilePath());
             executor.setStreamHandler(new PumpStreamHandler(output, errorStream, null));
             compileCode(request);
        } catch (IOException e) {
            log.error("Exception occurred while compiling code!!", e);
            FileUtil.WriteToFile(COMPILATION_ERROR, request.getErrorFilePath());
            throw new RuntimeException(e);
        } finally {
            sanitiseErrorFile(request.getErrorMsgFilePath());
        }
        try {
            errorStream = new FileOutputStream(request.getErrorMsgFilePath());
            List<String> inputArg = FileUtil.getFileAsString(request.getTestFilePath());
            for (String inputString : inputArg) {
               execute(inputString, errorStream, output, request);
            }
        } catch (IOException | ExecutionException e) {
            FileUtil.WriteToFile("RUNTIME_ERROR", request.getErrorFilePath());
            throw new RuntimeException(e);
        } catch (InterruptedException | TimeoutException e) {
            FileUtil.WriteToFile("TIME_LIMIT_EXCEEDED", request.getErrorFilePath());
            throw new RuntimeException(e);
        } finally {
            sanitiseErrorFile(request.getErrorMsgFilePath());
            executorService.shutdownNow();
        }
    }

    protected abstract void compileCode(CodeExecutionRequest request) throws IOException;
    protected abstract void runCode(CodeExecutionRequest request) throws IOException;

    private void execute(String input, OutputStream errorStream, OutputStream output, CodeExecutionRequest request)
            throws ExecutionException, InterruptedException, TimeoutException, IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.ISO_8859_1));
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(output, errorStream, inputStream);
        Callable<Void> codeExecutionCallable = () -> {
            executor.setStreamHandler(pumpStreamHandler);
            runCode(request);
            return null;
        };
        Future<Void> future = executorService.submit(codeExecutionCallable);
        future.get(request.getTimeLimit(), TimeUnit.SECONDS);
    }

    private  void sanitiseErrorFile(String errorMsgFileName) throws IOException {
        String replacementWord = errorMsgFileName.substring(0, errorMsgFileName.lastIndexOf("/") + 1);
        FileInputStream inputStream = new FileInputStream(errorMsgFileName);
        Files.deleteIfExists(Path.of(errorMsgFileName));
        FileOutputStream errorOutputStream = new FileOutputStream(errorMsgFileName);
        String content = IOUtils.toString(inputStream, Charset.defaultCharset());
        content = content.replace(replacementWord, "");
        errorOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
        inputStream.close();
        errorOutputStream.close();
    }
}
