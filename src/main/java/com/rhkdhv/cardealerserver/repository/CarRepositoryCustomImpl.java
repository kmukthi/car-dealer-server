package com.rhkdhv.cardealerserver.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.rhkdhv.cardealerserver.entity.Car;

public class CarRepositoryCustomImpl implements CarRepositoryCustom {
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<Car> findByYearAndMake(List<String> yearOfManufactures, List<String> makes) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> query = cb.createQuery(Car.class);
        Root<Car> car = query.from(Car.class);
 
        List<Predicate> yearPredicates = new ArrayList<>();
        if (yearOfManufactures != null) {
        	Path<String> yearOfManufacturePath = car.get("yearOfManufacture");
        	for (String yearOfManufacture : yearOfManufactures) {
        		yearPredicates.add(cb.equal(yearOfManufacturePath, yearOfManufacture));
            }
        }
        
        List<Predicate> makePredicates = new ArrayList<>();
        if (makes != null) {
        	Path<String> makePath = car.get("make");
            for (String make : makes) {
            	makePredicates.add(cb.equal(makePath, make));
            }
		}
        
        List<Predicate> combinedPredicates = new ArrayList<>();
        
        for(Predicate yearPredicate: yearPredicates) {
        	for(Predicate makePredicate: makePredicates) {
        		combinedPredicates.add(cb.and(yearPredicate, makePredicate));
            }
        	
        }
        
        query.select(car)
            .where(cb.or(combinedPredicates.toArray(new Predicate[combinedPredicates.size()])));
 
        return entityManager.createQuery(query)
            .getResultList();
	}

}
