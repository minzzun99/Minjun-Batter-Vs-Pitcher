package com.woowaprecourse.minjun_baseball_game.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Zone other = (Zone) obj;
        return this.number == other.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public int getNumber() {
        return number;
    }
}
