package com.woowaprecourse.minjun_baseball_game.dto;

import com.woowaprecourse.minjun_baseball_game.domain.Player;

public record PlayerDto(
        Long id,
        String name,
        int totalAtBats,
        int singles,
        int doubles,
        int triples,
        int homeRuns,
        double battingAverage
) {
    public static PlayerDto from(Player player) {
        return new PlayerDto(
                player.getId(),
                player.getName(),
                player.getTotalAtBats(),
                player.getSingles(),
                player.getDoubles(),
                player.getTriples(),
                player.getHomeRuns(),
                player.getBattingAverage()
        );
    }
}
