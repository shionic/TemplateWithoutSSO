package com.github.shionic.backendexample.services;

import com.github.shionic.backendexample.models.User;
import com.github.shionic.backendexample.repositories.UserRepository;
import com.github.shionic.backendexample.repositories.UserRoleRepository;
import com.github.shionic.backendexample.security.BasicUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getReferenceById(Long aLong) {
        return userRepository.getReferenceById(aLong);
    }

    public Optional<User> findById(Long aLong) {
        return userRepository.findById(aLong);
    }

    public boolean verifyPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public BasicUser getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        if(context == null) {
            return null;
        }
        var authentication = context.getAuthentication();
        if(authentication == null) {
            return null;
        }
        return (BasicUser) authentication.getDetails();
    }
}
