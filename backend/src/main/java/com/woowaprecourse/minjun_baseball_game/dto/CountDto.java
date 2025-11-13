package com.woowaprecourse.minjun_baseball_game.dto;

import com.woowaprecourse.minjun_baseball_game.domain.Count;

public record CountDto(int strike, int out) {

    public static CountDto from(Count count) {
        return new CountDto(count.getStrike(), count.getOut());
    }
}
