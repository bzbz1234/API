package com.example.demo.monitor;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class QpsLimiter {
    private static final int MAX_REQUESTS_PER_SECOND = 10;
    private static final long SECOND_IN_MILLIS = 1000;
    private long lastRefillTime = System.currentTimeMillis();
    private AtomicInteger tokens = new AtomicInteger(MAX_REQUESTS_PER_SECOND);

    public boolean allowRequest() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastRefillTime;
        if (elapsedTime >= SECOND_IN_MILLIS) {
            refillTokens();
            lastRefillTime = currentTime;
        }
        return tokens.getAndDecrement() > 0;
    }

    private void refillTokens() {
        tokens.set(MAX_REQUESTS_PER_SECOND);
    }
}
