package com.workshop.dixie.repository;

import com.workshop.dixie.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {

    @Query(value = "select * from cloud_schema.token where auth_token = :token", nativeQuery = true)
    Optional<Token> findToken(String token);

}
