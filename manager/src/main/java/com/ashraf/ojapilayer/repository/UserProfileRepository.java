package com.ashraf.ojapilayer.repository;

import com.ashraf.ojapilayer.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long>  {
    Optional<UserProfile> findById(Long id);
    List<UserProfile> findAllByIdIn(List<Long> ids);
}
