package com.moeveapplication.moeveapp;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.moeveapplication.moeveapp.model.Contract;
import com.moeveapplication.moeveapp.model.Driver;
import com.moeveapplication.moeveapp.model.Tour;
import com.moeveapplication.moeveapp.repository.ContractRepository;
import com.moeveapplication.moeveapp.repository.DriverRepository;
import com.moeveapplication.moeveapp.repository.TourRepository;
import com.moeveapplication.moeveapp.service.TourService;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.moeveapplication.moeveapp.model.Contract;
import com.moeveapplication.moeveapp.model.Driver;
import com.moeveapplication.moeveapp.model.Tour;
import com.moeveapplication.moeveapp.repository.ContractRepository;
import com.moeveapplication.moeveapp.repository.DriverRepository;
import com.moeveapplication.moeveapp.repository.TourRepository;
import com.moeveapplication.moeveapp.service.TourService;

@SpringBootTest
public class TourServiceTest {

    @InjectMocks
    private TourService tourService;

    @Mock
    private TourRepository tourRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private ContractRepository contractRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReorderContractsForTour_Success() {
        Long tourId = 1L;
        List<Long> contractIds = new ArrayList<>(List.of(2L, 3L));

        Tour tour = new Tour();
        when(tourRepository.findById(tourId)).thenReturn(Optional.of(tour));

        List<Contract> currentContracts = new ArrayList<>(List.of(new Contract(), new Contract()));
        tour.setContracts(currentContracts);

        when(contractRepository.findById(2L)).thenReturn(Optional.of(currentContracts.get(0)));
        when(contractRepository.findById(3L)).thenReturn(Optional.of(currentContracts.get(1)));

        when(contractRepository.save(any(Contract.class))).then(AdditionalAnswers.returnsFirstArg());

        when(tourRepository.save(any(Tour.class))).then(AdditionalAnswers.returnsFirstArg());

        Tour result = tourService.reorderContractsForTour(tourId, contractIds);

        assertNotNull(result);
        assertEquals(contractIds.size(), result.getContracts().size());
        assertEquals(null, result.getContracts().get(0).getId());
        assertEquals(null, result.getContracts().get(1).getId());
    }

