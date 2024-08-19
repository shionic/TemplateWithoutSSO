package com.github.shionic.backendexample.services;

import com.github.shionic.backendexample.models.User;
import com.github.shionic.backendexample.models.UserSession;
import com.github.shionic.backendexample.repositories.UserSessionRepository;
import com.github.shionic.backendexample.utils.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserSessionService {
    public static final int REFRESH_TOKEN_LENGTH = 32;
    @Autowired
    private UserSessionRepository userSessionRepository;

    @Transactional
    public UserSession create(User user) {
        var session = new UserSession();
        session.setUser(user);
        session.setStartDate(LocalDateTime.now());
        session.setRefreshToken(StringUtils.randomString(REFRESH_TOKEN_LENGTH));
        return userSessionRepository.save(session);
    }

    @Transactional
    public Optional<UserSession> findById(Long aLong) {
        return userSessionRepository.findById(aLong);
    }

    @Transactional
    public Optional<UserSession> findByRefreshToken(String refreshToken) {
        return userSessionRepository.findByRefreshToken(refreshToken);
    }

    @Transactional
    public void updateRefreshToken(UserSession userSession, String refreshToken) {
        userSessionRepository.updateRefreshToken(userSession.getId(), refreshToken);
    }

    @Transactional
    public void terminate(UserSession userSession) {
        userSessionRepository.terminateSession(userSession.getId(), LocalDateTime.now());
    }

    @Transactional
    public void terminate(Long sessionId) {
        userSessionRepository.terminateSession(sessionId, LocalDateTime.now());
    }
}
