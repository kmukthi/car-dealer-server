package com.rhkdhv.cardealerserver.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rhkdhv.cardealerserver.entity.Car;
import com.rhkdhv.cardealerserver.repository.CarRepository;
import com.rhkdhv.cardealerserver.service.CarServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CarServiceTest {
	
	@InjectMocks
	private CarServiceImpl carService;
	
	@Mock
	private CarRepository carRepository;
	
	@Test
	public void should_save_car() {
		Car car = mock(Car.class);
		when(carRepository.save(car)).thenReturn(car);
		Car savedCar = carService.saveCar(car);
		assertEquals(savedCar, car);
	}
	
	@Test
	public void should_sort_cars_based_on_total_annual_cost() {
		Car ford = new Car("Ford", "abc", "abc", 1987, new BigDecimal(8000), 10, new BigDecimal(2880));
		Car citroen = new Car("Citroen", "zxc", "zxc", 1990, new BigDecimal(12000), 20, new BigDecimal(940));
		List<Car> carList = Arrays.asList(ford, citroen);
		double distanceToTravelInOneMOnth = 200;
		double fuelPrice = 0.50;
		BigDecimal expectedTotalAnnualCostForFord = BigDecimal.valueOf(5000).setScale(2, RoundingMode.HALF_UP);
		BigDecimal expectedTotalAnnualCostForCitroen = BigDecimal.valueOf(4000).setScale(2, RoundingMode.HALF_UP);
		final List<Car> sortedLst = carService.sortCarAccordingToTotalAnnualCost(fuelPrice, distanceToTravelInOneMOnth, carList);
		assertEquals(citroen.getMake(), sortedLst.get(0).getMake());
		assertEquals(expectedTotalAnnualCostForCitroen, sortedLst.get(0).getTotalAnnualCost());
		assertEquals(ford.getMake(), sortedLst.get(1).getMake());
		assertEquals(expectedTotalAnnualCostForFord, sortedLst.get(1).getTotalAnnualCost());
		
	}
	

}
