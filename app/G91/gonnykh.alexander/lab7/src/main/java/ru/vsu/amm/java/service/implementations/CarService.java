package ru.vsu.amm.java.service.implementations;

import ru.vsu.amm.java.entities.CarEntity;
import ru.vsu.amm.java.enums.Status;
import ru.vsu.amm.java.mappers.CarMapper;
import ru.vsu.amm.java.model.dto.CarDto;
import ru.vsu.amm.java.repository.implementations.CarRepository;
import ru.vsu.amm.java.repository.implementations.UserCarRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class CarService {
    private final CarRepository carRepository;
    private final UserCarRepository userCarRepository;

    public CarService() {
        this.carRepository = new CarRepository();
        this.userCarRepository = new UserCarRepository();
    }

    public void addCar(CarDto carDto) {
        CarEntity carEntity = CarMapper.CarDtoToCarEntity(carDto);
        carRepository.save(carEntity);
    }

    public void deleteCar(Long carId) {
        carRepository.delete(carId);
    }

    public void updateCar(CarDto carDto) {
        CarEntity carEntity = CarMapper.CarDtoToCarEntity(carDto);
        carRepository.update(carEntity);
    }

    public List<CarDto> findCarsByStatus(Status status) {
        return carRepository.findByStatus(status)
                .stream()
                .map(CarMapper::carEntityToCarDto)
                .toList();
    }


    public List<CarDto> findAllCars() {
        return carRepository.findAll()
                .stream()
                .map(CarMapper::carEntityToCarDto)
                .toList();
    }

    public List<CarDto> findAllNotRentedCars() {
        return carRepository.findNotRented()
                .stream()
                .map(CarMapper::carEntityToCarDto)
                .toList();
    }

    public CarDto findCarsById(Long id) {
        CarEntity carEntity = carRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Car not found"));
        return CarMapper.carEntityToCarDto(carEntity);
    }

    public void bookCar(Long userId, CarDto carDto) {
        userCarRepository.bookCar(userId, carDto);
    }


}
