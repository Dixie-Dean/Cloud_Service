package com.workshop.dixie.configuration;

import com.workshop.dixie.mapper.FileMapper;
import com.workshop.dixie.mapper.TokenMapper;
import com.workshop.dixie.repository.CloudUserRepository;
import com.workshop.dixie.security.CloudUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class JavaConfig {
    private final CloudUserRepository repository;

    public JavaConfig(CloudUserRepository repository) {
        this.repository = repository;
    }

    @Bean
    public FileMapper fileMapper() {
        return new FileMapper();
    }

    @Bean
    public TokenMapper tokenMapper() {
        return new TokenMapper();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> repository.findCloudUserByEmail(email).map(CloudUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
