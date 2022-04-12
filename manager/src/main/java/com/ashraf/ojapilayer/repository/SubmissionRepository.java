package com.ashraf.ojapilayer.repository;

import com.ashraf.ojapilayer.entity.Submission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmissionRepository extends CrudRepository<Submission, Long> {
    Optional<Submission> findById(Long id);
}
