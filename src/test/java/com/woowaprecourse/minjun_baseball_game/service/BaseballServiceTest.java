package com.woowaprecourse.minjun_baseball_game.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowaprecourse.minjun_baseball_game.domain.BaseballGame;
import com.woowaprecourse.minjun_baseball_game.domain.GameMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BaseballServiceTest {
    @Test
    @DisplayName("투수 모드 게임 생성 테스트")
    void 투수모드_게임생성() {
        BaseballGameService gameService = new BaseballGameService();
        BaseballGame baseballGame = gameService.createGame(GameMode.PITCHER);

        assertThat(baseballGame.getGameMode()).isEqualTo(GameMode.PITCHER);
        assertThat(baseballGame.getScoreBoard().getMyScore()).isEqualTo(2);
    }

    @Test
    @DisplayName("타자 모드 게임 생성 테스트")
    void 타자모드_게임생성() {
        BaseballGameService gameService = new BaseballGameService();
        BaseballGame baseballGame = gameService.createGame(GameMode.BATTER);

        assertThat(baseballGame.getGameMode()).isEqualTo(GameMode.BATTER);
        assertThat(baseballGame.getScoreBoard().getMyScore()).isEqualTo(0);
    }
}
