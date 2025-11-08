package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.PitcherModeZoneStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PitcherModeZoneStrategyTest {
    @Test
    @DisplayName("핫 존 콜드존 랜덤 생성 테스트")
    void 핫존_콜드존_랜덤_생성() {
        PitcherModeZoneStrategy pitcherModeZoneStrategy = new PitcherModeZoneStrategy();
        assertThatCode(() -> pitcherModeZoneStrategy.generate(1))
                .doesNotThrowAnyException();
    }
}
