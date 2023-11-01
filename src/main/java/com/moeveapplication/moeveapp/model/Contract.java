package com.moeveapplication.moeveapp.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String contractNumber;
    private String customerName;
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    @JsonBackReference
    private Tour tour;

    public Contract() {

    }
	public Contract(Long id, String contractNumber, String customerName, String deliveryAddress, Tour tour) {
		super();
		this.id = id;
		this.contractNumber = contractNumber;
		this.customerName = customerName;
		this.deliveryAddress = deliveryAddress;
		this.tour = tour;
	}

    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
	    this.tour = tour;
	    if(tour != null && !tour.getContracts().contains(this)) {
	        tour.getContracts().add(this);
	    }
	}


   
}
