package com.woowaprecourse.minjun_baseball_game.domain;

public enum HitType {
    SINGLE(1),
    DOUBLE(2),
    TRIPLE(3),
    HOMERUN(4);

    private final int base;

    HitType(int base) {
        this.base = base;
    }

    public int getBase() {
        return base;
    }
}
