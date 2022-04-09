package com.ashraf.ojapilayer.repository;

import com.ashraf.ojapilayer.entity.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long>  {
    Optional<Question> findById(Long id);

    @Query(value = "SELECT * FROM question  WHERE ?1 = ANY(topics)", nativeQuery = true)
    List<Question> findQuestionsByTopic(String topic);
}
