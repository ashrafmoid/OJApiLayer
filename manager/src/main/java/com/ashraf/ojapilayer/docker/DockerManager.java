package com.ashraf.ojapilayer.docker;

import com.ashraf.ojapilayer.enums.ContainerStatus;
import com.ashraf.ojapilayer.models.Container;
import com.ashraf.ojapilayer.models.ContainerCreationRequest;
import com.ashraf.ojapilayer.models.CreateContainerResponse;

import java.util.List;

public interface DockerManager {
    CreateContainerResponse createContainer(ContainerCreationRequest containerCreationRequest);
    List<Container> getAllContainersWithStatus(ContainerStatus status);
    String buildImageFromFile(String filepath);
    void startContainerWithId(String id);
    void stopContainerWithId(String id);
    void killContainerWithId(String id);
}
