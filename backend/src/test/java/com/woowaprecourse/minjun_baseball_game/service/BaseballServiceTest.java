package com.woowaprecourse.minjun_baseball_game.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowaprecourse.minjun_baseball_game.domain.BaseballGame;
import com.woowaprecourse.minjun_baseball_game.domain.GameMode;
import com.woowaprecourse.minjun_baseball_game.domain.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BaseballServiceTest {
    private Player createTestPlayer() {
        return new Player("강민준", 100, 25, 10, 5, 10);
    }

    @Test
    @DisplayName("투수 모드 게임 생성 및 gameId 반환 테스트")
    void 투수모드_게임생성() {
        BaseballGameService gameService = new BaseballGameService();
        String gameId = gameService.createGame(GameMode.PITCHER, createTestPlayer());

        assertThat(gameId).isNotNull();
        assertThat(gameId).isNotEmpty();
    }

    @Test
    @DisplayName("타자 모드 게임 생성 및 gameId 반환 테스트")
    void 타자모드_게임생성() {
        BaseballGameService gameService = new BaseballGameService();
        String gameId = gameService.createGame(GameMode.BATTER, createTestPlayer());

        assertThat(gameId).isNotNull();
        assertThat(gameId).isNotEmpty();
    }

    @Test
    @DisplayName("투수모드 gameId 게임 조회 테스트")
    void 투수모드_게임_조회() {
        BaseballGameService service = new BaseballGameService();
        String gameId = service.createGame(GameMode.PITCHER, createTestPlayer());
        BaseballGame game = service.getGame(gameId);

        assertThat(game).isNotNull();
        assertThat(game.getGameMode()).isEqualTo(GameMode.PITCHER);
        assertThat(game.getScoreBoard().getMyScore()).isEqualTo(2);
    }

    @Test
    @DisplayName("타자모드 gameId 게임 조회 테스트")
    void 타자모드_게임_조회() {
        BaseballGameService service = new BaseballGameService();
        String gameId = service.createGame(GameMode.BATTER, createTestPlayer());
        BaseballGame game = service.getGame(gameId);

        assertThat(game).isNotNull();
        assertThat(game.getGameMode()).isEqualTo(GameMode.BATTER);
        assertThat(game.getScoreBoard().getMyScore()).isEqualTo(0);
    }

    @Test
    @DisplayName("존재하지 않는 gameId 조회 예외 발생 테스트")
    void 존재하지_않는_게임_예외_발생() {
        BaseballGameService service = new BaseballGameService();

        assertThatThrownBy(() -> service.getGame("invalid-test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 게임입니다");
    }

    @Test
    @DisplayName("여러 게임 동시 생성 확인 테스트")
    void 여러_게임_생성() {
        BaseballGameService service = new BaseballGameService();

        String gameId1 = service.createGame(GameMode.PITCHER, createTestPlayer());
        String gameId2 = service.createGame(GameMode.BATTER, createTestPlayer());

        BaseballGame game1 = service.getGame(gameId1);
        BaseballGame game2 = service.getGame(gameId2);

        assertThat(game1.getGameMode()).isEqualTo(GameMode.PITCHER);
        assertThat(game2.getGameMode()).isEqualTo(GameMode.BATTER);
        assertThat(gameId1).isNotEqualTo(gameId2);
    }
}
