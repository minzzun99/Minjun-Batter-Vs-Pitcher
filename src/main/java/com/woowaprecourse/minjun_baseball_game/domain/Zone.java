package com.woowaprecourse.minjun_baseball_game.domain;

public class Zone {
    private final int number;

    public Zone(int number) {
        validateRange(number);
        this.number = number;
    }

    private void validateRange(int number) {
        if (number < 1 || number > 9) {
            throw new IllegalArgumentException("스트라이크 존은 1~9 사이의 숫자입니다.");
        }
    }

    public boolean equals(Zone other) {
        return this.number == other.number;
    }

    public int getNumber() {
        return number;
    }
}
