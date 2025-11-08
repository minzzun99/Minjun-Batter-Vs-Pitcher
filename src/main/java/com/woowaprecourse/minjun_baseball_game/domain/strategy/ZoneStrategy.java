package com.woowaprecourse.minjun_baseball_game.domain.strategy;

import com.woowaprecourse.minjun_baseball_game.domain.StrikeZone;

public interface ZoneStrategy {
    StrikeZone generate(int userSelected);
}
