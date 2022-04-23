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

    private static final String CODE_EXECUTOR_IMAGE_FORMAT = "code-executor-%s";
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
            String containerId = startContainerForCodeExecution(submission);
            // wait for some time for container to finish execution
            waitWhileContainerIsRunning(containerId);
            waitWhileFileIsNotAvailable(submission);
           CodeExecutionResponse response = codeEvaluator.evaluateCode(CodeEvaluationRequest.builder()
                    .correctOutputFilePath(localSubmissionCopyDir + submission.getId() + "/answer.txt")
                    .userGeneratedOutputFilePath(localSubmissionCopyDir + submission.getId() + "/output.txt").build());
           log.info("CodeExecutionResponse is {}", response);
           submissionService.updateSubmissionResult(response, submission.getId());

        }catch (IOException | DockerException | InterruptedException | URISyntaxException e) {
            log.error("Error while building container!!!", e);
            throw new RuntimeException(e);
        }
    }

    private void waitWhileFileIsNotAvailable(Submission submission) {
        Path outputPath = Paths.get (localSubmissionCopyDir + submission.getId() + "/output.txt");
        while (!Files.exists(outputPath) && !Files.isDirectory(outputPath)) {}
    }

    private void waitWhileContainerIsRunning(String containerId) throws DockerException, InterruptedException {
        while(dockerManager.isContainerRunning(containerId)) {}
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
        final String imageName = String.format(CODE_EXECUTOR_IMAGE_FORMAT, submission.getId());
        final String imageId = dockerManager.buildImageFromFile(
                BuildImageCreationRequest.builder().imageName(imageName)
                        .dockerContextPath(dockerContextPath).build());
        log.info("Image id {}", imageId);
        CreateContainerResponse response = dockerManager.createContainer(ContainerCreationRequest.builder()
                .imageName(imageName)
                .imageVersion(IMAGE_VERSION)
                .containerPort("8065").hostPort("8097").name("code-executor-container-" + submission.getId() +" " +
                        submission.getLanguage().getValue())
                .command(List.of("java", "-jar", "codeExecutor-1.0-SNAPSHOT.jar",
                        String.valueOf(submission.getId()), submission.getLanguage().getValue()))
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
