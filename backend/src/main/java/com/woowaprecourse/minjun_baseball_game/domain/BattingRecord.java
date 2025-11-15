package com.woowaprecourse.minjun_baseball_game.domain;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;
import lombok.Getter;

public class BattingRecord {
    @Getter
    private final String playerName;

    private final int totalAtBats;
    private final int singles;
    private final int doubles;
    private final int triples;
    private final int homeruns;

    private final NumberGenerator numberGenerator;


    public BattingRecord(String playerName, int totalAtBats, int singles, int doubles, int triples, int homeruns,
                         NumberGenerator numberGenerator) {
        this.playerName = playerName;
        this.totalAtBats = totalAtBats;
        this.singles = singles;
        this.doubles = doubles;
        this.triples = triples;
        this.homeruns = homeruns;
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
        return (double) getTotalHits() / totalAtBats;
    }

    public int getTotalHits() {
        return singles + doubles + triples + homeruns;
    }

    private double getSingleRate() {
        return (double) singles / getTotalHits();
    }

    private double getDoubleRate() {
        return (double) (singles + doubles) / getTotalHits();
    }

    private double getTripleRate() {
        return (double) (singles + doubles + triples) / getTotalHits();
    }
}
