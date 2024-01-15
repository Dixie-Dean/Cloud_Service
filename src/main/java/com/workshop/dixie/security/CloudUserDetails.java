package com.workshop.dixie.security;

import com.workshop.dixie.entity.CloudUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class CloudUserDetails implements UserDetails {

    private final CloudUser cloudUser;

    public CloudUserDetails(CloudUser cloudUser) {
        this.cloudUser = cloudUser;
    }

    @Override
    public String getUsername() {
        return cloudUser.getEmail();
    }

    @Override
    public String getPassword() {
        return cloudUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(cloudUser.getRoles().split(", ")).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
