package com.woowaprecourse.minjun_baseball_game.controller;

import com.woowaprecourse.minjun_baseball_game.domain.Player;
import com.woowaprecourse.minjun_baseball_game.dto.PlayerCreateRequest;
import com.woowaprecourse.minjun_baseball_game.dto.PlayerDto;
import com.woowaprecourse.minjun_baseball_game.dto.PlayerUpdateRequest;
import com.woowaprecourse.minjun_baseball_game.service.PlayerService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/players")
@RequiredArgsConstructor
public class AdminPlayersController {
    private final PlayerService playerService;

    // 선수 목록 조회
    @GetMapping
    public List<PlayerDto> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    // 선수 추가
    @PostMapping
    public PlayerDto createPlayer(@RequestBody @Valid PlayerCreateRequest request) {
        Player player = playerService.createPlayer(request);
        return PlayerDto.from(player);
    }

    // 선수 삭제
    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }

    // 선수 기록 수정
    @PutMapping("/{id}")
    public PlayerDto updatePlayer(@PathVariable Long id, @RequestBody @Valid PlayerUpdateRequest request) {
        return playerService.updatePlayer(id, request);
    }
}
