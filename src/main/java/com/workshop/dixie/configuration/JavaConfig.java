package com.workshop.dixie.configuration;


import com.workshop.dixie.mapper.FileMapper;
import com.workshop.dixie.mapper.TokenMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
    @Bean
    public FileMapper fileMapper() {
        return new FileMapper();
    }

    @Bean
    public TokenMapper tokenMapper() {
        return new TokenMapper();
    }
}
