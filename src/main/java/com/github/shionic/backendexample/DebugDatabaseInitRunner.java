package com.github.shionic.backendexample;

import com.github.shionic.backendexample.models.User;
import com.github.shionic.backendexample.models.UserRole;
import com.github.shionic.backendexample.repositories.UserRepository;
import com.github.shionic.backendexample.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DebugDatabaseInitRunner implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if(args.length == 0 || !args[0].equals("--startup")) {
            return;
        }
        UserRole adminRole = new UserRole();
        adminRole.setId("ADMIN");
        adminRole = userRoleRepository.save(adminRole);
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setUserRoles(List.of(adminRole));
        userRepository.save(user);
    }
}
