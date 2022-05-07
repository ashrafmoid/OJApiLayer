package com.ashraf.ojapilayer.service.impl;

import com.ashraf.ojapilayer.DTO.KafkaSubmissionDTO;
import com.ashraf.ojapilayer.api.requestmodels.CodeEvaluationRequest;
import com.ashraf.ojapilayer.docker.DockerManager;
import com.ashraf.ojapilayer.entity.FileStore;
import com.ashraf.ojapilayer.entity.Question;
import com.ashraf.ojapilayer.entity.Submission;
import com.ashraf.ojapilayer.evaluator.CodeEvaluator;
import com.ashraf.ojapilayer.models.BuildImageCreationRequest;
import com.ashraf.ojapilayer.models.CodeExecutionResponse;
import com.ashraf.ojapilayer.models.ContainerCreationRequest;
import com.ashraf.ojapilayer.models.CreateContainerResponse;
import com.ashraf.ojapilayer.service.DocumentService;
import com.ashraf.ojapilayer.service.EvaluationService;
import com.ashraf.ojapilayer.service.QuestionService;
import com.ashraf.ojapilayer.service.SubmissionService;
import com.spotify.docker.client.exceptions.DockerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {
    private final DockerManager dockerManager;
    private final DocumentService documentService;
    private final QuestionService questionService;
    private final SubmissionService submissionService;
    private final CodeEvaluator codeEvaluator;
    @Value("${docker.file.context.path}")
    private String dockerContextPath;
    @Value("${local.submission.copy.dir}")
    private String localSubmissionCopyDir;
    @Value("${time.limit.per.testcase}")
    private String timeLimitPerTestCase;

    private static final String CODE_EXECUTOR_IMAGE_FORMAT = "code/executor";
    private static final String IMAGE_VERSION = "latest";

    @Override
    public void evaluateSubmission(KafkaSubmissionDTO kafkaSubmissionDTO) {
        Submission submission = submissionService.getSubmissionById(String.valueOf(kafkaSubmissionDTO.getSubmissionId()))
                .orElseThrow();
        Question question = questionService.getQuestionById(Long.valueOf(kafkaSubmissionDTO.getQuestionId()))
                .orElseThrow();
        log.info("Evaluating Submission {}", submission);
        saveRequiredFilesOnDisk(submission, question);
        try {
            // Container is started in detached mode
            String containerId = startContainerForCodeExecution(submission);
            // wait for some time for container to finish execution
            waitWhileContainerIsRunning(containerId);
            log.info("Container finished execution!!");
            waitWhileFileIsNotAvailable(submission);
            CodeExecutionResponse response = codeEvaluator.evaluateCode(CodeEvaluationRequest.builder()
                    .correctOutputFilePath(localSubmissionCopyDir + submission.getId() + "/answer.txt")
                    .userGeneratedOutputFilePath(localSubmissionCopyDir + submission.getId() + "/output.txt")
                    .errorFilePath(localSubmissionCopyDir + submission.getId() + "/error.txt")
                    .errorMsgFilePath(localSubmissionCopyDir + submission.getId() + "/error-msg.txt")
                    .build());
            log.info("CodeExecutionResponse is {}", response);
            submissionService.updateSubmissionResult(response, submission.getId());
        } catch (IOException | DockerException | InterruptedException | URISyntaxException e) {
            log.error("Error while building container!!!", e);
            throw new RuntimeException(e);
        }
    }

    private void waitWhileFileIsNotAvailable(Submission submission) {
        Long startTime = System.currentTimeMillis();
        Path outputPath = Paths.get (localSubmissionCopyDir + submission.getId() + "/output.txt");
        Path errorPath = Paths.get(localSubmissionCopyDir + submission.getId() + "/error.txt");
        while ((!Files.exists(outputPath) && !Files.isDirectory(outputPath)) &&
                (!Files.exists(errorPath) && !Files.isDirectory(errorPath))) {
            Long time = System.currentTimeMillis();
            if (time - startTime > 1000) {
                log.error("File sync taking more than expected time, failing!!!");
                throw new RuntimeException("File sync taking more than expected time");
            }
        }
    }

    private void waitWhileContainerIsRunning(String containerId) throws DockerException, InterruptedException {
        Long startTime = System.currentTimeMillis();
        while(dockerManager.isContainerRunning(containerId)) {
            Long time = System.currentTimeMillis();
            if (time- startTime > 50000) {
                log.error("Container running for more than 5 minutes, something is wrong!! Killing container");
                dockerManager.stopContainerWithId(containerId);
                throw new RuntimeException("Container running for more than 5 minutes, something is wrong!!");
            }
        }
    }

    private void saveRequiredFilesOnDisk(Submission submission, Question question) {
        FileStore submissionDocument = documentService.getDocument(submission.getDocumentLink());
        FileStore testFileDocument = documentService.getDocument(question.getTestFileLink());
        FileStore correctOutputFile = documentService.getDocument(question.getCorrectOutputFileLink());
        saveToFile(submissionDocument, localSubmissionCopyDir + submission.getId() + "/" + "Main." + submission.getLanguage().getValue());
        saveToFile(testFileDocument, localSubmissionCopyDir + submission.getId() + "/" + "test.txt");
        saveToFile(correctOutputFile, localSubmissionCopyDir + submission.getId() + "/answer.txt");
    }

    private String startContainerForCodeExecution(Submission submission) throws DockerException, IOException, URISyntaxException, InterruptedException {
        final String imageName = CODE_EXECUTOR_IMAGE_FORMAT + ":" + IMAGE_VERSION;
        final String imageId = dockerManager.buildImageFromFile(
                BuildImageCreationRequest.builder().imageName(imageName)
                        .dockerContextPath(dockerContextPath).build());
        log.info("Image id {}", imageId);
        CreateContainerResponse response = dockerManager.createContainer(ContainerCreationRequest.builder()
                .imageName(imageName)
                .containerPort("8065").hostPort("8096").name("code-executor-container-" + submission.getId() +" " +
                        submission.getLanguage().getValue())
                .command(List.of("java", "-jar", "codeExecutor-1.0-SNAPSHOT.jar",
                        String.valueOf(submission.getId()), submission.getLanguage().getValue(), timeLimitPerTestCase))
                .volume(localSubmissionCopyDir).build());

        log.info("CreateContainerResponse --> {}", response);
        dockerManager.startContainerWithId(response.getId());
        return response.getId();
    }

    private void saveToFile(FileStore fileStore, String path) {
        File tempFile = new File(path);
        try {
            tempFile.getParentFile().mkdirs();
            tempFile.createNewFile();
        } catch (IOException e) {
            log.info("File already exists, skipping file creation");
        }
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                outputStream.write(fileStore.getData());
        } catch (IOException e) {
            log.error("Exception while writing file to temp location");
            throw new RuntimeException(e);
        }
    }
}
