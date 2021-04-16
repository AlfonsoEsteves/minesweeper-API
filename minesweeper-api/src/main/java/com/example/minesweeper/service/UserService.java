package com.example.minesweeper.service;

import com.example.minesweeper.gamelogic.User;
import com.example.minesweeper.repository.UserRepository;
import com.example.minesweeper.service.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user and persists it in the database.
     * The user's password is hashed before being saved in the database.
     *
     * @param name the user's name
     * @param password the user's password
     * @return the created user
     */
    public User createUser(String name, String password) throws UserAlreadyExistsException {
        if(loadUser(name) != null) {
            throw new UserAlreadyExistsException();
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(name, hashedPassword);
        userRepository.save(user);
        return user;
    }

    /**
     * Fetches a user from the database
     *
     * @param name of the user
     * @return the requested user
     * @throws UsernameNotFoundException
     */
    public User loadUser(String name) throws UsernameNotFoundException {
        return userRepository.findById(name).orElse(null);
    }
}
