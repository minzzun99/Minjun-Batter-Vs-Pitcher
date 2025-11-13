package com.woowaprecourse.minjun_baseball_game.domain;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.ZoneStrategy;

public class BaseballGameService {
    private final GameMode gameMode;
    private final GameState gameState;
    private final PitchResultJudge judge;
    private final ZoneStrategy zoneStrategy;

    public BaseballGameService(GameMode gameMode, BattingRecord battingRecord, ZoneStrategy zoneStrategy,
                               NumberGenerator numberGenerator) {
        this.gameMode = gameMode;
        this.gameState = new GameState(gameMode, numberGenerator);
        this.judge = new PitchResultJudge(battingRecord, numberGenerator);
        this.zoneStrategy = zoneStrategy;
    }

    public void playPitch(int selectedZone) {
        validateGameNotOver();

        Zone userZone = new Zone(selectedZone);
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
}
