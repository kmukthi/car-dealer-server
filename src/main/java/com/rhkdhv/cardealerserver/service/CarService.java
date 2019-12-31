package com.rhkdhv.cardealerserver.service;

import java.util.List;

import com.rhkdhv.cardealerserver.entity.Car;

public interface CarService {
	
	Car saveCar(Car car);
	
	void deleteCarById(Long id);
	
	List<Car> findCarsByYearAndMake(List<Long> yearOfManufacture, List<String> make);
	
	List<Car> sortCarAccordingToTotalAnnualCost(double fuelPrice, double distanceToTravelPerMonth, List<Car> cars);
	
	List<String> getAllMake();
	
	List<String> getAllYears();

}
