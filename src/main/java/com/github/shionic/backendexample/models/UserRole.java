package com.github.shionic.backendexample.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "UserRole")
@Table(name = "user_roles")
@Getter
@Setter
public class UserRole {
    @Id
    @SequenceGenerator(name = "user_roles_gen", sequenceName = "user_roles_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, length = 64)
    private String id;
}
