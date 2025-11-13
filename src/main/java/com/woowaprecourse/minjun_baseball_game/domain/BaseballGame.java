package com.woowaprecourse.minjun_baseball_game.domain;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.ZoneStrategy;

public class BaseballGame {
    private final GameMode gameMode;
    private final GameState gameState;
    private final PitchResultJudge judge;
    private final ZoneStrategy zoneStrategy;
    private final ZoneRandomGenerator zoneRandomGenerator;

    public BaseballGame(GameMode gameMode, BattingRecord battingRecord, ZoneStrategy zoneStrategy,
                        NumberGenerator numberGenerator, ZoneRandomGenerator zoneRandomGenerator) {
        this.gameMode = gameMode;
        this.gameState = new GameState(gameMode, numberGenerator);
        this.judge = new PitchResultJudge(battingRecord, numberGenerator);
        this.zoneStrategy = zoneStrategy;
        this.zoneRandomGenerator = zoneRandomGenerator;
    }

    public void playPitch(int selectedZone) {
        validateGameNotOver();

        Zone userZone = getPitchedZone(selectedZone);
        StrikeZone strikeZone = zoneStrategy.generate(selectedZone);

        PitchResult result = judge.judge(userZone, strikeZone);
        gameState.update(result);
    }

    public boolean isGameOver() {
        return gameState.isGameOver();
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public Count getCount() {
        return gameState.getCount();
    }

    public BaseRunnerStatus getBaseRunnerStatus() {
        return gameState.getBaseRunnerStatus();
    }

    public ScoreBoard getScoreBoard() {
        return gameState.getScoreBoard();
    }

    public GameResult getResult() {
        validateGameOver();

        if (gameState.getScoreBoard().isWin()) {
            return GameResult.WIN;
        }

        if (gameState.getScoreBoard().isLose()) {
            return GameResult.LOSE;
        }

        return GameResult.DRAW;
    }

    private void validateGameNotOver() {
        if (isGameOver()) {
            throw new IllegalStateException("게임이 종료되었습니다.");
        }
    }

    private void validateGameOver() {
        if (!isGameOver()) {
            throw new IllegalStateException("게임이 진행 중입니다.");
        }
    }

    private Zone getPitchedZone(int selectedZone) {
        // 투수 모드 - 선택한 곳으로 투구
        if (gameMode == GameMode.PITCHER) {
            return new Zone(selectedZone);
        }
        // 타자 모드 - 랜덤으로 투구 (selectedZone => 핫존)
        return zoneRandomGenerator.randomZone();
    }
}
