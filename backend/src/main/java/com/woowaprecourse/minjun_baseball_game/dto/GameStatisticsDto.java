package com.woowaprecourse.minjun_baseball_game.dto;

import com.woowaprecourse.minjun_baseball_game.domain.GameStatistics;

public record GameStatisticsDto(
        int singles,
        int doubles,
        int triples,
        int homeRuns,
        int totalHits,

        int strikeOuts,
        int groundOuts,
        int flyOuts,
        int totalOuts,

        int totalAtBats,
        double battingAverage
) {
    public static GameStatisticsDto from(GameStatistics statistics) {
        return new GameStatisticsDto(
                statistics.getSingles(),
                statistics.getDoubles(),
                statistics.getTriples(),
                statistics.getHomeruns(),
                statistics.getTotalHits(),
                statistics.getStrikeOuts(),
                statistics.getGroundOuts(),
                statistics.getFlyOuts(),
                statistics.getTotalOuts(),
                statistics.getTotalAtBats(),
                statistics.getBattingAverage()
        );
    }
}
