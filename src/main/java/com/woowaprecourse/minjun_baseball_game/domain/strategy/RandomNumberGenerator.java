package com.woowaprecourse.minjun_baseball_game.domain.strategy;

import java.security.SecureRandom;

public class RandomNumberGenerator implements NumberGenerator {
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public double generateNumber() {
        return secureRandom.nextDouble();
    }
}
