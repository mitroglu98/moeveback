package com.moeveapplication.moeveapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.moeveapplication.moeveapp.repository.TourRepository;

import jakarta.persistence.EntityManager;

import com.moeveapplication.moeveapp.repository.DriverRepository;
import com.moeveapplication.moeveapp.repository.ContractRepository;
import com.moeveapplication.moeveapp.model.Tour;
import com.moeveapplication.moeveapp.model.Contract;
import com.moeveapplication.moeveapp.model.Driver;
@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;
    
    @Autowired
    private DriverRepository driverRepository;
    
    @Autowired
    private ContractRepository contractRepository;
    
    @Autowired
    public EntityManager entityManager;
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Tour getTourById(Long id) {
        return tourRepository.findById(id).orElse(null);
    }
    public List<Tour> searchToursById(Long id) {
        Tour tour = tourRepository.findById(id).orElse(null);
        if (tour != null) {
            return Arrays.asList(tour);
        }
        return Collections.emptyList();
    }

    public Tour saveTour(Tour tour) {
        return tourRepository.save(tour);
    }

    public void deleteTour(Long id) {
        tourRepository.deleteById(id);
    }
   
    
    public Tour createNewTour(LocalDate tourDate, Long driverId, List<Long> contractIds) {
        
        Tour existingTour = tourRepository.findByDriverIdAndTourDate(driverId, tourDate);
        if (existingTour != null) {
            throw new RuntimeException("A tour with this driver on the given date already exists");
        }

        Driver driver = driverRepository.findById(driverId)
            .orElseThrow(() -> new RuntimeException("Driver not found"));

        List<Contract> contracts = contractRepository.findAllById(contractIds);
        if(contracts.size() != contractIds.size()) {
            throw new RuntimeException("Some contracts not found");
        }
        
        for (Contract contract : contracts) {
            if (contract.getTour() != null) {
                throw new RuntimeException("Contract with ID: " + contract.getId() + " is already assigned to another tour.");
            }
        }

        Tour tour = new Tour(tourDate, driver, contracts);

        for(Contract contract: contracts) {
            contract.setTour(tour);  
        }
        
        return tourRepository.save(tour);
    }

    
    @Transactional
    public Tour reorderContractsForTour(Long tourId, List<Long> contractIds) {

        Tour tour = getTourById(tourId);
        if(tour == null) {
            throw new RuntimeException("Tour not found");
        }


        List<Contract> currentContracts = tour.getContracts();
        for (Contract existingContract : currentContracts) {
            if (existingContract != null) {
                existingContract.setTour(null);
                contractRepository.save(existingContract);
            }
        }


        List<Contract> orderedContracts = contractIds.stream()
            .map(contractRepository::findById)
            .map(opt -> opt.orElse(null))
            .filter(con -> con != null)  
            .collect(Collectors.toList());


        if(orderedContracts.size() != contractIds.size()) {
            throw new RuntimeException("Some contracts not found");
        }


        for (Contract orderedContract : orderedContracts) {
            orderedContract.setTour(tour);
        }

    
        tour.setContracts(orderedContracts);

        return tourRepository.save(tour);
    }



    public Tour addDriverToTour(Long tourId, Long driverId) {
        Tour newTour = tourRepository.findById(tourId).orElseThrow(() -> new RuntimeException("Tour not found"));
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));

        List<Tour> existingTours = driver.getTours(); 
        if (existingTours != null && !existingTours.isEmpty()) {
            for (Tour tour : existingTours) {
                if (tour.getTourDate().equals(newTour.getTourDate())) {
                    throw new RuntimeException("Driver is already assigned to a tour on this date.");
                }
            }
        }

        newTour.setDriver(driver);
        return tourRepository.save(newTour);
    }




    @Transactional
    public Tour addContractsToTour(Long tourId, List<Long> contractIds) {
        System.out.println("Dodavanje ugovora na turu: " + tourId + ", ugovori: " + contractIds);

        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new RuntimeException("Tour not found"));
        List<Contract> contractsToAdd = contractRepository.findAllById(contractIds);

        if(contractsToAdd.size() != contractIds.size()) {
            throw new RuntimeException("Some contracts not found");
        }

        for(Contract contract: contractsToAdd) {
            if(contract.getTour() != null) {
                throw new RuntimeException("Contract with ID: " + contract.getId() + " is already assigned to another tour.");
            }
            
            if (!tour.getContracts().contains(contract)) {
                contract.setTour(tour); 
                tour.getContracts().add(contract);
            }
        }

        return tourRepository.save(tour); 
    }


    public Tour removeContractFromTour(Long tourId, Long contractId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new RuntimeException("Tour not found"));
        Contract contractToRemove = contractRepository.findById(contractId).orElseThrow(() -> new RuntimeException("Contract not found"));

        if(!tour.getContracts().contains(contractToRemove)) {
            throw new RuntimeException("This contract isn't part of the specified tour.");
        }

        tour.getContracts().remove(contractToRemove);
        contractToRemove.setTour(null);
        contractRepository.save(contractToRemove);
        return tourRepository.save(tour); 
    }

    public Tour removeDriverFromTour(Long tourId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new RuntimeException("Tour not found"));

        if(tour.getDriver() == null) {
            throw new RuntimeException("No driver is assigned to this tour.");
        }

        Driver driverToRemove = tour.getDriver();
        tour.setDriver(null);
        driverToRemove.getTours().remove(tour);
        driverRepository.save(driverToRemove);
        return tourRepository.save(tour);
    }

}
