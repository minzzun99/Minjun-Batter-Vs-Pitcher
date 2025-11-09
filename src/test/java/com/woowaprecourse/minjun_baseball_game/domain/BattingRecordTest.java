package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BattingRecordTest {
    @Test
    @DisplayName("타자 기록 생성 확인 테스트")
    void 타자_기록_생성() {
        BattingRecord record = new BattingRecord("강민준");
        assertThat(record.getPlayerName()).isEqualTo("강민준");
    }

    @Test
    @DisplayName("타율 확인 테스트")
    void 타율_확인() {
        BattingRecord record = new BattingRecord("강민준");
        double average = (double) 61 / 166;
        assertThat(record.getBattingAverage()).isEqualTo(average);
    }

    @Test
    @DisplayName("안타 종류 결정 테스트")
    void 안타_종류_결정() {
        BattingRecord record = new BattingRecord("강민준");
        HitType hitType = record.decideHitType();

        assertThat(hitType).isIn(HitType.SINGLE, HitType.DOUBLE, HitType.TRIPLE, HitType.HOMERUN);
    }
}
