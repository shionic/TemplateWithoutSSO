package com.github.shionic.backendexample.models;

import com.github.shionic.backendexample.security.BasicUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "User")
@Table(name = "users")
@Getter
@Setter
public class User implements BasicUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @ManyToMany
    @JoinTable(
            name = "user_role_links",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> userRoles;

    @Override
    public Long getSessionId() {
        throw new UnsupportedOperationException();
    }

    public List<String> getRoles() {
        return userRoles.stream().map(UserRole::getId).toList();
    }
}
