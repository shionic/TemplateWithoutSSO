package com.github.shionic.backendexample.services;

import com.github.shionic.backendexample.configurations.JwtProperties;
import com.github.shionic.backendexample.security.BasicUser;
import com.github.shionic.backendexample.utils.JwkUtils;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
    @Autowired
    private JwtKeyHolderService keyHolderService;
    @Autowired
    private JwtProperties jwtProperties;

    public String generate(BasicUser basicUser, long unitValue, TemporalUnit unit) throws JOSEException {
        var claimsSet = new JWTClaimsSet.Builder()
                .issuer(jwtProperties.getIssuer())
                .expirationTime(new Date(LocalDateTime.now().plus(unitValue, unit).toEpochSecond(ZoneOffset.UTC)))
                .subject(basicUser.getUsername())
                .claim("userId", basicUser.getId())
                .build();
        var object = new JWSObject(new JWSHeader(JWSAlgorithm.ES256),
                new Payload(claimsSet.toJSONObject()));
        object.sign(new ECDSASigner(keyHolderService.getPrivateKey()));
        return object.serialize();
    }

    public BasicUser parseAndVerify(String token) throws ParseException, JOSEException, SecurityException {
        var signedJWT = SignedJWT.parse(token);
        signedJWT.verify(new ECDSAVerifier(keyHolderService.getPublicKey()));
        signedJWT.getJWTClaimsSet();
        var set = signedJWT.getJWTClaimsSet();
        if(!jwtProperties.getIssuer().equals(set.getIssuer())) {
            throw new SecurityException(String.format("Issuer %s not valid", set.getIssuer()));
        }
        {
            var username = set.getSubject();
            var userId = (Long) set.getClaim("userId");
            @SuppressWarnings("unchecked")
            var roles = (List<String>) set.getClaim("roles");
            return new JwtUser(username, userId, roles);
        }
    }

    @AllArgsConstructor
    public static class JwtUser implements BasicUser {
        private final String username;
        private final Long id;
        private final List<String> roles;
        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public List<String> getRoles() {
            return roles;
        }
    }
}
