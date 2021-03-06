package racingcar.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CarTest {

    private String carName = "pobi";

    @Test
    public void 자동차_생성_후_이름_확인() {
        // when
        Car car = new Car(carName);

        // then
        assertThat(car.getName()).isEqualTo(carName);
    }

    @Test
    public void 전진_성공() {
        // given
        Car car = new Car(carName);
        int value = Car.MOVE_THRESHOLD + 1;

        // when
        car.goWhenGreaterThanThreshold(value);

        // then
        assertThat(car.getMovedDistance()).isEqualTo(1);
    }

    @Test
    public void 전진_실패() {
        // given
        Car car = new Car(carName);
        int value = Car.MOVE_THRESHOLD;

        // when
        car.goWhenGreaterThanThreshold(value);

        // then
        assertThat(car.getMovedDistance()).isEqualTo(0);
    }

    @Test
    public void copy_생성() {
        // given
        Car car = new Car(carName);
        car.go();

        // when
        Car copy = car.copy();

        // then
        assertThat(copy.getName()).isEqualTo(carName);
        assertThat(copy.getMovedDistance()).isEqualTo(1);

        car.go();
        assertThat(copy.getMovedDistance()).isEqualTo(1);
    }

    @Test
    public void 초기화() {
        // given
        Car car = new Car(carName);
        car.go();

        // when
        car.initialize();

        // then
        assertThat(car.getMovedDistance()).isEqualTo(0);
    }
}