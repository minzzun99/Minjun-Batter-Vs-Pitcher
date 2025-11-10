package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScoreBoardTest {
    @Test
    @DisplayName("초기 점수 확인 테스트")
    void 초기_점수_확인() {
        ScoreBoard scoreBoard = new ScoreBoard(0, 2);
        assertThat(scoreBoard.getMyScore()).isEqualTo(0);
        assertThat(scoreBoard.getComputerScore()).isEqualTo(2);
    }

    @Test
    @DisplayName("점수 추가 테스트")
    void 점수_추가() {
        ScoreBoard scoreBoard = new ScoreBoard(0, 2);
        scoreBoard.addMyScore(1);
        assertThat(scoreBoard.getMyScore()).isEqualTo(1);
    }

    @Test
    @DisplayName("승리 여부 확인 테스트")
    void 승리_여부_확인() {
        ScoreBoard scoreBoard = new ScoreBoard(3, 2);
        assertThat(scoreBoard.isWin()).isTrue();
    }

    @Test
    @DisplayName("패배 여부 확인 테스트")
    void 패배_여부_확인() {
        ScoreBoard scoreBoard = new ScoreBoard(1, 2);
        assertThat(scoreBoard.isLose()).isTrue();
    }

    @Test
    @DisplayName("무승부 여부 확인 테스트")
    void 무승부_여부_확인() {
        ScoreBoard scoreBoard = new ScoreBoard(2, 2);
        assertThat(scoreBoard.isDraw()).isTrue();
    }

    @Test
    @DisplayName("점수 추가 승리 여부 확인 테스트")
    void 점수_추가_승리_확인() {
        ScoreBoard scoreBoard = new ScoreBoard(0, 2);
        scoreBoard.addMyScore(3);
        assertThat(scoreBoard.isWin()).isTrue();
    }
}
