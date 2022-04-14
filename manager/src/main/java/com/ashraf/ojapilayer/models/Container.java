package com.ashraf.ojapilayer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Container {
    private String id;
    private String command;
    private String image;
    private String imageId;
    private String status;
}
