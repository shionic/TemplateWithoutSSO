package com.github.shionic.backendexample.controllers;

import com.github.shionic.backendexample.exceptions.AuthException;
import com.github.shionic.backendexample.exceptions.BadRequestException;
import com.github.shionic.backendexample.services.JwtService;
import com.github.shionic.backendexample.services.UserService;
import com.github.shionic.backendexample.services.UserSessionService;
import com.github.shionic.backendexample.utils.StringUtils;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserSessionService userSessionService;

    @PostMapping("/authorize")
    public AuthorizeResponse authorize(AuthorizeRequest request) {
        if(request.login == null || request.login.isEmpty()) {
            throw new BadRequestException("'login' field is null or empty");
        }
        if(request.password == null || request.password.isEmpty()) {
            throw new BadRequestException("'password' field is null or empty");
        }
        var optional = userService.findByUsername(request.login);
        if(optional.isEmpty()) {
            throw new AuthException("User not found");
        }
        var user = optional.get();
        if(!userService.verifyPassword(user, request.password)) {
            throw new AuthException("Wrong password");
        }
        var session = userSessionService.create(user);
        String access;
        try {
            access = jwtService.generate(user, session.getId(), 1, ChronoUnit.HOURS);
        } catch (JOSEException e) {
            throw new AuthException("Internal error", e);
        }
        return new AuthorizeResponse(access, session.getRefreshToken(), 3600L);
    }

    @PostMapping("/refresh")
    public AuthorizeResponse refresh(RefreshRequest request) {
        if(request.refreshToken == null || request.refreshToken.isEmpty()) {
            throw new BadRequestException("'refreshToken' field is null or empty");
        }
        var optional = userSessionService.findByRefreshToken(request.refreshToken);
        if(optional.isEmpty()) {
            throw new AuthException("Invalid refreshToken");
        }
        var newRefreshToken = StringUtils.randomString(UserSessionService.REFRESH_TOKEN_LENGTH);
        userSessionService.updateRefreshToken(optional.get(), newRefreshToken);
        String access;
        try {
            access = jwtService.generate(optional.get().getUser(), optional.get().getId(), 1, ChronoUnit.HOURS);
        } catch (JOSEException e) {
            throw new AuthException("Internal error", e);
        }
        return new AuthorizeResponse(access, newRefreshToken, 3600L);
    }

    @PostMapping("/exit")
    @PreAuthorize("isAuthenticated()")
    public void terminateSession() {
        var user = userService.getCurrentUser();
        userSessionService.terminate(user.getSessionId());
    }

    public record AuthorizeResponse(String accessToken, String refreshToken, Long expire) {

    }

    public record AuthorizeRequest(String login, String password) {

    }

    public record RefreshRequest(String refreshToken) {

    }
}
