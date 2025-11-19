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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class BaseballGameService {
    private final Map<String, BaseballGame> games = new ConcurrentHashMap<>();
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
        return gameId;
    }

    public BaseballGame getGame(String gameId) {
        BaseballGame game = games.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException(gameId + " : 존재하지 않는 게임입니다.");
        }
        return game;
    }

    private ZoneStrategy createZoneStrategy(GameMode gameMode) {
        if (gameMode == GameMode.PITCHER) {
            return new PitcherModeZoneStrategy(zoneRandomGenerator);
        }
        return new BatterModeZoneStrategy(zoneRandomGenerator);
    }

    public void removeGame(String gameId) {
        BaseballGame game = games.remove(gameId);
        if (game == null) {
            throw new IllegalArgumentException(gameId + " : 존재하지 않는 게임입니다.");
        }
    }
}
