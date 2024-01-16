package com.workshop.dixie.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContainerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Container
    private final GenericContainer<?> cloudServiceContainer =
            new GenericContainer<>("cloud-service:latest")
                    .withExposedPorts(5500);

    @Test
    void containerTest() {
        Integer port = cloudServiceContainer.getMappedPort(8080);

        ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port, String.class);

        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}
