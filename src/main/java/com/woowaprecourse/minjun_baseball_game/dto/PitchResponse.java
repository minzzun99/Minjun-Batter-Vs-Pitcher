package com.woowaprecourse.minjun_baseball_game.dto;

import com.woowaprecourse.minjun_baseball_game.domain.BaseballGame;
import com.woowaprecourse.minjun_baseball_game.domain.Count;
import com.woowaprecourse.minjun_baseball_game.domain.PitchResult;

public record PitchResponse (
        PitchResultDto pitchResult,
        CountDto count,
        BaseRunnerDto runners,
        ScoreBoardDto scoreBoard,
        boolean isGameOver
) {
    public static PitchResponse from(PitchResult pitchResult, BaseballGame game) {
        return new PitchResponse(
                PitchResultDto.from(pitchResult),
                CountDto.from(game.getCount()),
                BaseRunnerDto.from(game.getBaseRunnerStatus()),
                ScoreBoardDto.from(game.getScoreBoard()),
                game.isGameOver()
        );
    }
}
