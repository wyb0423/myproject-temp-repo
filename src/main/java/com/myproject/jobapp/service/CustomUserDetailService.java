package com.myproject.jobapp.service;

import com.myproject.jobapp.entity.Users;
import com.myproject.jobapp.repository.UserRepository;
import com.myproject.jobapp.util.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//this service can draw the user from db
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return new CustomUserDetails(user);
    }
}
