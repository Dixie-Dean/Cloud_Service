package com.workshop.dixie.repository;

import com.workshop.dixie.entity.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {

    @Query(value = "select * from cloud_schema.token where auth_token = :auth_token", nativeQuery = true)
    Optional<Token> findToken(@Param("auth_token") String token);

    @Query(value = "delete from cloud_schema.token where username = :username returning 'Success logout!'", nativeQuery = true)
    Optional<String> removeToken(@Param("username") String username);

}
