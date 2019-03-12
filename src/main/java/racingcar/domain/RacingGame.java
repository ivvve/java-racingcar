package racingcar.domain;

import racingcar.random.PositiveIntUnder10Generator;
import racingcar.random.RandomIntGenerator;
import racingcar.vo.RacingGameParameter;
import racingcar.vo.RacingGameRound;
import racingcar.vo.RacingResultOfRound;

import java.util.List;
import java.util.stream.Collectors;

public class RacingGame {
    private RacingGameInfo racingGameInfo;
    private RandomIntGenerator randomIntGenerator;

    public RacingGame(RacingGameParameter parameter) {
        this(parameter, new PositiveIntUnder10Generator());
    }

    public RacingGame(RacingGameParameter parameter, RandomIntGenerator randomIntGenerator) {
        List<Car> cars = carSetUp(parameter.getCarNames());
        RacingGameRound racingGameRound = new RacingGameRound(parameter.getTotalRound());

        this.racingGameInfo = new RacingGameInfo(cars, racingGameRound);
        this.randomIntGenerator = randomIntGenerator;
    }

    private List<Car> carSetUp(List<String> carNames) {
        return carNames.stream()
                .map(Car::new)
                .collect(Collectors.toList());
    }

    public int getTotalRound() {
        return this.racingGameInfo
                .getRacingGameRound()
                .getTotalRound();
    }

    public int getCurrentRound() {
        return this.racingGameInfo
                .getRacingGameRound()
                .getCurrentRound();
    }

    public List<Car> getCars() {
        return this.racingGameInfo.getCars();
    }

    public RacingResultOfRound runOnce() {
        if (!hasNextRound()) {
            throw new IllegalStateException("종료된 경주 입니다. 초기화를 해주세요.");
        }

        List<Car> cars = getCars();

        cars.forEach(car -> {
            int randomInt = this.randomIntGenerator.getRandomInt();
            car.goWhenGreaterThanThreshold(randomInt);
        });

        int currentRound = getCurrentRound();
        List<Car> carsOfRound = copyCarList(cars);

        this.racingGameInfo.incrementCurrentRound();
        return new RacingResultOfRound(carsOfRound, currentRound);
    }

    private List<Car> copyCarList(List<Car> originalCars) {
        return originalCars.stream()
                .map(Car::copy)
                .collect(Collectors.toList());
    }

    public boolean hasNextRound() {
        return this.racingGameInfo.hasNextRound();
    }

    public void initializeRacingGame() {
        this.racingGameInfo.initialize();
    }
}