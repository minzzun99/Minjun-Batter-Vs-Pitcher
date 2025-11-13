package com.woowaprecourse.minjun_baseball_game.domain;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;

public class GameState {
    private static final double SACRIFICE_FLY_RATE = 0.3;
    private static final int MAX_OUT_FOR_SACRIFICE_FLY = 2;

    private final GameMode gameMode;
    private final Count count;
    private final BaseRunner baseRunner;
    private final ScoreBoard scoreBoard;
    private final NumberGenerator numberGenerator;

    public GameState(GameMode gameMode, NumberGenerator numberGenerator) {
        this.gameMode = gameMode;
        this.count = new Count();
        this.baseRunner = new BaseRunner();
        this.scoreBoard = new ScoreBoard(gameMode.getInitMyScore(), gameMode.getInitComputerScore());
        this.numberGenerator = numberGenerator;
    }

    public void update(PitchResult result) {
        if (result.isSwingAndMiss()) {
            handleSwingAndMiss();
            return;
        }

        if (result.isHit()) {
            handleHit(result);
            return;
        }

        if (result.isOut()) {
            handleOut(result);
        }
    }

    //9회 기준 - 역전하면 아웃 카운트와 상관없이 게임 종료
    public boolean isGameOver() {
        if (count.isThreeOut()) {
            return true;
        }
        // 투수 모드 - 역전당하면 즉시 패배
        if (gameMode == GameMode.PITCHER && scoreBoard.isLose()) {
            return true;
        }
        // 타자 모드 - 역전하면 즉시 승리
        return gameMode == GameMode.BATTER && scoreBoard.isWin();
    }

    public Count getCount() {
        return count;
    }

    public BaseRunnerStatus getBaseRunnerStatus() {
        return baseRunner.getRunnerStatus();
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    private void handleSwingAndMiss() {
        count.addStrike();
        checkAndClearRunners();
    }

    private void handleHit(PitchResult result) {
        int score = baseRunner.advance(result.getHitType());
        addScore(score);
        count.resetStrike();
    }

    private void handleOut(PitchResult result) {
        processSacrificeFly(result);
        count.addOut();
        count.resetStrike();
        checkAndClearRunners();
    }

    private void checkAndClearRunners() {
        if (count.isThreeOut()) {
            baseRunner.clearRunners();
        }
    }

    private void processSacrificeFly(PitchResult result) {
        if (!canSacrificeFly(result)) {
            return;
        }

        double random = numberGenerator.generateNumber();
        if (random <= SACRIFICE_FLY_RATE) {
            int score = baseRunner.scoreThirdRunner();
            addScore(score);
        }
    }

    private boolean canSacrificeFly(PitchResult result) {
        return result.getOutType() == OutType.FLY_OUT && count.getOut() < MAX_OUT_FOR_SACRIFICE_FLY
                && baseRunner.hasRunnerOnThird();
    }

    private void addScore(int score) {
        if (gameMode == GameMode.PITCHER) {
            scoreBoard.addComputerScore(score);
            return;
        }
        scoreBoard.addMyScore(score);
    }
}
