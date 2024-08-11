package com.github.shionic.backendexample.configurations;

import com.github.shionic.backendexample.services.JwtKeyHolderService;
import com.github.shionic.backendexample.utils.JwkUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;

@Configuration
public class JwtConfiguration {
    @Bean
    public JwtKeyHolderService jwtKeyHolderService(JwtProperties properties) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        ECPublicKey publicKey;
        ECPrivateKey privateKey;
        if (properties.isGenerateTemp()) {
            var pair = JwkUtils.generateECKeys();
            publicKey = (ECPublicKey) pair.getPublic();
            privateKey = (ECPrivateKey) pair.getPrivate();
        } else {
            publicKey = JwkUtils.readECPublicKey(Path.of(properties.getPublicKeyPath()));
            privateKey = JwkUtils.readECPrivateKey(Path.of(properties.getPrivateKeyPath()));
        }
        return new JwtKeyHolderService(privateKey, publicKey);
    }
}
