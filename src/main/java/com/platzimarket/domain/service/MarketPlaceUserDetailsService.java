package com.platzimarket.domain.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MarketPlaceUserDetailsService implements UserDetailsService {
    // * Create an encoder password with strength 16
    // ? https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html#authentication-password-storage-boot-cli
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String passwordEncoder = encoder.encode("Leo12leo");

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("candrade",passwordEncoder, new ArrayList<>());
    }
}
