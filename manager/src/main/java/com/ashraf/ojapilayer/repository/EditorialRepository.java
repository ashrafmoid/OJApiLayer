package com.ashraf.ojapilayer.repository;

import com.ashraf.ojapilayer.entity.Editorial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EditorialRepository extends CrudRepository<Editorial, Long> {
    Optional<Editorial> findById(Long id);
}
