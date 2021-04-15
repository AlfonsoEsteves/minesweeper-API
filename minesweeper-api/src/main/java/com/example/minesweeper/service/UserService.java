package com.example.minesweeper.service;

import com.example.minesweeper.model.User;
import com.example.minesweeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user and persists it in the database.
     * The user's password is hashed before being saved in the database.
     *
     * @param name the user's name
     * @param password the user's password
     * @return the created user
     */
    public User createUser(String name, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(name, hashedPassword);
        userRepository.save(user);
        return user;
    }

    public User loadUser(String name) throws UsernameNotFoundException {
        return userRepository.findById(name).get();
    }
}
