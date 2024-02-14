package racingcar.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cars {

    private List<Car> cars;

    public Cars(List<Car> cars) {
        this.cars = cars;
    }

    public static Cars from(final String carsName) {
        String[] names = carsName.split(",");

        List<Car> cars = Arrays.stream(names)
                .map(Car::from)
                .collect(Collectors.toList());

        return new Cars(cars);
    }


    public void go(NumberGenerator generator) {
        cars.forEach(car -> car.go(generator.generate()));
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }
}
