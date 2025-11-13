package com.woowaprecourse.minjun_baseball_game.domain;

public class Count {
    private static final int MAX_STRIKE = 3;
    private static final int MAX_OUT = 3;

    private int strike;
    private int out;

    public Count() {
        this.strike = 0;
        this.out = 0;
    }

    public void addStrike() {
        strike++;

        if (isStrikeOut()) {
            addOut();
            resetStrike();
        }
    }

    public void addOut() {
        if (isThreeOut()) {
            throw new IllegalArgumentException("이미 3아웃입니다.");
        }
        out++;
    }

    public void resetStrike() {
        this.strike = 0;
    }

    public boolean isStrikeOut() {
        return strike >= MAX_STRIKE;
    }

    public boolean isThreeOut() {
        return out >= MAX_OUT;
    }

    public int getStrike() {
        return strike;
    }

    public int getOut() {
        return out;
    }
}
