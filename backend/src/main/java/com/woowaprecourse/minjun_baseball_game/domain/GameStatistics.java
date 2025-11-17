package com.woowaprecourse.minjun_baseball_game.domain;

import java.util.Map;
import lombok.Getter;

@Getter
public class GameStatistics {
    private int singles = 0;
    private int doubles = 0;
    private int triples = 0;
    private int homeruns = 0;

    private int strikeOuts = 0;
    private int groundOuts = 0;
    private int flyOuts = 0;

    private int totalAtBats = 0;

    private final Map<HitType, Runnable> hitActions = Map.of(
            HitType.SINGLE, () -> singles++,
            HitType.DOUBLE, () -> doubles++,
            HitType.TRIPLE, () -> triples++,
            HitType.HOMERUN, () -> homeruns++
    );

    private final Map<OutType, Runnable> outActions = Map.of(
            OutType.STRIKE_OUT, () -> strikeOuts++,
            OutType.GROUND_OUT, () -> groundOuts++,
            OutType.FLY_OUT, () -> flyOuts++
    );

    public void recordHit(HitType hitType) {
        totalAtBats++;
        hitActions.get(hitType).run();
    }

    public void recordOut(OutType outType) {
        totalAtBats++;
        outActions.get(outType).run();
    }

    public int getTotalHits() {
        return singles + doubles + triples + homeruns;
    }

    public int getTotalOuts() {
        return strikeOuts + groundOuts + flyOuts;
    }

    public double getBattingAverage() {
        if (totalAtBats == 0) {
            return 0.0;
        }
        return (double) getTotalHits() / totalAtBats;
    }
}
