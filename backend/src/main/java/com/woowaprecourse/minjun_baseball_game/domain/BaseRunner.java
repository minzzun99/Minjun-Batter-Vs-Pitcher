package com.woowaprecourse.minjun_baseball_game.domain;

public class BaseRunner {
    private boolean first;
    private boolean second;
    private boolean third;

    public BaseRunner() {
        this.first = false;
        this.second = false;
        this.third = false;
    }

    public BaseRunner(boolean first, boolean second, boolean third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public int advance(HitType hitType) {
        if (hitType == HitType.SINGLE) {
            return advanceSingle();
        }
        if (hitType == HitType.DOUBLE) {
            return advanceDouble();
        }
        if (hitType == HitType.TRIPLE) {
            return advanceTriple();
        }
        return advanceHomerun();
    }

    public void clearRunners() {
        this.first = false;
        this.second = false;
        this.third = false;
    }

    public boolean hasRunnerOnThird() {
        return third;
    }

    public int scoreThirdRunner() {
        third = false;
        return 1;
    }

    public BaseRunnerStatus getRunnerStatus() {
        return new BaseRunnerStatus(first, second, third);
    }

    private int advanceSingle() {
        int score = calculateScore(false, false, true);
        third = second;
        second = first;
        first = true;
        return score;
    }

    private int advanceDouble() {
        int score = calculateScore(false, true, true);
        third = first;
        second = true;
        first = false;
        return score;
    }

    private int advanceTriple() {
        int score = calculateScore(true, true, true);
        third = true;
        second = false;
        first = false;
        return score;
    }

    private int advanceHomerun() {
        int score = 1 + calculateScore(true, true, true);
        clearRunners();
        return score;
    }

    private int calculateScore(boolean isFirstScoring, boolean isSecondScoring, boolean isThirdScoring) {
        int score = 0;
        if (isThirdScoring && third) {
            score += 1;
        }
        if (isSecondScoring && second) {
            score += 1;
        }
        if (isFirstScoring && first) {
            score += 1;
        }
        return score;
    }
}
