package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomPasswordDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    public CustomPasswordDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPassword(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with password: " + username));
        return user; // The User entity itself implements UserDetails
    }
}
