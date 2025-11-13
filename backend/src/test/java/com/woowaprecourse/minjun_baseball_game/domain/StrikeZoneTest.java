package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StrikeZoneTest {
    @Test
    @DisplayName("StrikeZone 정상 생성 테스트")
    void 정상_생성() {
        Zone hotZone = new Zone(1);
        Zone coldZone = new Zone(2);

        assertThatCode(() -> new StrikeZone(hotZone, coldZone))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("StrikeZone 중복 숫자 예외 발생 테스트")
    void 중복_숫자_예외_발생() {
        Zone hotZone = new Zone(2);
        Zone coldZone = new Zone(2);

        assertThatThrownBy(() -> new StrikeZone(hotZone, coldZone))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("핫존 Null인 경우 예외 발생 테스트")
    void 핫존_Null_예외_발생() {
        Zone hotZone = null;
        Zone coldZone = new Zone(2);

        assertThatThrownBy(() -> new StrikeZone(hotZone, coldZone))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("콜드존 Null인 경우 예외 발생 테스트")
    void 콜드존_Null_예외_발생() {
        Zone hotZone = new Zone(1);
        Zone coldZone = null;

        assertThatThrownBy(() -> new StrikeZone(hotZone, coldZone))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("핫존 일치 확인 테스트")
    void 핫존_일치_확인() {
        Zone hotZone = new Zone(1);
        Zone coldZone = new Zone(2);
        StrikeZone strikeZone = new StrikeZone(hotZone, coldZone);

        Zone test = new Zone(1);
        assertThat(strikeZone.isHotZone(test)).isTrue();
    }

    @Test
    @DisplayName("핫존 불일치 확인 테스트")
    void 핫존_불일치_확인() {
        Zone hotZone = new Zone(1);
        Zone coldZone = new Zone(2);
        StrikeZone strikeZone = new StrikeZone(hotZone, coldZone);

        Zone test = new Zone(5);
        assertThat(strikeZone.isHotZone(test)).isFalse();
    }

    @Test
    @DisplayName("콜드존 일치 확인 테스트")
    void 콜드존_일치_확인() {
        Zone hotZone = new Zone(1);
        Zone coldZone = new Zone(2);
        StrikeZone strikeZone = new StrikeZone(hotZone, coldZone);

        Zone test = new Zone(2);
        assertThat(strikeZone.isColdZone(test)).isTrue();
    }

    @Test
    @DisplayName("콜드존 불일치 확인 테스트")
    void 콜드존_불일치_확인() {
        Zone hotZone = new Zone(1);
        Zone coldZone = new Zone(2);
        StrikeZone strikeZone = new StrikeZone(hotZone, coldZone);

        Zone test = new Zone(5);
        assertThat(strikeZone.isColdZone(test)).isFalse();
    }
}
