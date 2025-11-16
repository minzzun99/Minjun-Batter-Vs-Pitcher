package com.woowaprecourse.minjun_baseball_game.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerCreateRequest {
    @NotBlank
    private String name;

    @Min(1)
    private int totalAtBats;

    @Min(0)
    private int singles;

    @Min(0)
    private int doubles;

    @Min(0)
    private int triples;

    @Min(0)
    private int homeRuns;
}
