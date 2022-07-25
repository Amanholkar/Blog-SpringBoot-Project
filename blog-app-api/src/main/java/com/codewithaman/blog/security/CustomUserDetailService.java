package com.codewithaman.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithaman.blog.entities.User;
import com.codewithaman.blog.exceptions.ResourceNotFoundException;
import com.codewithaman.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{


    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // loading user form database by username

      User user =    this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "email"+username, 0));

        return user;
    }
    
}
