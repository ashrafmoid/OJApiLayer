package com.ashraf.ojapilayer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildImageCreationRequest {
    // This should be relative to context path if required
    private String dockerFilePath;
    // This is used by docker to understand current working directory on host
    private String dockerContextPath;
    private String imageName;
}
