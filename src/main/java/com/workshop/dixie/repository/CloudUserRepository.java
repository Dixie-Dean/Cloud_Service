package com.workshop.dixie.repository;

import com.workshop.dixie.entity.CloudUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CloudUserRepository extends JpaRepository<CloudUser, Long> {

    Optional<CloudUser> findCloudUserByUsername(String username);

}
