package com.github.shionic.backendexample.services;

import com.github.shionic.backendexample.models.User;
import com.github.shionic.backendexample.repositories.UserRepository;
import com.github.shionic.backendexample.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getReferenceById(Long aLong) {
        return userRepository.getReferenceById(aLong);
    }

    public Optional<User> findById(Long aLong) {
        return userRepository.findById(aLong);
    }
}
