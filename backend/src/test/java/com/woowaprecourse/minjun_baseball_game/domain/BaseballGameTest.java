package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.BatterModeZoneStrategy;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.PitcherModeZoneStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BaseballGameTest {
    private static final String PLAYER_NAME = "강민준";

    @Nested
    @DisplayName("초기 상태 확인 테스트")
    class InitializeTest {
        @Test
        @DisplayName("투수 모드 게임 생성 확인 테스트")
        void 투수모드_게임생성() {
            NumberGenerator generator = () -> 0.5;
            BattingRecord record = new BattingRecord(PLAYER_NAME, generator);
            ZoneRandomGenerator zoneRandomGenerator = new ZoneRandomGenerator(generator);
            BaseballGame gameService = new BaseballGame(GameMode.PITCHER, record,
                    new PitcherModeZoneStrategy(zoneRandomGenerator), generator, zoneRandomGenerator
            );

            assertThat(gameService.getGameMode()).isEqualTo(GameMode.PITCHER);
            assertThat(gameService.getScoreBoard().getMyScore()).isEqualTo(2);
            assertThat(gameService.getScoreBoard().getComputerScore()).isEqualTo(0);
        }

        @Test
        @DisplayName("타자 모드 게임 생성 확인 테스트")
        void 타자모드_게임생성() {
            NumberGenerator generator = () -> 0.5;
            BattingRecord record = new BattingRecord(PLAYER_NAME, generator);
            ZoneRandomGenerator zoneRandomGenerator = new ZoneRandomGenerator(generator);
            BaseballGame gameService = new BaseballGame(GameMode.BATTER, record,
                    new PitcherModeZoneStrategy(zoneRandomGenerator), generator, zoneRandomGenerator
            );

            assertThat(gameService.getGameMode()).isEqualTo(GameMode.BATTER);
            assertThat(gameService.getScoreBoard().getMyScore()).isEqualTo(0);
            assertThat(gameService.getScoreBoard().getComputerScore()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("투구 진행 테스트")
    class PlayPitchTest {
        @Test
        @DisplayName("정상 투구 진행 확인 테스트")
        void 정상_투구() {
            NumberGenerator generator = () -> 0.5;
            BattingRecord record = new BattingRecord(PLAYER_NAME, generator);
            ZoneRandomGenerator zoneRandomGenerator = new ZoneRandomGenerator(generator);
            BaseballGame gameService = new BaseballGame(GameMode.BATTER, record,
                    new BatterModeZoneStrategy(zoneRandomGenerator), generator, zoneRandomGenerator
            );

            assertThatCode(() -> gameService.playPitch(3))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("게임 종료 후 투구 예외 발생 테스트")
        void 게임종료_투구_예외_발생() {
            NumberGenerator generator = () -> 0.9;  // 항상 아웃
            BattingRecord record = new BattingRecord(PLAYER_NAME, generator);
            ZoneRandomGenerator zoneRandomGenerator = new ZoneRandomGenerator(generator);
            BaseballGame game = new BaseballGame(GameMode.BATTER, record,
                    new BatterModeZoneStrategy(zoneRandomGenerator), generator, zoneRandomGenerator
            );

            // 3아웃
            game.playPitch(5);
            game.playPitch(5);
            game.playPitch(5);

            assertThat(game.isGameOver()).isTrue();
            assertThatThrownBy(() -> game.playPitch(5))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("게임이 종료되었습니다.");
        }

        @Test
        @DisplayName("연속 투구 확인 테스트")
        void 연속_투구() {
            NumberGenerator generator = () -> 0.5;
            BattingRecord record = new BattingRecord(PLAYER_NAME, generator);
            ZoneRandomGenerator zoneRandomGenerator = new ZoneRandomGenerator(generator);
            BaseballGame game = new BaseballGame(GameMode.BATTER, record,
                    new BatterModeZoneStrategy(zoneRandomGenerator), generator, zoneRandomGenerator
            );

            game.playPitch(1);
            game.playPitch(2);
            game.playPitch(3);

            assertThat(game.isGameOver()).isFalse();
        }
    }

    @Nested
    @DisplayName("게임 종료 및 결과 테스트")
    class GameOverTest {
        @Test
        @DisplayName("3아웃 시 게임 종료 확인 테스트")
        void 쓰리아웃_게임종료() {
            NumberGenerator generator = () -> 0.99;
            BattingRecord record = new BattingRecord(PLAYER_NAME, generator);
            ZoneRandomGenerator zoneRandomGenerator = new ZoneRandomGenerator(generator);
            BaseballGame game = new BaseballGame(GameMode.BATTER, record,
                    new BatterModeZoneStrategy(zoneRandomGenerator), generator, zoneRandomGenerator
            );

            game.playPitch(5);
            game.playPitch(5);
            game.playPitch(5);

            assertThat(game.isGameOver()).isTrue();
        }

        @Test
        @DisplayName("게임 진행 중 결과 조회 예외 발생 테스트")
        void 게임진행중_결과조회_예외_발생() {
            NumberGenerator generator = () -> 0.5;
            BattingRecord record = new BattingRecord(PLAYER_NAME, generator);
            ZoneRandomGenerator zoneRandomGenerator = new ZoneRandomGenerator(generator);
            BaseballGame game = new BaseballGame(GameMode.BATTER, record,
                    new BatterModeZoneStrategy(zoneRandomGenerator), generator, zoneRandomGenerator
            );

            assertThatThrownBy(game::getResult)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("게임이 진행 중입니다.");
        }
    }
}
