package com.workshop.dixie.service;

import com.workshop.dixie.security.SecurityUser;
import com.workshop.dixie.repository.CloudUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final CloudUserRepository repository;

    public JpaUserDetailsService(CloudUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findCloudUserByEmail(email).map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
