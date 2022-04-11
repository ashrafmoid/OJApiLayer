package com.ashraf.ojapilayer.repository;

import com.ashraf.ojapilayer.entity.RegisteredUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegisteredUserRepository  extends CrudRepository<RegisteredUser, String> {

    Optional<RegisteredUser> findById(String id);
    boolean existsById(String s);
    @Query("SELECT new java.lang.Long(u.id) FROM registeredUser r JOIN r.userProfile u where r.id in ?1")
    List<Long> findAllUserProfileIdByHandle(List<String> ids);
}
