package com.moeveapplication.moeveapp;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.moeveapplication.moeveapp.model.Driver;
import com.moeveapplication.moeveapp.repository.DriverRepository;
import com.moeveapplication.moeveapp.service.DriverService;

@SpringBootTest
public class DriverServiceTest {

    @InjectMocks
    private DriverService driverService;

    @Mock
    private DriverRepository driverRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllDrivers() {
        List<Driver> drivers = new ArrayList<>();

        when(driverRepository.findAll()).thenReturn(drivers);

        List<Driver> result = driverService.getAllDrivers();

        assertEquals(drivers, result);
    }

    @Test
    public void testGetDriverById() {
        Long id = 1L;
        Driver driver = new Driver();
        when(driverRepository.findById(id)).thenReturn(Optional.of(driver));

        Driver result = driverService.getDriverById(id);

        assertEquals(driver, result);
    }

    @Test
    public void testGetDriverById_NotFound() {
        Long id = 1L;
        when(driverRepository.findById(id)).thenReturn(Optional.empty());

        Driver result = driverService.getDriverById(id);

        assertNull(result);
    }

    @Test
    public void testSaveDriver() {
        Driver driver = new Driver();

        when(driverRepository.save(driver)).thenReturn(driver);

        Driver result = driverService.saveDriver(driver);

        assertEquals(driver, result);
    }
    
    @Test
    public void testSearchDriversByName() {
        String name = "Mitar Mijatovic";
        List<Driver> matchingDrivers = new ArrayList<>();
    

        when(driverRepository.findByNameContainingIgnoreCase(name)).thenReturn(matchingDrivers);

        List<Driver> result = driverService.searchDriversByName(name);

        assertEquals(matchingDrivers, result);
    }

    @Test
    public void testDeleteDriver() {
        Long id = 1L;
        
        driverService.deleteDriver(id);

        verify(driverRepository, times(1)).deleteById(id);
    }
}