    @Test
    public void testReorderContractsForTour_TourNotFound() {
        Long tourId = 1L;
        List<Long> contractIds = new ArrayList<>(List.of(2L, 3L));

        when(tourRepository.findById(tourId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tourService.reorderContractsForTour(tourId, contractIds));
    }

    @Test
    public void testReorderContractsForTour_SomeContractsNotFound() {
        Long tourId = 1L;
        List<Long> contractIds = new ArrayList<>(List.of(2L, 3L, 4L));

        Tour tour = new Tour();
        when(tourRepository.findById(tourId)).thenReturn(Optional.of(tour));

        List<Contract> currentContracts = new ArrayList<>(List.of(new Contract(), new Contract()));
        tour.setContracts(currentContracts);

        when(contractRepository.findById(2L)).thenReturn(Optional.of(currentContracts.get(0)));
        when(contractRepository.findById(3L)).thenReturn(Optional.of(currentContracts.get(1)));
        when(contractRepository.findById(4L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tourService.reorderContractsForTour(tourId, contractIds));
    }
    
    @Test
    public void testAddDriverToTour_Success() {
        Long tourId = 1L;
        Long driverId = 2L;

        Tour existingTour = new Tour();
        when(tourRepository.findById(tourId)).thenReturn(Optional.of(existingTour));

        Driver driver = new Driver();
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        driver.setTours(new ArrayList<>()); 

        when(tourRepository.save(any(Tour.class))).then(AdditionalAnswers.returnsFirstArg());

        Tour result = tourService.addDriverToTour(tourId, driverId);

        assertNotNull(result);
        assertEquals(driver, result.getDriver());
    }


    @Test
    public void testAddDriverToTour_TourNotFound() {
        Long tourId = 1L;
        Long driverId = 2L;

        when(tourRepository.findById(tourId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tourService.addDriverToTour(tourId, driverId));
    }

    @Test
    public void testAddDriverToTour_DriverNotFound() {
        Long tourId = 1L;
        Long driverId = 2L;

        Tour existingTour = new Tour();
        when(tourRepository.findById(tourId)).thenReturn(Optional.of(existingTour));
        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tourService.addDriverToTour(tourId, driverId));
    }

    @Test
    public void testAddDriverToTour_DriverAlreadyAssignedToTourOnDate() {
        Long tourId = 1L;
        Long driverId = 2L;

        Tour existingTour = new Tour();
        existingTour.setTourDate(LocalDate.now());

        when(tourRepository.findById(tourId)).thenReturn(Optional.of(existingTour));

        Driver driver = new Driver();
        driver.setTours(new ArrayList<>(List.of(existingTour)));

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        assertThrows(RuntimeException.class, () -> tourService.addDriverToTour(tourId, driverId));
    }
    

    
    @Test
    public void testSearchToursById_TourFound() {
        Long tourId = 1L;
        Tour tour = new Tour();
        when(tourRepository.findById(tourId)).thenReturn(Optional.of(tour));

        List<Tour> result = tourService.searchToursById(tourId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tour, result.get(0));
    }

    @Test
    public void testSearchToursById_TourNotFound() {
        Long tourId = 1L;
        when(tourRepository.findById(tourId)).thenReturn(Optional.empty());

        List<Tour> result = tourService.searchToursById(tourId);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testSaveTour() {
        Tour tour = new Tour();
        when(tourRepository.save(any(Tour.class))).then(AdditionalAnswers.returnsFirstArg());

        Tour result = tourService.saveTour(tour);

        assertNotNull(result);
    }

    @Test
    public void testDeleteTour() {
        Long tourId = 1L;
        doNothing().when(tourRepository).deleteById(tourId);

        assertDoesNotThrow(() -> tourService.deleteTour(tourId));
    }

    @Test
    public void testCreateNewTour_Success() {
        LocalDate tourDate = LocalDate.now();
        Long driverId = 1L;
        List<Long> contractIds = new ArrayList<>(List.of(2L, 3L));

        Tour existingTour = null;
        when(tourRepository.findByDriverIdAndTourDate(driverId, tourDate)).thenReturn(existingTour);

        Driver driver = new Driver();
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        List<Contract> contracts = new ArrayList<>(List.of(new Contract(), new Contract()));
        when(contractRepository.findAllById(contractIds)).thenReturn(contracts);

        for (Contract contract : contracts) {
            when(contractRepository.save(contract)).then(AdditionalAnswers.returnsFirstArg());
        }

        Tour savedTour = new Tour();
        when(tourRepository.save(any(Tour.class))).then(AdditionalAnswers.returnsFirstArg());

        Tour result = tourService.createNewTour(tourDate, driverId, contractIds);

        assertNotNull(result);
        assertEquals(tourDate, result.getTourDate());
        assertEquals(driver, result.getDriver());
        assertEquals(contracts.size(), result.getContracts().size());
        for (Contract contract : result.getContracts()) {
            assertEquals(result, contract.getTour());
        }
    }

    @Test
    public void testCreateNewTour_TourWithDriverAndDateAlreadyExists() {
        LocalDate tourDate = LocalDate.now();
        Long driverId = 1L;
        List<Long> contractIds = new ArrayList<>(List.of(2L, 3L));

        Tour existingTour = new Tour();
        when(tourRepository.findByDriverIdAndTourDate(driverId, tourDate)).thenReturn(existingTour);

        assertThrows(RuntimeException.class, () -> tourService.createNewTour(tourDate, driverId, contractIds));
    }

    @Test
    public void testCreateNewTour_DriverNotFound() {
        LocalDate tourDate = LocalDate.now();
        Long driverId = 1L;
        List<Long> contractIds = new ArrayList<>(List.of(2L, 3L));

        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tourService.createNewTour(tourDate, driverId, contractIds));
    }

    @Test
    public void testCreateNewTour_SomeContractsNotFound() {
        LocalDate tourDate = LocalDate.now();
        Long driverId = 1L;
        List<Long> contractIds = new ArrayList<>(List.of(2L, 3L, 4L));

        Tour existingTour = null;
        when(tourRepository.findByDriverIdAndTourDate(driverId, tourDate)).thenReturn(existingTour);

        Driver driver = new Driver();
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        List<Contract> contracts = new ArrayList<>(List.of(new Contract(), new Contract()));
        when(contractRepository.findAllById(contractIds)).thenReturn(contracts.subList(0, 2));

        assertThrows(RuntimeException.class, () -> tourService.createNewTour(tourDate, driverId, contractIds));
    }


 


}

