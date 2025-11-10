package com.woowaprecourse.minjun_baseball_game.domain;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;
import lombok.Getter;

public class BattingRecord {
    private static final int TOTAL_AT_BATS = 166;
    private static final int SINGLES = 33;
    private static final int DOUBLES = 23;
    private static final int TRIPLES = 4;
    private static final int HOMERUNS = 1;

    @Getter
    private final String playerName;
    private final NumberGenerator numberGenerator;

    public BattingRecord(String playerName, NumberGenerator numberGenerator) {
        this.playerName = playerName;
        this.numberGenerator = numberGenerator;
    }

    public HitType decideHitType() {
        double value = numberGenerator.generateNumber();

        if (value < getSingleRate()) {
            return HitType.SINGLE;
        }
        if (value < getDoubleRate()) {
            return HitType.DOUBLE;
        }
        if (value < getTripleRate()) {
            return HitType.TRIPLE;
        }
        return HitType.HOMERUN;
    }

    public double getBattingAverage() {
        return (double) getTotalHits() / TOTAL_AT_BATS;
    }

    public int getTotalHits() {
        return SINGLES + DOUBLES + TRIPLES + HOMERUNS;
    }

    private double getSingleRate() {
        return (double) SINGLES / getTotalHits();
    }

    private double getDoubleRate() {
        return (double) (SINGLES + DOUBLES) / getTotalHits();
    }

    private double getTripleRate() {
        return (double) (SINGLES + DOUBLES + TRIPLES) / getTotalHits();
    }
}
