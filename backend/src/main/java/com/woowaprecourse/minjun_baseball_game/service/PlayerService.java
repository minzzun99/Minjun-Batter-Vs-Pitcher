package com.woowaprecourse.minjun_baseball_game.service;

import com.woowaprecourse.minjun_baseball_game.domain.Player;
import com.woowaprecourse.minjun_baseball_game.dto.PlayerCreateRequest;
import com.woowaprecourse.minjun_baseball_game.dto.PlayerDto;
import com.woowaprecourse.minjun_baseball_game.dto.PlayerUpdateRequest;
import com.woowaprecourse.minjun_baseball_game.repository.PlayerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerService {
    private final PlayerRepository playerRepository;

    public List<PlayerDto> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(PlayerDto::from)
                .toList();
    }

    public PlayerDto getPlayerById(Long id) {
        Player player = findById(id);
        return PlayerDto.from(player);
    }

    public Player findById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선수를 찾을 수 없습니다: " + id));
    }

    @Transactional
    public Player createPlayer(PlayerCreateRequest request) {
        Player player = new Player(request.getName(), request.getTotalAtBats(), request.getSingles(),
                request.getDoubles(), request.getTriples(), request.getHomeRuns());

        return playerRepository.save(player);
    }

    @Transactional
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    @Transactional
    public PlayerDto updatePlayer(Long id, PlayerUpdateRequest request) {
        Player player = findById(id);
        player.update(request.getName(), request.getTotalAtBats(), request.getSingles(), request.getDoubles(),
                request.getTriples(), request.getHomeruns());

        return PlayerDto.from(player);
    }
}
