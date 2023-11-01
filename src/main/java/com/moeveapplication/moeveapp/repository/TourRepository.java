package com.moeveapplication.moeveapp.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moeveapplication.moeveapp.model.Tour;
@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    Tour findByDriverIdAndTourDate(Long driverId, LocalDate tourDate);

}
