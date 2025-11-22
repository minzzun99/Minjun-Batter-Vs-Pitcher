package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.RandomNumberGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PitchResultJudgeTest {
    private static final String PLAYER_NAME = "강민준";

    private static final int TOTAL_AT_BATS = 100;
    private static final int SINGLES = 25;
    private static final int DOUBLES = 10;
    private static final int TRIPLES = 5;
    private static final int HOMERUNS = 10;

    private BattingRecord createTestRecord(NumberGenerator generator) {
        return new BattingRecord(PLAYER_NAME, TOTAL_AT_BATS, SINGLES, DOUBLES, TRIPLES, HOMERUNS, generator);
    }

    BattingRecord battingRecord = createTestRecord(() -> 0.6);

    private final StrikeZone strikeZone = new StrikeZone(new Zone(1), new Zone(9));

    @Nested
    @DisplayName("일반존 테스트")
    class NormalZoneTest {
        private final Zone normalZone = new Zone(5);

        @Test
        @DisplayName("일반존 안타 결과 판정 테스트")
        void 안타_결과_판정() {
            NumberGenerator generator = () -> 0.3;
            PitchResultJudge resultJudge = new PitchResultJudge(battingRecord, generator);

            PitchResult result = resultJudge.judge(normalZone, strikeZone);

            assertThat(result.isHit()).isTrue();
            assertThat(result.getHitType()).isEqualTo(HitType.DOUBLE);
        }

        @Test
        @DisplayName("일반존 헛스윙 결과 판정 테스트")
        void 헛스윙_결과_판정() {
            NumberGenerator generator = () -> 0.6;
            PitchResultJudge resultJudge = new PitchResultJudge(battingRecord, generator);

            PitchResult result = resultJudge.judge(normalZone, strikeZone);

            assertThat(result.isSwingAndMiss()).isTrue();
        }

        @Test
        @DisplayName("일반존 아웃 결과 판정 테스트")
        void 아웃_결과_판정() {
            NumberGenerator generator = () -> 0.9;
            PitchResultJudge resultJudge = new PitchResultJudge(battingRecord, generator);

            PitchResult result = resultJudge.judge(normalZone, strikeZone);

            assertThat(result.isOut()).isTrue();
            // 랜덤값 0.5 초과는 FLY_OUT
            assertThat(result.getOutType()).isEqualTo(OutType.FLY_OUT);
        }
    }

    @Nested
    @DisplayName("핫존 테스트")
    class HotZoneTest {
        private final Zone hotZone = new Zone(1);

        @Test
        @DisplayName("핫존 안타 결과 판정 테스트")
        void 안타_결과_판정() {
            NumberGenerator generator = () -> 0.6;
            PitchResultJudge resultJudge = new PitchResultJudge(battingRecord, generator);

            PitchResult result = resultJudge.judge(hotZone, strikeZone);

            assertThat(result.isHit()).isTrue();
            assertThat(result.getHitType()).isEqualTo(HitType.DOUBLE);
        }

        @Test
        @DisplayName("핫존 헛스윙 결과 판정 테스트")
        void 헛스윙_결과_판정() {
            NumberGenerator generator = () -> 0.8;
            PitchResultJudge resultJudge = new PitchResultJudge(battingRecord, generator);

            PitchResult result = resultJudge.judge(hotZone, strikeZone);

            assertThat(result.isSwingAndMiss()).isTrue();
        }

        @Test
        @DisplayName("핫존 아웃 결과 판정 테스트")
        void 아웃_결과_판정() {
            NumberGenerator generator = () -> 0.9;
            PitchResultJudge resultJudge = new PitchResultJudge(battingRecord, generator);

            PitchResult result = resultJudge.judge(hotZone, strikeZone);

            assertThat(result.isOut()).isTrue();
            // 랜덤값 0.5 초과는 FLY_OUT
            assertThat(result.getOutType()).isEqualTo(OutType.FLY_OUT);
        }
    }

    @Nested
    @DisplayName("콜드존 테스트")
    class ColdZoneTest {
        private final Zone coldZone = new Zone(9);

        @Test
        @DisplayName("콜드존 안타 결과 판정 테스트")
        void 안타_결과_판정() {
            NumberGenerator generator = () -> 0.1;
            PitchResultJudge resultJudge = new PitchResultJudge(battingRecord, generator);

            PitchResult result = resultJudge.judge(coldZone, strikeZone);

            assertThat(result.isHit()).isTrue();
            assertThat(result.getHitType()).isEqualTo(HitType.DOUBLE);
        }

        @Test
        @DisplayName("콜드존 헛스윙 결과 판정 테스트")
        void 헛스윙_결과_판정() {
            NumberGenerator generator = () -> 0.5;
            PitchResultJudge resultJudge = new PitchResultJudge(battingRecord, generator);

            PitchResult result = resultJudge.judge(coldZone, strikeZone);

            assertThat(result.isSwingAndMiss()).isTrue();
        }

        @Test
        @DisplayName("콜드존 아웃 결과 판정 테스트")
        void 아웃_결과_판정() {
            NumberGenerator generator = () -> 0.7;
            PitchResultJudge resultJudge = new PitchResultJudge(battingRecord, generator);

            PitchResult result = resultJudge.judge(coldZone, strikeZone);

            assertThat(result.isOut()).isTrue();
            // 랜덤값 0.5 초과는 FLY_OUT
            assertThat(result.getOutType()).isEqualTo(OutType.FLY_OUT);
        }
    }
}
