package com.moeveapplication.moeveapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.moeveapplication.moeveapp.model.ContractIdsRequest;
import com.moeveapplication.moeveapp.model.Tour;
import com.moeveapplication.moeveapp.model.TourRequest;
import com.moeveapplication.moeveapp.service.TourService;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping("/{id}")
    public Tour getTourById(@PathVariable Long id) {
        return tourService.getTourById(id);
    }

    @PostMapping
    public Tour createTour(@RequestBody TourRequest tourRequest) {
        return tourService.createNewTour(tourRequest.getTourDate(), tourRequest.getDriverId(), tourRequest.getContractIds());
    }
    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
    }
    
    @GetMapping
    public List<Tour> getAllTours(@RequestParam(value = "searchId", required = false) Long searchId) {
        if(searchId != null) {
            return tourService.searchToursById(searchId);
        }
        return tourService.getAllTours();
    }

    
    @PutMapping("/{id}")
    public Tour updateTour(@PathVariable Long id, @RequestBody Tour tourDetails) {
        Tour tour = tourService.getTourById(id);
        if (tour == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour not found");
        }

        tour.setTourDate(tourDetails.getTourDate());
        return tourService.saveTour(tour);
    }
    @PutMapping("/{tourId}/reorder-contracts")
    public Tour reorderContracts(@PathVariable Long tourId, @RequestBody List<Long> contractIds) {
        return tourService.reorderContractsForTour(tourId, contractIds);
    }
    
    
    @PutMapping("/{tourId}/assign-driver/{driverId}")
    public Tour assignDriverToTour(@PathVariable Long tourId, @PathVariable Long driverId) {
        return tourService.addDriverToTour(tourId, driverId);
    }


    @PutMapping("/{tourId}/add-contracts")
    public Tour addContractsToTour(@PathVariable Long tourId, @RequestBody ContractIdsRequest request) {
        return tourService.addContractsToTour(tourId, request.getContractIds());
    }

    
    @DeleteMapping("/{tourId}/contracts/{contractId}")
    public Tour deleteContractFromTour(@PathVariable Long tourId, @PathVariable Long contractId) {
        return tourService.removeContractFromTour(tourId, contractId);
    }

    
}
