package com.woowaprecourse.minjun_baseball_game.domain;

public class StrikeZone {
    private final Zone hotZone;
    private final Zone coldZone;

    public StrikeZone(Zone hotZone, Zone coldZone) {
        validateZone(hotZone, coldZone);
        this.hotZone = hotZone;
        this.coldZone = coldZone;
    }

    private void validateZone(Zone hotZone, Zone coldZone) {
        validateNullZone(hotZone, coldZone);
        validateEqualZone(hotZone, coldZone);
    }

    private void validateEqualZone(Zone hotZone, Zone coldZone) {
        if (hotZone.equals(coldZone)) {
            throw new IllegalArgumentException("핫존과 콜드존은 같은 수 없습니다.");
        }
    }

    private void validateNullZone(Zone hotZone, Zone coldZone) {
        if (hotZone == null || coldZone == null) {
            throw new IllegalArgumentException("존은 null일 수 없습니다.");
        }
    }

    public boolean isHotZone(Zone zone) {
        return hotZone.equals(zone);
    }

    public boolean isColdZone(Zone zone) {
        return coldZone.equals(zone);
    }
}
