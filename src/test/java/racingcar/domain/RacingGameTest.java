package racingcar.domain;

import org.junit.Test;
import racingcar.random.CarMoveThresholdGenerator;
import racingcar.random.IntMoreThanCarMoveThresholdGenerator;
import racingcar.random.RandomIntGenerator;
import racingcar.vo.RacingGameParameter;
import racingcar.vo.RacingResultOfRound;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class RacingGameTest {
    private List<String> carNames = Arrays.asList("pobi", "crong", "honux");
    private int totalRound = 5;
    private RacingGameParameter parameter = new RacingGameParameter(carNames, totalRound);

    @Test
    public void RacingGame_생성_시_라운드_셋업() {
        // when

        RacingGame racingGame = new RacingGame(parameter);
        // then
        int currentRoundOfRacingGame = racingGame.getCurrentRound();
        int totalRoundOfRacingGame = racingGame.getTotalRound();

        assertThat(currentRoundOfRacingGame).isEqualTo(1);
        assertThat(totalRoundOfRacingGame).isEqualTo(totalRound);
    }

    @Test
    public void RacingGame_생성_시_자동차_셋업() {
        // when
        RacingGame racingGame = new RacingGame(parameter);

        // then
        List<Car> cars = racingGame.getCars();

        assertThat(cars)
                .hasSize(carNames.size())
                .doesNotContainNull();
    }

    @Test
    public void RacingGame_생성_시_자동차_이름_셋업() {
        // when
        RacingGame racingGame = new RacingGame(parameter);

        // then
        List<Car> cars = racingGame.getCars();

        assertThat(cars.stream().map(Car::getName))
                .containsExactlyElementsOf(carNames);
    }

    @Test
    public void 현재_라운드_경주_결과_가져오기() {
        // when
        RacingGame racingGame = new RacingGame(parameter);

        // then
        RacingResultOfRound racingResultOfRound = racingGame.race();

        assertThat(racingResultOfRound.getRound()).isEqualTo(1);
        assertThat(racingResultOfRound.getCarsOfRound())
                .hasSize(carNames.size())
                .doesNotContainNull();
    }


    @Test
    public void 전진하지않는_레이싱_경주() {
        // given
        RandomIntGenerator randomIntGenerator = new CarMoveThresholdGenerator();
        RacingGame racingGame = new RacingGame(parameter, randomIntGenerator);

        // when
        RacingResultOfRound racingResultOfRound = racingGame.race();

        // then
        List<Car> cars = racingResultOfRound.getCarsOfRound();

        cars.forEach(car ->
                assertThat(car.getMovedDistance()).isEqualTo(0)
        );
    }

    @Test
    public void 전진하는_레이싱_경주() {
        // given
        RandomIntGenerator randomIntGenerator = new IntMoreThanCarMoveThresholdGenerator();
        RacingGame racingGame = new RacingGame(parameter, randomIntGenerator);

        // when
        RacingResultOfRound racingResultOfRound = racingGame.race();

        // then
        List<Car> cars = racingResultOfRound.getCarsOfRound();

        cars.forEach(car ->
                assertThat(car.getMovedDistance()).isEqualTo(1)
        );
    }

    @Test
    public void totalRound_만큼_실행_여부_확인() {
        // given
        RacingGame racingGame = new RacingGame(parameter);
        int roundCount = 0;

        // when
        while (!racingGame.isEnd()) {
            racingGame.race();
            roundCount++;
        }

        // then
        assertThat(roundCount).isEqualTo(racingGame.getTotalRound());
    }

    @Test
    public void 모든_라운드가_끝난_후_경기_시도하면_IllegalStateException_발생() {
        // given
        RacingGame racingGame = new RacingGame(parameter);

        // when
        while (!racingGame.isEnd()) {
            racingGame.race();
        }

        // then
        assertThatIllegalStateException().isThrownBy(() -> racingGame.race());
    }

    @Test
    public void RacingGame_초기화() {
        // given
        RacingGame racingGame = new RacingGame(parameter);

        while (!racingGame.isEnd()) {
            racingGame.race();
        }

        // when
        racingGame.initializeRacingGame();

        // then
        int currentRoundOfRacingGame = racingGame.getCurrentRound();
        int totalRoundOfRacingGame = racingGame.getTotalRound();
        List<Car> cars = racingGame.getCars();

        assertThat(currentRoundOfRacingGame).isEqualTo(1);
        assertThat(totalRoundOfRacingGame).isEqualTo(totalRound);

        assertThat(cars.stream().map(Car::getName))
                .containsExactlyElementsOf(carNames);
        cars.forEach(car ->
                assertThat(car.getMovedDistance()).isEqualTo(0)
        );
    }
}