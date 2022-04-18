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
    @Value("${local.submission.copy.dir}")
    private String localSubmissionCopyDir;

    private static final String CODE_EXECUTOR_IMAGE_FORMAT = "code-executor-%s";
    private static final String IMAGE_VERSION = "latest";

    @Override
    public void evaluateSubmission(Submission submission) {
        log.info("Evaluating Submission {}", submission);
        FileStore fileStore = documentService.getDocument(submission.getDocumentLink());
        saveToFile(fileStore, submission.getLanguage(), submission);
        try {
            final String imageName = String.format(CODE_EXECUTOR_IMAGE_FORMAT, submission.getId());
            final String imageId = dockerManager.buildImageFromFile(
                    BuildImageCreationRequest.builder().imageName(imageName)
                            .dockerContextPath(dockerContextPath).build());
            log.info("Image id {}", imageId);
            CreateContainerResponse response = dockerManager.createContainer(ContainerCreationRequest.builder()
                    .imageName(imageName)
                            .imageVersion(IMAGE_VERSION)
                    .containerPort("8065").hostPort("8097").name("code-executor-container-" + submission.getId())
                    .volume(localSubmissionCopyDir).build());

            log.info("CreateContainerResponse --> {}", response);
            dockerManager.startContainerWithId(response.getId());
        }catch (IOException | DockerException | InterruptedException | URISyntaxException e) {
            log.error("Error while building container!!!", e);
            throw new RuntimeException(e);
        }
    }

    private void saveToFile(FileStore fileStore, ProgrammingLanguage language, Submission submission) {
        log.info(localSubmissionCopyDir);
        File tempFile = new File(localSubmissionCopyDir + submission.getId() + "/" + "Main." + language.getValue());
        try {
            tempFile.getParentFile().mkdirs();
            tempFile.createNewFile();
        } catch (IOException e) {
            log.info("File already exists, skipping file creation");
        }
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                outputStream.write(fileStore.getData());
                log.info("writing done!!");
        } catch (IOException e) {
            log.error("Exception while writing file to temp location");
            throw new RuntimeException(e);
        }
        log.info(tempFile);
    }
}
