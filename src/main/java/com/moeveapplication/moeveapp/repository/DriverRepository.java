package com.moeveapplication.moeveapp.repository;

import com.moeveapplication.moeveapp.model.Driver;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByNameContainingIgnoreCase(String name);
}
