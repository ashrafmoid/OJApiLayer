package com.ashraf.ojapilayer.repository;

import com.ashraf.ojapilayer.entity.RegisteredUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisteredUserRepository  extends CrudRepository<RegisteredUser, String> {

    Optional<RegisteredUser> findById(String id);
    boolean existsById(String s);
}
