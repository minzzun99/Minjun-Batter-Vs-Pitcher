package com.woowaprecourse.minjun_baseball_game.controller;

import com.woowaprecourse.minjun_baseball_game.domain.BaseballGame;
import com.woowaprecourse.minjun_baseball_game.domain.PitchResult;
import com.woowaprecourse.minjun_baseball_game.dto.GameResultResponse;
import com.woowaprecourse.minjun_baseball_game.dto.GameStartRequest;
import com.woowaprecourse.minjun_baseball_game.dto.GameStartResponse;
import com.woowaprecourse.minjun_baseball_game.dto.GameStatusResponse;
import com.woowaprecourse.minjun_baseball_game.dto.PitchRequest;
import com.woowaprecourse.minjun_baseball_game.dto.PitchResponse;
import com.woowaprecourse.minjun_baseball_game.dto.ScoreBoardDto;
import com.woowaprecourse.minjun_baseball_game.service.BaseballGameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final BaseballGameService service;

    public GameController(BaseballGameService service) {
        this.service = service;
    }

    @PostMapping("/start")
    public GameStartResponse startGame(@RequestBody GameStartRequest request) {
        String gameId = service.createGame(request.getGameMode());
        BaseballGame game = service.getGame(gameId);

        return GameStartResponse.from(gameId, game.getGameMode(), ScoreBoardDto.from(game.getScoreBoard()));
    }

    @PostMapping("/{gameId}/pitch")
    public PitchResponse pitch(@PathVariable String gameId, @RequestBody PitchRequest request) {
        BaseballGame game = service.getGame(gameId);
        PitchResult result = game.playPitch(request.getZoneNumber());

        return PitchResponse.from(result, game);
    }

    @GetMapping("/{gameId}/status")
    public GameStatusResponse getStatus(@PathVariable String gameId) {
        BaseballGame game = service.getGame(gameId);

        return GameStatusResponse.from(game);
    }

    @GetMapping("/{gameId}/result")
    public GameResultResponse getResult(@PathVariable String gameId) {
        BaseballGame game = service.getGame(gameId);

        return GameResultResponse.from(game);
    }
}
