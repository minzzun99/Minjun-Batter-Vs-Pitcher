package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GameStateTest {
    @Nested
    @DisplayName("초기 상태 확인 테스트")
    class InitializeTest {
        @Test
        @DisplayName("투수 모드 초기 상태 확인 테스트")
        void 투수모드_초기_상태() {
            GameState gameState = new GameState(GameMode.PITCHER, () -> 0.5);

            assertThat(gameState.getScoreBoard().getMyScore()).isEqualTo(2);
            assertThat(gameState.getScoreBoard().getComputerScore()).isEqualTo(0);
            assertThat(gameState.getCount().getStrike()).isEqualTo(0);
            assertThat(gameState.getCount().getOut()).isEqualTo(0);
        }

        @Test
        @DisplayName("타자 모드 초기 상태 확인 테스트")
        void 타자모드_초기_상태() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.5);

            assertThat(gameState.getScoreBoard().getMyScore()).isEqualTo(0);
            assertThat(gameState.getScoreBoard().getComputerScore()).isEqualTo(2);
            assertThat(gameState.getCount().getStrike()).isEqualTo(0);
            assertThat(gameState.getCount().getOut()).isEqualTo(0);
        }

        @Test
        @DisplayName("초기 상태 주자 확인 테스트")
        void 초기_상태_주자() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.5);
            BaseRunnerStatus baseRunnerStatus = gameState.getBaseRunnerStatus();

            assertThat(baseRunnerStatus.first()).isFalse();
            assertThat(baseRunnerStatus.second()).isFalse();
            assertThat(baseRunnerStatus.third()).isFalse();
        }
    }

    @Nested
    @DisplayName("안타 처리 테스트")
    class HitTest {
        @Test
        @DisplayName("타자모드 주자 진루 확인 테스트")
        void 타자모드_주자_진루_확인() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.5);
            gameState.update(PitchResult.hit(HitType.SINGLE));
            BaseRunnerStatus baseRunnerStatus = gameState.getBaseRunnerStatus();

            assertThat(baseRunnerStatus.first()).isTrue();
        }

        @Test
        @DisplayName("타자모드 득점 확인 테스트")
        void 타자모드_득점_확인() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.5);
            gameState.update(PitchResult.hit(HitType.HOMERUN));

            assertThat(gameState.getScoreBoard().getMyScore()).isEqualTo(1);
        }

        @Test
        @DisplayName("타자모드 안타 시 스트라이크 리셋 확인 테스트")
        void 타자모드_스트라이크_리셋_확인() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.5);
            gameState.update(PitchResult.swingAndMiss());
            gameState.update(PitchResult.swingAndMiss());
            assertThat(gameState.getCount().getStrike()).isEqualTo(2);

            gameState.update(PitchResult.hit(HitType.DOUBLE));
            assertThat(gameState.getCount().getStrike()).isEqualTo(0);
        }

        @Test
        @DisplayName("투수모드 안타 시 진루 확인 테스트")
        void 투수모드_안타_진루_확인() {
            GameState gameState = new GameState(GameMode.PITCHER, () -> 0.5);
            gameState.update(PitchResult.hit(HitType.SINGLE));
            BaseRunnerStatus baseRunnerStatus = gameState.getBaseRunnerStatus();

            assertThat(baseRunnerStatus.first()).isTrue();
        }

        @Test
        @DisplayName("투수모드 안타 시 컴퓨터 점수 증가 확인 테스트")
        void 투수모드_안타_점수_증가() {
            GameState gameState = new GameState(GameMode.PITCHER, () -> 0.5);
            gameState.update(PitchResult.hit(HitType.HOMERUN));

            assertThat(gameState.getScoreBoard().getComputerScore()).isEqualTo(1);
        }

        @Test
        @DisplayName("투수모드 안타 스트라이크 리셋 확인 테스트")
        void 투수모드_안타_스트라이크_리셋_확인() {
            GameState gameState = new GameState(GameMode.PITCHER, () -> 0.5);
            gameState.update(PitchResult.swingAndMiss());
            gameState.update(PitchResult.swingAndMiss());
            assertThat(gameState.getCount().getStrike()).isEqualTo(2);

            gameState.update(PitchResult.hit(HitType.DOUBLE));
            assertThat(gameState.getCount().getStrike()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("아웃 처리 테스트")
    class OutTest {
        @Test
        @DisplayName("타자모드 아웃 카운트 증가 확인 테스트")
        void 타자모드_아웃_카운트_증가_확인() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.5);
            gameState.update(PitchResult.out(OutType.FLY_OUT));

            assertThat(gameState.getCount().getOut()).isEqualTo(1);
        }

        @Test
        @DisplayName("타자모드 3아웃 주자 초기화 확인 테스트")
        void 타자모드_쓰리아웃_주자_초기화() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.5);

            gameState.update(PitchResult.hit(HitType.SINGLE));
            assertThat(gameState.getBaseRunnerStatus().first()).isTrue();

            gameState.update(PitchResult.out(OutType.GROUND_OUT));
            gameState.update(PitchResult.out(OutType.GROUND_OUT));
            gameState.update(PitchResult.out(OutType.GROUND_OUT));

            BaseRunnerStatus status = gameState.getBaseRunnerStatus();
            assertThat(status.first()).isFalse();
        }

        @Test
        @DisplayName("타자모드 아웃 시 스트라이크 리셋 확인 테스트")
        void 타자모드_아웃_스트라이크_리셋() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.5);

            gameState.update(PitchResult.swingAndMiss());
            assertThat(gameState.getCount().getStrike()).isEqualTo(1);

            gameState.update(PitchResult.out(OutType.GROUND_OUT));
            assertThat(gameState.getCount().getStrike()).isEqualTo(0);
        }

        @Test
        @DisplayName("투수모드 아웃 카운트 증가 확인 테스트")
        void 투수모드_아웃_카운트_증가_확인() {
            GameState gameState = new GameState(GameMode.PITCHER, () -> 0.5);
            gameState.update(PitchResult.out(OutType.FLY_OUT));

            assertThat(gameState.getCount().getOut()).isEqualTo(1);
        }

        @Test
        @DisplayName("투수모드 3아웃 주자 초기화 확인 테스트")
        void 투수모드_쓰리아웃_주자_초기화() {
            GameState gameState = new GameState(GameMode.PITCHER, () -> 0.5);

            gameState.update(PitchResult.hit(HitType.SINGLE));
            assertThat(gameState.getBaseRunnerStatus().first()).isTrue();

            gameState.update(PitchResult.out(OutType.GROUND_OUT));
            gameState.update(PitchResult.out(OutType.GROUND_OUT));
            gameState.update(PitchResult.out(OutType.GROUND_OUT));

            BaseRunnerStatus status = gameState.getBaseRunnerStatus();
            assertThat(status.first()).isFalse();
        }

        @Test
        @DisplayName("투수모드 아웃 시 스트라이크 리셋 확인 테스트")
        void 투수모드_아웃_스트라이크_리셋() {
            GameState gameState = new GameState(GameMode.PITCHER, () -> 0.5);

            gameState.update(PitchResult.swingAndMiss());
            assertThat(gameState.getCount().getStrike()).isEqualTo(1);

            gameState.update(PitchResult.out(OutType.GROUND_OUT));
            assertThat(gameState.getCount().getStrike()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("희생플라이 테스트")
    class SacrificeFlyTest {
        @Test
        @DisplayName("희생플라이 성공 확인 테스트")
        void 희생플라이_성공_확인() {
            //30% 이하면 희생플라이 가능
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.1);

            // 3루 주자 배치
            gameState.update(PitchResult.hit(HitType.TRIPLE));
            assertThat(gameState.getBaseRunnerStatus().third()).isTrue();

            // 플라이 아웃
            gameState.update(PitchResult.out(OutType.FLY_OUT));

            assertThat(gameState.getScoreBoard().getMyScore()).isEqualTo(1);
            assertThat(gameState.getBaseRunnerStatus().third()).isFalse();
            assertThat(gameState.getCount().getOut()).isEqualTo(1);
        }

        @Test
        @DisplayName("희생플라이 실패 확인 테스트")
        void 희생플라이_실패_확인() {
            // 30% 초과 희생플라이 불가
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.4);

            // 3루 주자 배치
            gameState.update(PitchResult.hit(HitType.TRIPLE));
            assertThat(gameState.getBaseRunnerStatus().third()).isTrue();

            // 플라이 아웃
            gameState.update(PitchResult.out(OutType.FLY_OUT));

            assertThat(gameState.getScoreBoard().getMyScore()).isEqualTo(0);
            assertThat(gameState.getBaseRunnerStatus().third()).isTrue();
            assertThat(gameState.getCount().getOut()).isEqualTo(1);
        }

        @Test
        @DisplayName("3루주자 없이 희생플라이 불가 확인 테스트")
        void 삼루주자_없이_희생플라이_불가() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.1);
            assertThat(gameState.getBaseRunnerStatus().third()).isFalse();

            // 플라이 아웃
            gameState.update(PitchResult.out(OutType.FLY_OUT));

            assertThat(gameState.getScoreBoard().getMyScore()).isEqualTo(0);
            assertThat(gameState.getCount().getOut()).isEqualTo(1);
        }

        @Test
        @DisplayName("2아웃 희생플라이 불가 확인 테스트")
        void 투아웃_희생플라이_불가() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.1);

            // 3루 주자 배치
            gameState.update(PitchResult.hit(HitType.TRIPLE));
            assertThat(gameState.getBaseRunnerStatus().third()).isTrue();

            // 2아웃
            gameState.update(PitchResult.out(OutType.GROUND_OUT));
            gameState.update(PitchResult.out(OutType.GROUND_OUT));

            // 희생플라이 전 점수
            int scoreBefore = gameState.getScoreBoard().getMyScore();

            // 플라이 아웃
            gameState.update(PitchResult.out(OutType.FLY_OUT));

            assertThat(gameState.getScoreBoard().getMyScore()).isEqualTo(scoreBefore);
        }

        @Test
        @DisplayName("땅볼 아웃 희생플라이 불가 확인 테스트")
        void 땅볼_아웃_희생플라이_불가() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.1);

            // 3루 주자 배치
            gameState.update(PitchResult.hit(HitType.TRIPLE));
            assertThat(gameState.getBaseRunnerStatus().third()).isTrue();

            // 땅볼 아웃
            gameState.update(PitchResult.out(OutType.GROUND_OUT));

            assertThat(gameState.getScoreBoard().getMyScore()).isEqualTo(0);
            assertThat(gameState.getCount().getOut()).isEqualTo(1);
        }

        @Test
        @DisplayName("투수모드 희생플라이 성공 확인 테스트")
        void 투수모드_희생플라이_성공_확인() {
            //30% 이하면 희생플라이 가능
            GameState gameState = new GameState(GameMode.PITCHER, () -> 0.1);

            // 3루 주자 배치
            gameState.update(PitchResult.hit(HitType.TRIPLE));
            assertThat(gameState.getBaseRunnerStatus().third()).isTrue();

            // 플라이 아웃
            gameState.update(PitchResult.out(OutType.FLY_OUT));

            assertThat(gameState.getScoreBoard().getComputerScore()).isEqualTo(1);
            assertThat(gameState.getBaseRunnerStatus().third()).isFalse();
            assertThat(gameState.getCount().getOut()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("게임 종료 테스트")
    class GameOverTest {
        @Test
        @DisplayName("3아웃 시 게임 종료 테스트")
        void 쓰리아웃_게임종료() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.5);

            gameState.update(PitchResult.out(OutType.GROUND_OUT));
            gameState.update(PitchResult.out(OutType.GROUND_OUT));
            assertThat(gameState.isGameOver()).isFalse();

            gameState.update(PitchResult.out(OutType.GROUND_OUT));
            assertThat(gameState.isGameOver()).isTrue();
        }

        @Test
        @DisplayName("역전 시 게임 종료")
        void 역전_게임종료() {
            GameState gameState = new GameState(GameMode.BATTER, () -> 0.5);

            // 3점 홈런
            gameState.update(PitchResult.hit(HitType.SINGLE));
            gameState.update(PitchResult.hit(HitType.SINGLE));
            gameState.update(PitchResult.hit(HitType.SINGLE));
            assertThat(gameState.isGameOver()).isFalse();

            gameState.update(PitchResult.hit(HitType.HOMERUN));

            assertThat(gameState.getScoreBoard().isWin()).isTrue();
            assertThat(gameState.isGameOver()).isTrue();
            assertThat(gameState.getCount().getOut()).isEqualTo(0);
        }
    }
}
