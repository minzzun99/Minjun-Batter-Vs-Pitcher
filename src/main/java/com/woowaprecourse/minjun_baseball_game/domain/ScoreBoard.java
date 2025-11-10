package com.woowaprecourse.minjun_baseball_game.domain;

import lombok.Getter;

public class ScoreBoard {
    @Getter
    private int myScore;
    @Getter
    private final int computerScore;

    public ScoreBoard(int myScore, int computerScore) {
        this.myScore = myScore;
        this.computerScore = computerScore;
    }

    public void addMyScore(int score) {
        this.myScore += score;
    }

    public boolean isWin() {
        return myScore > computerScore;
    }

    public boolean isDraw() {
        return myScore == computerScore;
    }

    public boolean isLose() {
        return myScore < computerScore;
    }
}
