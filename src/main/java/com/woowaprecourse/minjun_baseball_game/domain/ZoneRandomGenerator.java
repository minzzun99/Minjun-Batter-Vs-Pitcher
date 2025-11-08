package com.woowaprecourse.minjun_baseball_game.domain;

import java.util.Random;

public class ZoneRandomGenerator {
    private static final int MIN_ZONE_RANGE = 1;
    private static final int MAX_ZONE_RANGE = 9;

    private final Random random;

    public ZoneRandomGenerator(Random random) {
        this.random = random;
    }

    public ZoneRandomGenerator() {
        this(new Random());
    }

    public Zone randomZone() {
        int number = random.nextInt(MAX_ZONE_RANGE - MIN_ZONE_RANGE + 1) + MIN_ZONE_RANGE;
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
