package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CountTest {
    @Test
    @DisplayName("Count 초기 상태 확인 테스트")
    void 초기상태_확인() {
        Count count = new Count();
        assertThat(count.getStrike()).isEqualTo(0);
        assertThat(count.getOut()).isEqualTo(0);
    }

    @Test
    @DisplayName("스트라이크 카운트 증가 확인 테스트")
    void 스트라이크_증가() {
        Count count = new Count();
        count.addStrike();
        assertThat(count.getStrike()).isEqualTo(1);
    }

    @Test
    @DisplayName("아웃 카운트 증가 확인 테스트")
    void 아웃_증가() {
        Count count = new Count();
        count.addOut();
        assertThat(count.getOut()).isEqualTo(1);
    }

    @Test
    @DisplayName("삼진 아웃 아웃 카운트 증가 확인 테스트")
    void 삼진_아웃_아웃카운트_증가() {
        Count count = new Count();
        count.addStrike();
        count.addStrike();
        count.addStrike();
        assertThat(count.getStrike()).isEqualTo(0);
        assertThat(count.getOut()).isEqualTo(1);
    }

    @Test
    @DisplayName("아웃 카운트 3개 이상 증가 예외 발생 테스트")
    void 아웃_세_개_이상_예외_발생() {
        Count count = new Count();
        count.addOut();
        count.addOut();
        count.addOut();
        assertThatThrownBy(count::addOut)
                .isInstanceOf(IllegalArgumentException.class);
    }
}
