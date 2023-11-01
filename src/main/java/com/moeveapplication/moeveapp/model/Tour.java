package com.moeveapplication.moeveapp.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name="Tour", 
    uniqueConstraints=
        @UniqueConstraint(columnNames={"driver_id", "tourDate"})
)
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate tourDate;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToMany(mappedBy = "tour", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @OrderColumn(name="contract_order")
    @JsonManagedReference
    private List<Contract> contracts;
 
	public Tour() {

	}

	public Tour(LocalDate tourDate, Driver driver, List<Contract> contracts) {
		super();
		this.tourDate = tourDate;
		this.driver = driver;
		this.contracts = contracts;
	}

	public Tour(Long id, LocalDate tourDate, Driver driver, List<Contract> contracts) {
		super();
		this.id = id;
		this.tourDate = tourDate;
		this.driver = driver;
		this.contracts = contracts;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDate getTourDate() {
		return tourDate;
	}


	public void setTourDate(LocalDate tourDate) {
		this.tourDate = tourDate;
	}



	public Driver getDriver() {
		return driver;
	}


	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
	    this.contracts = contracts;
	    for(Contract contract : this.contracts) {
	        contract.setTour(this);
	    }
	}

	@Override
	public String toString() {
		return "Tour [id=" + id + ", tourDate=" + tourDate + ", driver=" + driver + ", contracts=" + contracts + "]";
	}

    
}