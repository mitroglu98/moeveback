package com.moeveapplication.moeveapp.service;


import com.moeveapplication.moeveapp.model.Driver;
import com.moeveapplication.moeveapp.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) {
        return driverRepository.findById(id).orElse(null);
    }

    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }
    public List<Driver> searchDriversByName(String name) {
        return driverRepository.findByNameContainingIgnoreCase(name);
    }
    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }
}