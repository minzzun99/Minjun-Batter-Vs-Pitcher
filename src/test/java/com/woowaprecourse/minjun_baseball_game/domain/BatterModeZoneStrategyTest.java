package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.BatterModeZoneStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BatterModeZoneStrategyTest {
    @Test
    @DisplayName("콜드존 랜덤 생성 테스트")
    void 콜드존_랜덤_생성() {
        BatterModeZoneStrategy batterModeZoneStrategy = new BatterModeZoneStrategy();
        assertThatCode(() -> batterModeZoneStrategy.generate(2))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("핫존 생성 테스트")
    void 핫존_생성_확인() {
        BatterModeZoneStrategy batterModeZoneStrategy = new BatterModeZoneStrategy();
        StrikeZone strikeZone = batterModeZoneStrategy.generate(2);
        assertThat(strikeZone.isHotZone(new Zone(2)));
    }

    @Test
    @DisplayName("핫존 범위 초과 예외 발생 테스트")
    void 핫존_범위_초과_예외_발생() {
        BatterModeZoneStrategy batterModeZoneStrategy = new BatterModeZoneStrategy();
        assertThatThrownBy(() -> batterModeZoneStrategy.generate(100))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
