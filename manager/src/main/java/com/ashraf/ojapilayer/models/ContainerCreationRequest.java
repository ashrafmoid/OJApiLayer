package com.ashraf.ojapilayer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String command;
    private String volume;
}
