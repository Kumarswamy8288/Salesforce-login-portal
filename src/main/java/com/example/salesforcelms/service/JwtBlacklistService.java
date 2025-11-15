package com.example.salesforcelms.service;

import com.example.salesforcelms.entity.BlacklistedToken;
import com.example.salesforcelms.repository.BlacklistedTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class JwtBlacklistService {

    private final BlacklistedTokenRepository repository;

    public JwtBlacklistService(BlacklistedTokenRepository repository) {
        this.repository = repository;
    }

    public void blacklistToken(String token) {
        if (repository.findByToken(token).isEmpty()) {
            repository.save(new BlacklistedToken(null, token, null));
        }
    }

    public boolean isTokenBlacklisted(String token) {
        return repository.findByToken(token).isPresent();
    }
}
