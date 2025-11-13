package com.woowaprecourse.minjun_baseball_game.dto;

import com.woowaprecourse.minjun_baseball_game.domain.BaseballGame;
import com.woowaprecourse.minjun_baseball_game.domain.GameMode;

public record GameStatusResponse(
        GameMode gameMode,
        CountDto count,
        BaseRunnerDto baseRunners,
        ScoreBoardDto scoreBoard,
        boolean gameOver
) {
    public static GameStatusResponse from(BaseballGame game) {
        return new GameStatusResponse(
                game.getGameMode(),
                new CountDto(game.getCount().getStrike(), game.getCount().getOut()),
                BaseRunnerDto.from(game.getBaseRunnerStatus()),
                ScoreBoardDto.from(game.getScoreBoard()),
                game.isGameOver()
        );
    }
}
