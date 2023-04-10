package com.example.exercise.security;

import com.example.exercise.entity.User;
import com.example.exercise.entity.enums.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;

public class UserDetailsImpl implements UserDetails {


    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }
    @Override
    public List<GrantedAuthority> getAuthorities() {
        UserRoleEnum role = user.getRole();
        String authority = role.getAuthority();
        return Arrays.asList(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getPassword() {
        return null;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}

