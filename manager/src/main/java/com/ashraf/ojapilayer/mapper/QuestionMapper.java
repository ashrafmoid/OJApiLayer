package com.ashraf.ojapilayer.mapper;

import com.ashraf.ojapilayer.DTO.QuestionDTO;
import com.ashraf.ojapilayer.entity.Question;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionDTO questionToQuestionDTO(Question question);
}
