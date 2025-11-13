package com.woowaprecourse.minjun_baseball_game.dto;

import com.woowaprecourse.minjun_baseball_game.domain.BaseRunnerStatus;

public record BaseRunnerDto(boolean first, boolean second, boolean third) {

    public static BaseRunnerDto from(BaseRunnerStatus status) {
        return new BaseRunnerDto(status.first(), status.second(), status.third());
    }
}
