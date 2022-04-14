package com.ashraf.ojapilayer.docker;

import com.ashraf.ojapilayer.enums.ContainerStatus;
import com.ashraf.ojapilayer.mapper.DockerClientMapper;
import com.ashraf.ojapilayer.models.Container;
import com.ashraf.ojapilayer.models.ContainerCreationRequest;
import com.ashraf.ojapilayer.models.CreateContainerResponse;
import com.github.dockerjava.api.DockerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DockerManagerImpl implements DockerManager {

    private final DockerClient dockerClient;
    private final DockerClientMapper dockerClientMapper;

    @Override
    public CreateContainerResponse createContainer(ContainerCreationRequest containerCreationRequest) {
        return null;
    }

    @Override
    public List<Container> getAllContainersWithStatus(ContainerStatus status) {
        return dockerClient.listContainersCmd().exec().stream()
                .map(dockerClientMapper::dockerClientContainerToContainer)
                .collect(Collectors.toList());

    }

    @Override
    public String buildImageFromFile(String filepath) {
        return null;
    }

    @Override
    public void startContainerWithId(String id) {

    }

    @Override
    public void stopContainerWithId(String id) {

    }

    @Override
    public void killContainerWithId(String id) {

    }
}
