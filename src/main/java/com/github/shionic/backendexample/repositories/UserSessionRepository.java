package com.github.shionic.backendexample.repositories;

import com.github.shionic.backendexample.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    @Query("select us from UserSession us join fetch us.user where us.refreshToken = ?1 and us.active = true")
    Optional<UserSession> findByRefreshToken(String refreshToken);
    @Query("update UserSession us set us.refreshToken = ?2 where us.id = ?1")
    @Modifying
    void updateRefreshToken(Long id, String refreshToken);
    @Query("update UserSession us set us.active = false, us.endDate = ?2 where us.id = ?1")
    @Modifying
    void terminateSession(Long id, LocalDateTime endDate);
}
