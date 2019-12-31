package com.rhkdhv.cardealerserver.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhkdhv.cardealerserver.entity.Car;
import com.rhkdhv.cardealerserver.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {
	
	@Autowired
	private CarRepository carRepository;

	@Override
	public Car saveCar(Car car) {
		return carRepository.save(car);
	}

	@Override
	public List<Car> findCarsByYearAndMake(List<Long> yearOfManufacture, List<String> make) {
		return carRepository.findByYearAndMake(yearOfManufacture, make);
	}

	@Override
	public List<Car> sortCarAccordingToTotalAnnualCost(double fuelPrice, double distanceToTravelPerMonth, List<Car> carsInStock) {
		final double constantMultuplyingFactor = fuelPrice * distanceToTravelPerMonth;
		carsInStock.stream().forEach(c -> {
			final double priceOfFuelInOneMonth = constantMultuplyingFactor/c.getMileage();
			final double annualFuelPrice = 12 * priceOfFuelInOneMonth;
			final BigDecimal annualFuelPriceAndAnnualMaintenanceCost = new BigDecimal(annualFuelPrice).add(c.getAnnualMaintenanceCost());
			final BigDecimal priceOfCarAnnually = c.getPrice().divide(new BigDecimal(4));
			final BigDecimal totalAnnualCost = annualFuelPriceAndAnnualMaintenanceCost.add(priceOfCarAnnually);
			c.setTotalAnnualCost(totalAnnualCost);
		});
		Collections.sort(carsInStock);
		return carsInStock;
	}

	@Override
	public List<String> getAllMake() {
		return carRepository.findAllMake();
	}

	@Override
	public List<String> getAllYears() {
		return carRepository.findAllYears();
	}

	@Override
	public void deleteCarById(Long id) {
		carRepository.deleteById(id);
	}

}
