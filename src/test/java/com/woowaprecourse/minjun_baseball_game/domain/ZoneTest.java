package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ZoneTest {
    @Test
    @DisplayName("Zone 생성 확인 테스트")
    void Zone_생성_확인() {
        Zone zone = new Zone(3);
        assertThat(zone.getNumber()).isEqualTo(3);
    }

    @ParameterizedTest
    @DisplayName("Zone 생성 범위 예외 발생 테스트")
    @ValueSource(ints = {-1, 0, 10})
    void Zone_생성_범위_예외_발생(int input) {
        assertThatThrownBy(() -> new Zone(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Zone 같은 번호 비교 테스트")
    void 같은_번호_비교() {
        Zone zone = new Zone(3);
        Zone other = new Zone(3);
        assertThat(zone.equals(other)).isTrue();
    }

    @Test
    @DisplayName("Zone 다른 번호 비교 테스트")
    void 다른_번호_비교() {
        Zone zone = new Zone(5);
        Zone other = new Zone(3);
        assertThat(zone.equals(other)).isFalse();
    }
}
