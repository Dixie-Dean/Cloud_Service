package com.workshop.dixie.configuration;


import com.workshop.dixie.mapper.CloudUserMapper;
import com.workshop.dixie.mapper.FileMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
    @Bean
    public CloudUserMapper cloudUserMapper() {
        return new CloudUserMapper();
    }

    @Bean
    public FileMapper fileMapper() {
        return new FileMapper();
    }
}
