package com.rhkdhv.cardealerserver;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rhkdhv.cardealerserver.entity.Car;
import com.rhkdhv.cardealerserver.repository.CarRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarRepositoryIntegrationTest {
	
	@Autowired
	private CarRepository carRepository;
	
	private static String FORD_MAKE_FOR_TESTIN;
	private static String TOYOTA_MAKE_FOR_TESTIN;
	private static long DATE_MAKE_FOR_TESTIN;
	private static long DATE2_MAKE_FOR_TESTIN;
	
	@After
	public void tearDown() {
		deleteAllCarsCreatedForTesting();
	}
	
	@BeforeClass
	public static void setUp() {
		FORD_MAKE_FOR_TESTIN = "Ford"+UUID.randomUUID();
		TOYOTA_MAKE_FOR_TESTIN = "Toyota"+UUID.randomUUID();
		DATE_MAKE_FOR_TESTIN = ZonedDateTime.now().toEpochSecond();
		DATE2_MAKE_FOR_TESTIN = ZonedDateTime.now().minusDays(4).toEpochSecond();
	}
	
	@Test
	public void should_save_cars() {
		BigDecimal price = new BigDecimal(2000);
		BigDecimal maintenanceCost = new BigDecimal(300);
		Car ford = new Car(FORD_MAKE_FOR_TESTIN, "Fusion", "_version_F", DATE_MAKE_FOR_TESTIN, price, 11.5, maintenanceCost);
		carRepository.save(ford);
		assertNotNull(ford.getId());
	}
	
	@Test
	public void should_get_a_list_of_cars_by_year() {
		createCars();
		List<Car> cars = carRepository.findByYearAndMake(Arrays.asList(DATE_MAKE_FOR_TESTIN), null);
		Car fusion = cars.stream().filter(c -> c.getModel().equals("Fusion")).findFirst().get();
		Car corolla = cars.stream().filter(c -> c.getModel().equals("Corolla")).findFirst().get();
		Car focus = cars.stream().filter(c -> c.getModel().equals("Focus")).findFirst().get();
		assertTrue(cars.size() == 3);
		assertTrue(focus.getModel().equals("Focus"));
		assertTrue(corolla.getModel().equals("Corolla"));
		assertTrue(fusion.getModel().equals("Fusion"));
	}
	
	@Test
	public void should_get_a_list_of_cars_by_make() {
		createCars();
		List<Car> cars = carRepository.findByYearAndMake(null, Arrays.asList(FORD_MAKE_FOR_TESTIN));
		assertTrue(cars.get(0).getModel().equals("Focus"));
	}
	
	@Test
	public void should_get_a_list_of_cars_by_year_and_make() {
		createCars();
		List<Car> cars = carRepository.findByYearAndMake(Arrays.asList(DATE_MAKE_FOR_TESTIN), Arrays.asList(FORD_MAKE_FOR_TESTIN));
		Car ford1 = cars.stream().filter(c -> c.getModel().equals("Focus")).findFirst().get();
		assertTrue(ford1.getModel().equals("Focus"));
		Car ford2 = cars.stream().filter(c -> c.getModel().equals("Fusion")).findFirst().get();
		assertTrue(ford2.getModel().equals("Fusion"));
		assertTrue(cars.size() == 2);
	}
	
	@Test
	public void should_get_a_list_of_cars_by_year_and_makes() {
		createCars();
		List<Car> cars = carRepository.findByYearAndMake(Arrays.asList(DATE2_MAKE_FOR_TESTIN, DATE_MAKE_FOR_TESTIN), Arrays.asList(FORD_MAKE_FOR_TESTIN, TOYOTA_MAKE_FOR_TESTIN));
		Car ford1 = cars.stream().filter(c -> c.getModel().equals("Focus")).findFirst().get();
		assertTrue(ford1.getModel().equals("Focus"));
		Car ford2 = cars.stream().filter(c -> c.getModel().equals("Fusion")).findFirst().get();
		assertTrue(ford2.getModel().equals("Fusion"));
		Car corolla = cars.stream().filter(c -> c.getModel().equals("Corolla")).findFirst().get();
		assertTrue(corolla.getModel().equals("Corolla"));
		Car camry = cars.stream().filter(c -> c.getModel().equals("Camry")).findFirst().get();
		assertTrue(camry.getModel().equals("Camry"));
		assertTrue(cars.size() == 4);
	}
	
	@Test
	public void should_get_entire_list_when_no_make_and_no_year_is_specified() {
		createCars();
		List<Car> cars = carRepository.findByYearAndMake(null, null);
		List<Car> entireListOfCars = carRepository.findAll();
		assertTrue(cars.size() == entireListOfCars.size());
	}
	
	private void createCars() {
		BigDecimal price = new BigDecimal(2000);
		BigDecimal maintenanceCost = new BigDecimal(300);
		Car fordFocus = new Car(FORD_MAKE_FOR_TESTIN, "Focus", "_version_F", DATE_MAKE_FOR_TESTIN, price, 11.5, maintenanceCost);
		Car fordFustion = new Car(FORD_MAKE_FOR_TESTIN, "Fusion", "_version_F", DATE_MAKE_FOR_TESTIN, price, 11.5, maintenanceCost);
		Car toyotaCorolla = new Car(TOYOTA_MAKE_FOR_TESTIN, "Corolla", "_version_T", DATE_MAKE_FOR_TESTIN, price, 11.5, maintenanceCost);
		Car toyotaCamry = new Car(TOYOTA_MAKE_FOR_TESTIN, "Camry", "_version_T", DATE2_MAKE_FOR_TESTIN, price, 11.5, maintenanceCost);
		List<Car> cars = Arrays.asList(fordFocus, fordFustion, toyotaCorolla, toyotaCamry);
		carRepository.saveAll(cars);
	}
	
	private void deleteAllCarsCreatedForTesting() {
		carRepository.deleteByMake(FORD_MAKE_FOR_TESTIN);
		carRepository.deleteByMake(TOYOTA_MAKE_FOR_TESTIN);
	}

}
