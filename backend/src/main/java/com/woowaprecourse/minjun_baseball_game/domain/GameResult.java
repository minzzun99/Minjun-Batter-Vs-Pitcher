package com.woowaprecourse.minjun_baseball_game.domain;

public enum GameResult {
    WIN("승리"),
    LOSE("패배"),
    DRAW("무승부");

    private final String resultName;

    GameResult(String resultName) {
        this.resultName = resultName;
    }

    public String getResultName() {
        return resultName;
    }
}
