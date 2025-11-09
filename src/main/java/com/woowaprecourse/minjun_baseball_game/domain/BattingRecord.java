package com.woowaprecourse.minjun_baseball_game.domain;

public class BattingRecord {
    private static final int TOTAL_AT_BATS = 166;
    private static final int SINGLES = 33;
    private static final int DOUBLES = 23;
    private static final int TRIPLES = 4;
    private static final int HOMERUNS = 1;

    private final String playerName;

    public BattingRecord(String playerName) {
        this.playerName = playerName;
    }

    public HitType decideHitType() {
        double value = Math.random();

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

    public String getPlayerName() {
        return playerName;
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
