package com.rhkdhv.cardealerserver.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "car")
public class Car implements Comparable<Car>, Serializable {
	
	private static final long serialVersionUID = -1423366697045194540L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private long id;
	
	private String make;
	
	private String model;
	
	private String version;
	
	@Column(name="yearofmanufacture")
	private long yearOfManufacture;
	
	private BigDecimal price;
	
	private double mileage;
	
	@Column(name = "annualmaintenancecost")
	private BigDecimal annualMaintenanceCost;
	
	@ApiModelProperty(required = false, hidden = true)
	@Transient
	private BigDecimal totalAnnualCost;
	
	private Car() {
		
	}
	
	public Car( String make, String model, String version, long year, BigDecimal price, double mileage, BigDecimal annualMaintenanceCost) {
		this.make = make;
		this.model = model;
		this.version = version;
		this.yearOfManufacture = year;
		this.price = price;
		this.mileage = mileage;
		this.annualMaintenanceCost = annualMaintenanceCost;
	}
	
	public long getId() {
		return id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public long getYearOfManufacture() {
		return yearOfManufacture;
	}

	public void setYearOfManufacture(long yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public double getMileage() {
		return mileage;
	}
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	public BigDecimal getAnnualMaintenanceCost() {
		return annualMaintenanceCost;
	}
	public void setAnnualMaintenanceCost(BigDecimal annualMaintenanceCost) {
		this.annualMaintenanceCost = annualMaintenanceCost;
	}

	public BigDecimal getTotalAnnualCost() {
		return totalAnnualCost;
	}

	public void setTotalAnnualCost(BigDecimal totalAnnualCost) {
		this.totalAnnualCost = totalAnnualCost;
	}

	@Override
	public int compareTo(Car o) {
		return this.totalAnnualCost.compareTo(o.totalAnnualCost);
	}
	
	
	
}
