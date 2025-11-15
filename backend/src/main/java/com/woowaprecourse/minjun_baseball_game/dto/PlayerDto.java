package com.woowaprecourse.minjun_baseball_game.dto;

import com.woowaprecourse.minjun_baseball_game.domain.Player;

public record PlayerDto(
        Long id,
        String name,
        int totalAtBats,
        double battingAverage
) {
    public static PlayerDto from(Player player) {
        return new PlayerDto(
                player.getId(),
                player.getName(),
                player.getTotalAtBats(),
                player.getBattingAverage()
        );
    }
}
