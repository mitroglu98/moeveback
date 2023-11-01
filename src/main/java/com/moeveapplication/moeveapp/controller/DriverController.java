package com.moeveapplication.moeveapp.controller;
import com.moeveapplication.moeveapp.model.Driver;
import com.moeveapplication.moeveapp.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public Driver getDriverById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }

    @PostMapping
    public Driver createDriver(@RequestBody Driver driver) {
        return driverService.saveDriver(driver);
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
    }
    
    @GetMapping("/search")
    public List<Driver> searchDriversByName(@RequestParam String name) {
        return driverService.searchDriversByName(name);
    }
    
    @PutMapping("/{id}")
    public Driver updateDriver(@PathVariable Long id, @RequestBody Driver driverDetails) {
        Driver driver = driverService.getDriverById(id);
        if (driver == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver not found");
        }

        driver.setName(driverDetails.getName());
        driver.setSurname(driverDetails.getSurname());
        return driverService.saveDriver(driver);
    }

}