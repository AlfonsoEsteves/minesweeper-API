package com.example.minesweeper.authentication;

import com.example.minesweeper.model.User;
import com.example.minesweeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * This class is a wrapper of UserService
 * It allows Spring Security to handle the users with the UserDetails interface
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadUser(username);
        return new CustomUserDetails(user);
    }
}
