package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.RandomNumberGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BattingRecordTest {
    NumberGenerator numberGenerator = new RandomNumberGenerator();
    BattingRecord record = new BattingRecord("강민준", numberGenerator);

    @Test
    @DisplayName("타자 기록 생성 확인 테스트")
    void 타자_기록_생성() {
        assertThat(record.getPlayerName()).isEqualTo("강민준");
    }

    @Test
    @DisplayName("타율 확인 테스트")
    void 타율_확인() {
        double average = (double) 61 / 166;
        assertThat(record.getBattingAverage()).isEqualTo(average);
    }

    @Test
    @DisplayName("안타 종류 결정 테스트")
    void 안타_종류_결정() {
        HitType hitType = record.decideHitType();
        assertThat(hitType).isIn(HitType.SINGLE, HitType.DOUBLE, HitType.TRIPLE, HitType.HOMERUN);
    }
}
