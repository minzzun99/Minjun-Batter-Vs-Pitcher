package com.woowaprecourse.minjun_baseball_game.dto;

import com.woowaprecourse.minjun_baseball_game.domain.BaseballGame;
import com.woowaprecourse.minjun_baseball_game.domain.GameResult;

public record GameResultResponse(GameResult result, ScoreBoardDto scoreBoard, GameStatisticsDto gameStatistics) {

    public static GameResultResponse from(BaseballGame game) {
        return new GameResultResponse(game.getResult(), ScoreBoardDto.from(game.getScoreBoard()),
                GameStatisticsDto.from(game.getStatistics()));
    }
}
