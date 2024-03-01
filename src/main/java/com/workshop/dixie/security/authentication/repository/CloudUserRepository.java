package com.workshop.dixie.security.authentication.repository;

import com.workshop.dixie.security.authentication.model.entity.CloudUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CloudUserRepository extends JpaRepository<CloudUser, Long> {

    Optional<CloudUser> findCloudUserByEmail(String email);

    boolean existsByEmail(String email);

}
