package com.ashraf.ojapilayer.repository;

import com.ashraf.ojapilayer.entity.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findById(Long id);
    Page<Submission> findAll(Pageable pageable);
}
