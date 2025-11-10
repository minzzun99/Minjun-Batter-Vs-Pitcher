package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BaseRunnerTest {
    @Nested
    @DisplayName("초기화 테스트")
    class InitializeTest {
        @Test
        @DisplayName("초기 상태 확인 테스트")
        void 초기_상태_확인() {
            BaseRunner baseRunner = new BaseRunner();
            BaseRunnerStatus status = baseRunner.getRunnerStatus();

            assertThat(status.first()).isFalse();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isFalse();
        }

        @Test
        @DisplayName("생성자로 주자 설정 테스트")
        void 생성자로_주자_설정() {
            BaseRunner baseRunner = new BaseRunner(true, false, true);
            BaseRunnerStatus status = baseRunner.getRunnerStatus();

            assertThat(status.first()).isTrue();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isTrue();
        }
    }

    @Nested
    @DisplayName("1루타 진루 테스트")
    class SingleAdvanceTest {
        @Test
        @DisplayName("주자 없을 때 1루타 진루 테스트")
        void 주자_없이_일루타() {
            BaseRunner baseRunner = new BaseRunner();
            int score = baseRunner.advance(HitType.SINGLE);
            assertThat(score).isEqualTo(0);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isTrue();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isFalse();
        }

        @Test
        @DisplayName("1루 주자 있을 때 1루타 진루 테스트")
        void 일루주자_일루타() {
            BaseRunner baseRunner = new BaseRunner(true, false, false);
            int score = baseRunner.advance(HitType.SINGLE);
            assertThat(score).isEqualTo(0);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isTrue();
            assertThat(status.second()).isTrue();
            assertThat(status.third()).isFalse();
        }

        @Test
        @DisplayName("2루 주자 있을 때 1루타 진루 테스트")
        void 이루주자_일루타() {
            BaseRunner baseRunner = new BaseRunner(false, true, false);
            int score = baseRunner.advance(HitType.SINGLE);
            assertThat(score).isEqualTo(0);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isTrue();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isTrue();
        }

        @Test
        @DisplayName("3루 주자 있을 때 1루타 1득점 확인 테스트")
        void 삼루주자_일루타_일득점() {
            BaseRunner baseRunner = new BaseRunner(false, false, true);
            int score = baseRunner.advance(HitType.SINGLE);
            assertThat(score).isEqualTo(1);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isTrue();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isFalse();
        }

        @Test
        @DisplayName("만루 상황 1루타 1득점 확인 테스트")
        void 만루_일루타_일득점() {
            BaseRunner baseRunner = new BaseRunner(true, true, true);
            int score = baseRunner.advance(HitType.SINGLE);
            assertThat(score).isEqualTo(1);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isTrue();
            assertThat(status.second()).isTrue();
            assertThat(status.third()).isTrue();
        }
    }

    @Nested
    @DisplayName("2루타 진루 테스트")
    class DoubleAdvanceTest {
        @Test
        @DisplayName("주자 없을 때 2루타 진루 테스트")
        void 주자_없이_이루타() {
            BaseRunner baseRunner = new BaseRunner();
            int score = baseRunner.advance(HitType.DOUBLE);
            assertThat(score).isEqualTo(0);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isTrue();
            assertThat(status.third()).isFalse();
        }

        @Test
        @DisplayName("1루 주자 있을 때 2루타 진루 테스트")
        void 일루주자_이루타() {
            BaseRunner baseRunner = new BaseRunner(true, false, false);
            int score = baseRunner.advance(HitType.DOUBLE);
            assertThat(score).isEqualTo(0);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isTrue();
            assertThat(status.third()).isTrue();
        }

        @Test
        @DisplayName("2루 주자 있을 때 2루타 1득점 확인 테스트")
        void 이루주자_이루타_일득점() {
            BaseRunner baseRunner = new BaseRunner(false, true, false);
            int score = baseRunner.advance(HitType.DOUBLE);
            assertThat(score).isEqualTo(1);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isTrue();
            assertThat(status.third()).isFalse();
        }

        @Test
        @DisplayName("3루 주자 있을 때 2루타 1득점 확인 테스트")
        void 삼루주자_이루타_일득점() {
            BaseRunner baseRunner = new BaseRunner(false, false, true);
            int score = baseRunner.advance(HitType.DOUBLE);
            assertThat(score).isEqualTo(1);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isTrue();
            assertThat(status.third()).isFalse();
        }

        @Test
        @DisplayName("만루 상황 2루타 2득점 확인 테스트")
        void 만루_이루타_이득점() {
            BaseRunner baseRunner = new BaseRunner(true, true, true);
            int score = baseRunner.advance(HitType.DOUBLE);
            assertThat(score).isEqualTo(2);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isTrue();
            assertThat(status.third()).isTrue();
        }
    }

    @Nested
    @DisplayName("3루타 진루 테스트")
    class TripleAdvanceTest {
        @Test
        @DisplayName("주자 없을 때 3루타 진루 테스트")
        void 주자_없이_삼루타() {
            BaseRunner baseRunner = new BaseRunner();
            int score = baseRunner.advance(HitType.TRIPLE);
            assertThat(score).isEqualTo(0);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isTrue();
        }

        @Test
        @DisplayName("1루 주자 있을 때 3루타 1득점 확인 테스트")
        void 일루주자_삼루타_일득점() {
            BaseRunner baseRunner = new BaseRunner(true, false, false);
            int score = baseRunner.advance(HitType.TRIPLE);
            assertThat(score).isEqualTo(1);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isTrue();
        }

        @Test
        @DisplayName("만루 상황 3루타 3득점 확인 테스트")
        void 만루_삼루타_삼득점() {
            BaseRunner baseRunner = new BaseRunner(true, true, true);
            int score = baseRunner.advance(HitType.TRIPLE);
            assertThat(score).isEqualTo(3);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isTrue();
        }
    }

    @Nested
    @DisplayName("홈런 진루 테스트")
    class HomerunAdvanceTest {
        @Test
        @DisplayName("솔로 홈런 1득점 확인 테스트")
        void 솔로_홈런() {
            BaseRunner baseRunner = new BaseRunner();
            int score = baseRunner.advance(HitType.HOMERUN);
            assertThat(score).isEqualTo(1);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isFalse();
        }

        @Test
        @DisplayName("투런 홈런 2득점 확인 테스트")
        void 투런_홈런() {
            BaseRunner baseRunner = new BaseRunner(true, false, false);
            int score = baseRunner.advance(HitType.HOMERUN);
            assertThat(score).isEqualTo(2);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isFalse();
        }

        @Test
        @DisplayName("쓰리런 홈런 3득점 확인 테스트")
        void 쓰리런_홈런() {
            BaseRunner baseRunner = new BaseRunner(true, false, true);
            int score = baseRunner.advance(HitType.HOMERUN);
            assertThat(score).isEqualTo(3);

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isFalse();
        }

        @Test
        @DisplayName("만루 홈런 4득점 확인 테스트")
        void 만루_홈런() {
            BaseRunner baseRunner = new BaseRunner(true, true, true);
            int score = baseRunner.advance(HitType.HOMERUN);

            assertThat(score).isEqualTo(4);
            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isFalse();
        }
    }

    @Nested
    @DisplayName("기능 테스트")
    class FunctionTest {
        @Test
        @DisplayName("희생플라이 득점 확인 테스트")
        void 희생플라이_득점() {
            BaseRunner baseRunner = new BaseRunner(false, false, true);
            int score = baseRunner.scoreThirdRunner();

            assertThat(score).isEqualTo(1);
            assertThat(baseRunner.hasRunnerOnThird()).isFalse();
        }

        @Test
        @DisplayName("주자 클리어 테스트")
        void 주자_클리어() {
            BaseRunner baseRunner = new BaseRunner(true, true, true);
            baseRunner.clearRunners();

            BaseRunnerStatus status = baseRunner.getRunnerStatus();
            assertThat(status.first()).isFalse();
            assertThat(status.second()).isFalse();
            assertThat(status.third()).isFalse();
        }

        @Test
        @DisplayName("3루 주자 확인 테스트")
        void 삼루_주자_확인() {
            BaseRunner baseRunner = new BaseRunner(false, false, true);

            assertThat(baseRunner.hasRunnerOnThird()).isTrue();
        }
    }
}
