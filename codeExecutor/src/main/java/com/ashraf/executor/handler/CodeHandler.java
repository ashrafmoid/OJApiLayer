package com.ashraf.executor.handler;

import com.ashraf.executor.model.CodeExecutionRequest;
import com.ashraf.executor.util.FileReaderUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Log4j2
public abstract class CodeHandler {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    protected final DefaultExecutor executor = new DefaultExecutor();

    public void executeCode(CodeExecutionRequest request) {
        log.info("Starting to execute code");
        try {
            compileCode(request);
            List<String> inputArg = FileReaderUtil.getFileAsString(request.getTestFilePath());
            FileOutputStream output = new FileOutputStream(request.getCodeFilePath().substring(0, request.getCodeFilePath().lastIndexOf("/")) + "/output.txt");
            for (String inputString : inputArg) {
                execute(inputString, output, request);
            }
        } catch (IOException e) {
            log.error("Exception while executing program!!");
            throw new RuntimeException(e);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            log.error("Thread related exception ", e);
        } finally {
            executorService.shutdownNow();
        }
    }

    protected abstract void compileCode(CodeExecutionRequest request) throws IOException;
    protected abstract void runCode(CodeExecutionRequest request) throws IOException;

    private void execute(String input, OutputStream output, CodeExecutionRequest request)
            throws ExecutionException, InterruptedException, TimeoutException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.ISO_8859_1));
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(output, null, inputStream);
        Callable<Void> codeExecutionCallable = () -> {
            executor.setStreamHandler(pumpStreamHandler);
            runCode(request);
            return null;
        };
        Future<Void> future = executorService.submit(codeExecutionCallable);
        future.get(1L, TimeUnit.SECONDS);
    }
}
