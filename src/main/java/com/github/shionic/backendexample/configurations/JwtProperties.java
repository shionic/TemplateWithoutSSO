package com.github.shionic.backendexample.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperties {
    private boolean generateTemp;
    private String publicKeyPath;
    private String privateKeyPath;
    private String issuer;

    public JwtProperties() {
    }
}
