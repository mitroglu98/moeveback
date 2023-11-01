package com.moeveapplication.moeveapp.model;

import java.time.LocalDate;
import java.util.List;

public class TourRequest {
    private LocalDate tourDate;
    private Long driverId;
    private List<Long> contractIds;
	public TourRequest() {
		super();
	}
	public TourRequest(LocalDate tourDate, Long driverId, List<Long> contractIds) {
		super();
		this.tourDate = tourDate;
		this.driverId = driverId;
		this.contractIds = contractIds;
	}
	public LocalDate getTourDate() {
		return tourDate;
	}
	public void setTourDate(LocalDate tourDate) {
		this.tourDate = tourDate;
	}
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	public List<Long> getContractIds() {
		return contractIds;
	}
	public void setContractIds(List<Long> contractIds) {
		this.contractIds = contractIds;
	}

    
    
}
