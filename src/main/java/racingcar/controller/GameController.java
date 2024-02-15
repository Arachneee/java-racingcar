package racingcar.controller;

import java.util.List;
import racingcar.dto.CarDto;
import racingcar.model.Car;
import racingcar.model.Cars;
import racingcar.model.RandomNumberGenerator;
import racingcar.model.Round;
import racingcar.util.ExceptionRoofer;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class GameController {

    private final InputView inputView;
    private final OutputView outputView;

    public GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final Cars cars = getCars();
        final Round round = getRound();

        final RandomNumberGenerator generator = new RandomNumberGenerator();

        while (round.isContinue()) {
            cars.go(generator);
            round.progress();

            List<CarDto> carDtos = createCarDtos(cars);

            outputView.printPerRound(carDtos);
        }

        outputView.printPerRound(createCarDtos(cars));

        List<String> winner = cars.findWinner()
                .stream()
                .map(Car::getName)
                .toList();

        outputView.printWinners(winner);
    }

    private Cars getCars() {
        return ExceptionRoofer.generate(() -> {
            String names = inputView.readCarNames();
            return Cars.from(names);
        }, outputView::printError);
    }

    private Round getRound() {
        return ExceptionRoofer.generate(() -> {
            String tryRound = inputView.readTryRound();
            Round round = Round.from(tryRound);
            return round;
        }, outputView::printError);
    }

    private List<CarDto> createCarDtos(Cars cars) {
        return cars.getCars()
                .stream()
                .map(CarDto::from)
                .toList();
    }

}
