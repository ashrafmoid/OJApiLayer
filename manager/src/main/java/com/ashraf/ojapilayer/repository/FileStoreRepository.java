package com.ashraf.ojapilayer.repository;

import com.ashraf.ojapilayer.entity.FileStore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileStoreRepository extends CrudRepository<FileStore, String> {
    Optional<FileStore> findById(String id);
}
