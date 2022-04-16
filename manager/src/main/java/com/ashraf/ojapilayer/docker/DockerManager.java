package com.ashraf.ojapilayer.docker;

import com.ashraf.ojapilayer.enums.ContainerStatus;
import com.ashraf.ojapilayer.models.Container;
import com.ashraf.ojapilayer.models.ContainerCreationRequest;
import com.ashraf.ojapilayer.models.CreateContainerResponse;
import com.spotify.docker.client.exceptions.DockerException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface DockerManager {
    CreateContainerResponse createContainer(ContainerCreationRequest containerCreationRequest) throws DockerException, InterruptedException;
    List<Container> getAllContainersWithStatus(ContainerStatus status) throws DockerException, InterruptedException;
    String buildImageFromFile(String filepath, String name) throws DockerException, IOException, InterruptedException, URISyntaxException;
    void startContainerWithId(String id) throws DockerException, InterruptedException;
    void stopContainerWithId(String id) throws DockerException, InterruptedException;
    void killContainerWithId(String id) throws DockerException, InterruptedException;
}
