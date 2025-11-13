package com.woowaprecourse.minjun_baseball_game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class PitchResultTest {
    @Nested
    @DisplayName("기능 정상 작동 확인 테스트")
    class FunctionTest {
        @Test
        @DisplayName("안타 결과 생성 테스트")
        void 안타_결과_생성() {
            HitType hitType = HitType.HOMERUN;
            PitchResult result = PitchResult.hit(hitType);

            assertThat(result.isHit()).isTrue();
            assertThat(result.getHitType()).isEqualTo(HitType.HOMERUN);
        }

        @Test
        @DisplayName("아웃 결과 생성 테스트")
        void 아웃_결과_생성() {
            OutType outType = OutType.FLY_OUT;
            PitchResult result = PitchResult.out(outType);

            assertThat(result.isOut()).isTrue();
            assertThat(result.getOutType()).isEqualTo(OutType.FLY_OUT);
        }

        @Test
        @DisplayName("헛스윙 결과 생성 테스트")
        void 헛스윙_결과_생성() {
            PitchResult result = PitchResult.swingAndMiss();

            assertThat(result.isSwingAndMiss()).isTrue();
        }

        @Test
        @DisplayName("안타 베이스 값 확인 테스트")
        void 안타_베이스_값_확인() {
            assertThat(HitType.SINGLE.getBase()).isEqualTo(1);
            assertThat(HitType.DOUBLE.getBase()).isEqualTo(2);
            assertThat(HitType.TRIPLE.getBase()).isEqualTo(3);
            assertThat(HitType.HOMERUN.getBase()).isEqualTo(4);
        }

        @ParameterizedTest
        @EnumSource(OutType.class)
        @DisplayName("모든 아웃 종류 생성 확인 테스트")
        void 모든_아웃_생성_확인(OutType outType) {
            PitchResult result = PitchResult.out(outType);

            assertThat(result.isOut()).isTrue();
            assertThat(result.getOutType()).isEqualTo(outType);
        }

        @ParameterizedTest
        @EnumSource(HitType.class)
        @DisplayName("모든 안타 종류 생성 확인 테스트")
        void 모든_안타_생성_확인(HitType hitType) {
            PitchResult result = PitchResult.hit(hitType);

            assertThat(result.isHit()).isTrue();
            assertThat(result.getHitType()).isEqualTo(hitType);
        }
    }

    @Nested
    @DisplayName("예외 발생 테스트")
    class ExceptionTest {
        @Test
        @DisplayName("아웃일 경우 getHitType 호출 예외 발생 테스트")
        void 아웃_getHitType_예외_발생() {
            PitchResult result = PitchResult.out(OutType.GROUND_OUT);

            assertThatThrownBy(result::getHitType)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("아웃은 안타 종류가 없습니다.");
        }

        @Test
        @DisplayName("안타일 경우 getOutType 호출 예외 발생 테스트")
        void 안타_getOutType_호출_예외_발생() {
            PitchResult result = PitchResult.hit(HitType.SINGLE);

            assertThatThrownBy(result::getOutType)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("안타는 아웃 종류가 없습니다.");
        }

        @Test
        @DisplayName("헛스윙일 경우 getHitType 호출 예외 발생 테스트")
        void 헛스윙_getHitType_호출_예외_발생() {
            PitchResult result = PitchResult.swingAndMiss();

            assertThatThrownBy(result::getHitType)
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("헛스윙일 경우 getOutType 호출 예외 발생 테스트")
        void 헛스윙_getOutType_호출_예외_발생() {
            PitchResult result = PitchResult.swingAndMiss();

            assertThatThrownBy(result::getOutType)
                    .isInstanceOf(IllegalStateException.class);
        }
    }
}
