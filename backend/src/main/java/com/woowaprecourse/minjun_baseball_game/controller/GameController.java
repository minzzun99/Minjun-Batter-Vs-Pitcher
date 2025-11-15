package com.woowaprecourse.minjun_baseball_game.controller;

import com.woowaprecourse.minjun_baseball_game.domain.BaseballGame;
import com.woowaprecourse.minjun_baseball_game.domain.PitchResult;
import com.woowaprecourse.minjun_baseball_game.domain.Player;
import com.woowaprecourse.minjun_baseball_game.dto.GameResultResponse;
import com.woowaprecourse.minjun_baseball_game.dto.GameStartRequest;
import com.woowaprecourse.minjun_baseball_game.dto.GameStartResponse;
import com.woowaprecourse.minjun_baseball_game.dto.GameStatusResponse;
import com.woowaprecourse.minjun_baseball_game.dto.PitchRequest;
import com.woowaprecourse.minjun_baseball_game.dto.PitchResponse;
import com.woowaprecourse.minjun_baseball_game.dto.PlayerDto;
import com.woowaprecourse.minjun_baseball_game.dto.ScoreBoardDto;
import com.woowaprecourse.minjun_baseball_game.service.BaseballGameService;
import com.woowaprecourse.minjun_baseball_game.service.PlayerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    private final BaseballGameService gameService;
    private final PlayerService playerService;


    // 선수 목록 조회
    @GetMapping("/players")
    public List<PlayerDto> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    // 선수 조회
    @GetMapping("/players/{id}")
    public PlayerDto getPlayer(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    // 게임 시작
    @PostMapping("/start")
    public GameStartResponse startGame(@RequestBody GameStartRequest request) {
        Player player = playerService.findById(request.getPlayerId());

        String gameId = gameService.createGame(request.getGameMode(), player);
        BaseballGame game = gameService.getGame(gameId);

        return GameStartResponse.from(gameId, game.getGameMode(), ScoreBoardDto.from(game.getScoreBoard()));
    }

    // 투구
    @PostMapping("/{gameId}/pitch")
    public PitchResponse pitch(@PathVariable String gameId, @RequestBody PitchRequest request) {
        BaseballGame game = gameService.getGame(gameId);
        PitchResult result = game.playPitch(request.getZoneNumber());

        return PitchResponse.from(result, game);
    }

    // 게임 상태 조회
    @GetMapping("/{gameId}/status")
    public GameStatusResponse getStatus(@PathVariable String gameId) {
        BaseballGame game = gameService.getGame(gameId);

        return GameStatusResponse.from(game);
    }

    // 게임 결과 조회
    @GetMapping("/{gameId}/result")
    public GameResultResponse getResult(@PathVariable String gameId) {
        BaseballGame game = gameService.getGame(gameId);

        return GameResultResponse.from(game);
    }
}
