package com.woowaprecourse.minjun_baseball_game.domain;

import lombok.Getter;

@Getter
public enum OutType {
    STRIKE_OUT("삼진 아웃"),
    GROUND_OUT("땅볼 아웃"),
    FLY_OUT("플라이 아웃");

    private final String outName;

    OutType(String outName) {
        this.outName = outName;
    }
}
