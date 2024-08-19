package com.github.shionic.backendexample.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "UserSession")
@Table(name = "user_sessions")
@Getter
@Setter
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_session_gen")
    @SequenceGenerator(name = "user_session_gen", sequenceName = "user_session_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    private boolean active;
}
