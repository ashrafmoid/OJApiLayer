package com.ashraf.ojapilayer.repository;

import com.ashraf.ojapilayer.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, String>  {
    Optional<UserProfile> findById(Long id);
}
