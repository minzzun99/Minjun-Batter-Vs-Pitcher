package com.woowaprecourse.minjun_baseball_game.dto;

import com.woowaprecourse.minjun_baseball_game.domain.ScoreBoard;

public record ScoreBoardDto(int myScore, int computerScore) {

    public static ScoreBoardDto from(ScoreBoard scoreBoard) {
        return new ScoreBoardDto(scoreBoard.getMyScore(), scoreBoard.getComputerScore());
    }
}
