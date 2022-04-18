package com.ashraf.ojapilayer.models;

import com.ashraf.ojapilayer.enums.ContainerStreams;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerAttachRequest {
    private String id;
    private ContainerStreams attachedTo;
}
