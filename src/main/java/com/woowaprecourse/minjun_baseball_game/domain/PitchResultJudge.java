package com.woowaprecourse.minjun_baseball_game.domain;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;

public class PitchResultJudge {
    private static final double HOT_ZONE_MULTIPLIER = 1.5;
    private static final double COLD_ZONE_MULTIPLIER = 0.5;
    private static final double NORMAL_ZONE_MULTIPLIER = 1.0;
    private static final double SWING_AND_MISS_MULTIPLIER = 0.5;
    private static final double GROUND_OUT_RATE = 0.5;

    private final BattingRecord battingRecord;
    private final NumberGenerator numberGenerator;

    public PitchResultJudge(BattingRecord battingRecord, NumberGenerator numberGenerator) {
        this.battingRecord = battingRecord;
        this.numberGenerator = numberGenerator;
    }

    public PitchResult judge(Zone pitchedZone, StrikeZone strikeZone) {
        double hitRate = calculateHitRate(pitchedZone, strikeZone);
        double randomValue = numberGenerator.generateNumber();
        double swingAndMissRate = hitRate + getSwingAndMissRate(hitRate);
        // 안타 판정
        if (randomValue < hitRate) {
            return PitchResult.hit(battingRecord.decideHitType());
        }
        // 헛스윙 판정
        if (randomValue < swingAndMissRate) {
            return PitchResult.swingAndMiss();
        }
        // 아웃 타입 판정
        return PitchResult.out(decideOutType());
    }

    private double calculateHitRate(Zone pitchedZone, StrikeZone strikeZone) {
        double battingAverage = battingRecord.getBattingAverage();

        return battingAverage * getMultiplier(pitchedZone, strikeZone);
    }

    private double getMultiplier(Zone pitchedZone, StrikeZone strikeZone) {
        if (strikeZone.isHotZone(pitchedZone)) {
            return HOT_ZONE_MULTIPLIER;
        }
        if (strikeZone.isColdZone(pitchedZone)) {
            return COLD_ZONE_MULTIPLIER;
        }
        return NORMAL_ZONE_MULTIPLIER;
    }

    private OutType decideOutType() {
        double value = numberGenerator.generateNumber();

        if (value < GROUND_OUT_RATE) {
            return OutType.GROUND_OUT;
        }
        return OutType.FLY_OUT;
    }

    private double getSwingAndMissRate(double hitRate) {
        return (1.0 - hitRate) * SWING_AND_MISS_MULTIPLIER;
    }
}
