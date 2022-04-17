package com.ashraf.ojapilayer.service.impl;

import com.ashraf.ojapilayer.docker.DockerManager;
import com.ashraf.ojapilayer.entity.FileStore;
import com.ashraf.ojapilayer.entity.Submission;
import com.ashraf.ojapilayer.enums.ProgrammingLanguage;
import com.ashraf.ojapilayer.models.BuildImageCreationRequest;
import com.ashraf.ojapilayer.models.ContainerCreationRequest;
import com.ashraf.ojapilayer.models.CreateContainerResponse;
import com.ashraf.ojapilayer.service.DocumentService;
import com.ashraf.ojapilayer.service.EvaluationService;
import com.spotify.docker.client.exceptions.DockerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@Component
@Log4j2
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {
    private final DockerManager dockerManager;
    private final DocumentService documentService;
    @Value("${docker.file.context.path}")
    private String dockerContextPath;

    @Override
    public void evaluateSubmission(Submission submission) {
        log.info("Evaluating Submission {}", submission);
        FileStore fileStore = documentService.getDocument(submission.getDocumentLink());
        File file = saveToTempFile(fileStore, submission.getLanguage());
        try {
            final String imageId = dockerManager.buildImageFromFile(
                    BuildImageCreationRequest.builder().imageName("code-executor-" + submission.getId())
                            .dockerContextPath(dockerContextPath).build());
            log.info("Image id {}", imageId);
            CreateContainerResponse response = dockerManager.createContainer(ContainerCreationRequest.builder()
                    .imageName("code-executor-" + submission.getId())
                            .imageVersion("latest")
                    .containerPort("8065").hostPort("8097").name("code-executor-container-" + submission.getId()).build());
            log.info("CreateContainerResponse --> {}", response);
            dockerManager.startContainerWithId(response.getId());
        }catch (IOException | DockerException | InterruptedException | URISyntaxException e) {
            log.error("Error while building container!!!", e);
            throw new RuntimeException(e);
        }
    }

    private File saveToTempFile(FileStore fileStore, ProgrammingLanguage language) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile(RandomStringUtils.randomAlphabetic(6), "." + language.getValue());
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                outputStream.write(fileStore.getData());
            }
        } catch (IOException e) {
            log.error("Exception while writing file to temp location");
            throw new RuntimeException(e);
        }
        return tempFile;
    }
}
