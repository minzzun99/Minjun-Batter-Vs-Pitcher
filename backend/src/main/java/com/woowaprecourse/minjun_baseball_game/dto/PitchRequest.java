package com.woowaprecourse.minjun_baseball_game.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PitchRequest {
    @Min(1)
    @Max(9)
    private int zoneNumber;
}
