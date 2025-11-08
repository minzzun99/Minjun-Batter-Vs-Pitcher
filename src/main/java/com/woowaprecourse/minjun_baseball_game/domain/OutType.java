package com.woowaprecourse.minjun_baseball_game.domain;

import lombok.Getter;

@Getter
public enum OutType {
    STRIKE_OUT("삼진 아웃", false),
    GROUND_OUT("땅볼 아웃", false),
    FLY_OUT("플라이 아웃", false),
    SACRIFICE_FLY("희생 플라이", true);

    private final String outName;
    private final boolean runnerAdvance;

    OutType(String outName, boolean runnerAdvance) {
        this.outName = outName;
        this.runnerAdvance = runnerAdvance;
    }
}
