package com.github.shionic.backendexample.repositories;

import com.github.shionic.backendexample.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
