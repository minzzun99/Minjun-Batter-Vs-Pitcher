package com.woowaprecourse.minjun_baseball_game.domain;

import lombok.Getter;

public class PitchResult {
    @Getter
    private final ResultType resultType;
    private final HitType hitType;
    private final OutType outType;

    private PitchResult(ResultType resultType, HitType hitType, OutType outType) {
        this.resultType = resultType;
        this.hitType = hitType;
        this.outType = outType;
    }

    // 안타
    public static PitchResult hit(HitType hitType) {
        return new PitchResult(ResultType.HIT, hitType, null);
    }

    // 아웃 (삼진, 땅볼, 플라이, 희생플라이)
    public static PitchResult out(OutType outType) {
        return new PitchResult(ResultType.OUT, null, outType);
    }

    // 헛스윙
    public static PitchResult swingAndMiss() {
        return new PitchResult(ResultType.SWING_AND_MISS, null, null);
    }

    public boolean isHit() {
        return resultType == ResultType.HIT;
    }

    public boolean isOut() {
        return resultType == ResultType.OUT;
    }

    public boolean isSwingAndMiss() {
        return resultType == ResultType.SWING_AND_MISS;
    }

    public boolean allowRunnerAdvance() {
        if (isHit()) {
            return true;
        }

        if (isOut()) {
            return outType.isRunnerAdvance();
        }

        return false;
    }

    public HitType getHitType() {
        if (!isHit()) {
            throw new IllegalStateException("아웃은 안타 종류가 없습니다.");
        }
        return hitType;
    }

    public OutType getOutType() {
        if (!isOut()) {
            throw new IllegalStateException("안타는 아웃 종류가 없습니다.");
        }
        return outType;
    }
}
