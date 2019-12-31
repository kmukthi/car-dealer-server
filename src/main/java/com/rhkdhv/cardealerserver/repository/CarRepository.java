package com.rhkdhv.cardealerserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rhkdhv.cardealerserver.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	
	public Car save(Car car);
	
	public void deleteById(Long id);
	
	@Query("SELECT c FROM Car c WHERE ((:yearOfManufacture) is null or c.yearOfManufacture IN (:yearOfManufacture)) and ((:make) is null"
			  + " or c.make IN (:make))")
	public List<Car> findByYearAndMake(@Param("yearOfManufacture")List<Long> yearOfManufacture, @Param("make")List<String> make);
	
	@Transactional
	public List<Car> deleteByMake(String make);
	
	public  List<Car> findAll();
	
	@Query("SELECT DISTINCT (c.make) from Car c")
	public List<String> findAllMake();
	
	@Query("SELECT DISTINCT (c.yearOfManufacture) from Car c")
	public List<String> findAllYears();

}
