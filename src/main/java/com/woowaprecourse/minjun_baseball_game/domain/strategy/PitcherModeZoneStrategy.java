package com.woowaprecourse.minjun_baseball_game.domain.strategy;

import com.woowaprecourse.minjun_baseball_game.domain.StrikeZone;
import com.woowaprecourse.minjun_baseball_game.domain.Zone;
import com.woowaprecourse.minjun_baseball_game.domain.ZoneRandomGenerator;

public class PitcherModeZoneStrategy implements ZoneStrategy {
    private final ZoneRandomGenerator generator;

    public PitcherModeZoneStrategy() {
        this.generator = new ZoneRandomGenerator();
    }

    @Override
    public StrikeZone generate(int userSelected) {
        Zone hotZone = generator.randomZone();
        Zone coldZone = generator.randomUniqueZone(hotZone);
        return new StrikeZone(hotZone, coldZone);
    }
}
