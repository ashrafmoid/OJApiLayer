package com.ashraf.ojapilayer.mapper;

import com.ashraf.ojapilayer.models.Container;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DockerClientMapper {
    Container dockerClientContainerToContainer(com.github.dockerjava.api.model.Container container);
}
