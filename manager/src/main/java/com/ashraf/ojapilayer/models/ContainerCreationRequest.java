package com.ashraf.ojapilayer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerCreationRequest {
    private String imageName;
    private String imageVersion;
    private String name;
    private String hostName;
    private String hostPort;
    private String containerPort;
    private List<String> command;
    private String volume;
}
