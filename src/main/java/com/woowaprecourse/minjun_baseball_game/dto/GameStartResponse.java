package com.woowaprecourse.minjun_baseball_game.dto;

import com.woowaprecourse.minjun_baseball_game.domain.GameMode;

public record GameStartResponse(String gameId, GameMode gameMode, ScoreBoardDto scoreBoard) {

    public static GameStartResponse from(String gameId, GameMode mode, ScoreBoardDto scoreBoard) {
        return new GameStartResponse(gameId, mode, scoreBoard);
    }
}
