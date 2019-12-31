package com.rhkdhv.cardealerserver.repository;

import java.util.List;

import com.rhkdhv.cardealerserver.entity.Car;

public interface CarRepositoryCustom {
	
	List<Car> findByYearAndMake(List<String> yearOfManufacture, List<String> make);

}
