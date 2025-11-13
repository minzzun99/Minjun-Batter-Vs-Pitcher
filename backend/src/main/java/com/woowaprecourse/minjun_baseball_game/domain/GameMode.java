package com.woowaprecourse.minjun_baseball_game.domain;

public enum GameMode {
    PITCHER("투수 모드", 2, 0),
    BATTER("타자 모드", 0, 2);

    private final String modeName;
    private final int initMyScore;
    private final int initComputerScore;

    GameMode(String modeName, int initMyScore, int initComputerScore) {
        this.modeName = modeName;
        this.initMyScore = initMyScore;
        this.initComputerScore = initComputerScore;
    }

    public String getModeName() {
        return modeName;
    }

    public int getInitMyScore() {
        return initMyScore;
    }

    public int getInitComputerScore() {
        return initComputerScore;
    }
}
