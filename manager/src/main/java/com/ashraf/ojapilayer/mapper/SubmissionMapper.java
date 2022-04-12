package com.ashraf.ojapilayer.mapper;

import com.ashraf.ojapilayer.DTO.SubmissionDTO;
import com.ashraf.ojapilayer.entity.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {
    @Mappings({
            @Mapping(target = "questionId" , source = "submission.question.id"),
            @Mapping(target = "authorId" , source = "submission.author.id")
    })
    SubmissionDTO submissionToSubmissionDTO(Submission submission);
}
