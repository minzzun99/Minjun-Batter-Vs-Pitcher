package com.woowaprecourse.minjun_baseball_game.service;

import com.woowaprecourse.minjun_baseball_game.domain.BaseballGame;
import com.woowaprecourse.minjun_baseball_game.domain.BattingRecord;
import com.woowaprecourse.minjun_baseball_game.domain.GameMode;
import com.woowaprecourse.minjun_baseball_game.domain.Player;
import com.woowaprecourse.minjun_baseball_game.domain.ZoneRandomGenerator;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.BatterModeZoneStrategy;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.PitcherModeZoneStrategy;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.RandomNumberGenerator;
import com.woowaprecourse.minjun_baseball_game.domain.strategy.ZoneStrategy;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BaseballGameService {
    private static final long GAME_TIMEOUT = 30 * 60 * 1000;

    private final Map<String, BaseballGame> games = new ConcurrentHashMap<>();
    private final Map<String, Long> gameCreatedTime = new ConcurrentHashMap<>();

    private final NumberGenerator numberGenerator;
    private final ZoneRandomGenerator zoneRandomGenerator;

    public BaseballGameService() {
        this.numberGenerator = new RandomNumberGenerator();
        this.zoneRandomGenerator = new ZoneRandomGenerator(numberGenerator);
    }

    public String createGame(GameMode gameMode, Player player) {
        String gameId = UUID.randomUUID().toString();
        BattingRecord record = player.toBattingRecord(numberGenerator);
        ZoneStrategy strategy = createZoneStrategy(gameMode);
        BaseballGame game = new BaseballGame(gameMode, record, strategy, numberGenerator, zoneRandomGenerator);

        games.put(gameId, game);
        gameCreatedTime.put(gameId, System.currentTimeMillis());
        return gameId;
    }

    public BaseballGame getGame(String gameId) {
        validateGameCreatedTime(gameId);
        BaseballGame game = games.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException(gameId + " : 존재하지 않는 게임입니다.");
        }
        return game;
    }

    public void removeGame(String gameId) {
        BaseballGame game = games.remove(gameId);
        if (game == null) {
            throw new IllegalArgumentException(gameId + " : 존재하지 않는 게임입니다.");
        }
    }

    private void validateGameCreatedTime(String gameId) {
        if (isExpired(gameId)) {
            removeExpiredGame(gameId);
            throw new IllegalArgumentException("만료된 게임입니다.");
        }
    }

    private ZoneStrategy createZoneStrategy(GameMode gameMode) {
        if (gameMode == GameMode.PITCHER) {
            return new PitcherModeZoneStrategy(zoneRandomGenerator);
        }
        return new BatterModeZoneStrategy(zoneRandomGenerator);
    }

    @Scheduled(fixedRate = 600_000)
    public void cleanupExpiredGames() {
        gameCreatedTime.keySet().removeIf(this::removeIfExpired);
    }

    private boolean removeIfExpired(String gameId) {
        if (isExpired(gameId)) {
            removeExpiredGame(gameId);
            return true;
        }
        return false;
    }

    private boolean isExpired(String gameId) {
        Long createdTime = gameCreatedTime.get(gameId);
        if (createdTime == null) {
            return false;
        }
        return System.currentTimeMillis() - createdTime > GAME_TIMEOUT;
    }

    private void removeExpiredGame(String gameId) {
        games.remove(gameId);
        gameCreatedTime.remove(gameId);
    }
}
