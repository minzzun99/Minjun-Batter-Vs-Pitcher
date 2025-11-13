package com.woowaprecourse.minjun_baseball_game.dto;

import com.woowaprecourse.minjun_baseball_game.domain.PitchResult;

public record PitchResultDto(String type, String detail) {

    public static PitchResultDto from(PitchResult result) {
        String type = result.getResultType().name();
        String detail = null;

        if (result.isHit()) {
            detail = result.getHitType().name();
            return new PitchResultDto(type, detail);
        }
        if (result.isOut()) {
            detail = result.getOutType().name();
            return new PitchResultDto(type, detail);
        }
        return new PitchResultDto(type, detail);
    }
}
