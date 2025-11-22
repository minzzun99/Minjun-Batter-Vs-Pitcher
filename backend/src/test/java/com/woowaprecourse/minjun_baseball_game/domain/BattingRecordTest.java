package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.RandomNumberGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BattingRecordTest {
    private static final String PLAYER_NAME = "강민준";

    private static final int TOTAL_AT_BATS = 100;
    private static final int SINGLES = 25;
    private static final int DOUBLES = 10;
    private static final int TRIPLES = 5;
    private static final int HOMERUNS = 10;

    private BattingRecord createTestRecord(NumberGenerator generator) {
        return new BattingRecord(PLAYER_NAME, TOTAL_AT_BATS, SINGLES, DOUBLES, TRIPLES, HOMERUNS, generator);
    }

    NumberGenerator numberGenerator = new RandomNumberGenerator();
    BattingRecord record = createTestRecord(numberGenerator);

    @Test
    @DisplayName("타자 기록 생성 확인 테스트")
    void 타자_기록_생성() {
        assertThat(record.getPlayerName()).isEqualTo("강민준");
    }

    @Test
    @DisplayName("타율 확인 테스트")
    void 타율_확인() {
        assertThat(record.getBattingAverage()).isEqualTo(0.5);
    }

    @Test
    @DisplayName("안타 종류 결정 테스트")
    void 안타_종류_결정() {
        HitType hitType = record.decideHitType();
        assertThat(hitType).isIn(HitType.SINGLE, HitType.DOUBLE, HitType.TRIPLE, HitType.HOMERUN);
    }
}
