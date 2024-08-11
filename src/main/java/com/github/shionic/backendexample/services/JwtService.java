package com.github.shionic.backendexample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    private JwtKeyHolderService keyHolderService;

    public void generate() {

    }

    public void parseAndVerify(String token) {

    }
}
