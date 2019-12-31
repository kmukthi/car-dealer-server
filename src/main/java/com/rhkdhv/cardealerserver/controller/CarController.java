package com.rhkdhv.cardealerserver.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhkdhv.cardealerserver.entity.Car;
import com.rhkdhv.cardealerserver.service.CarService;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/car")
@Api(value = "Operations pertaining to car")
public class CarController {

	@Autowired
	private CarService carService;
	
	private Logger logger = LoggerFactory.getLogger(CarController.class);

	
	@RequestMapping(path ="/", method = {RequestMethod.POST})
	public Car save(@RequestBody Car car) {
		if(car.getId() != 0) {
			final String errorMsg = "ID should not be provided while saving a car";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		return carService.saveCar(car);
	}
	
	@RequestMapping(path ="/{id}", method = {RequestMethod.PUT})
	public Car update(@PathVariable long id, @RequestBody Car car) {
		return carService.saveCar(car);
	}
	
	@RequestMapping(path ="/{id}", method = {RequestMethod.DELETE})
	public void delete(@PathVariable long id) {
		carService.deleteCarById(id);
	}
	
	@RequestMapping(path="/getByYearAndMake", method = RequestMethod.GET)
	public List<Car> getByYearAndMake(@RequestParam(required = false) List<Long> year, @RequestParam(required = false) List<String>make) {
		return carService.findCarsByYearAndMake(year, make);
	}
	
	@RequestMapping(path="/findRanking/{fuelPrice}/{distanceToTravelPerMonth}", method = RequestMethod.GET)
	public List<Car> findRanking(@PathVariable double fuelPrice, @PathVariable double distanceToTravelPerMonth, @RequestParam(required = false) List<Long> year, @RequestParam(required = false) List<String>make) {
		final List<Car> cars = this.getByYearAndMake(year, make);
		return carService.sortCarAccordingToTotalAnnualCost(fuelPrice, distanceToTravelPerMonth, cars);
	}
	
	@RequestMapping(path="/make", method = RequestMethod.GET)
	public List<String> getAllMake() {
		return carService.getAllMake();
	}
	
	@RequestMapping(path="/years", method = RequestMethod.GET)
	public List<String> getAllYears() {
		return carService.getAllYears();
	}
	
}
