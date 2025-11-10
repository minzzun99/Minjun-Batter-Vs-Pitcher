package com.woowaprecourse.minjun_baseball_game.domain;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;

public class ZoneRandomGenerator {
    private static final int MIN_ZONE_RANGE = 1;
    private static final int MAX_ZONE_RANGE = 9;

    private final NumberGenerator numberGenerator;

    public ZoneRandomGenerator(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public Zone randomZone() {
        double random = numberGenerator.generateNumber();
        int number = (int) (random * (MAX_ZONE_RANGE - MIN_ZONE_RANGE + 1)) + MIN_ZONE_RANGE;
        return new Zone(number);
    }

    public Zone randomUniqueZone(Zone target) {
        Zone zone;
        do {
            zone = randomZone();
        } while (zone.equals(target));
        return zone;
    }
}
