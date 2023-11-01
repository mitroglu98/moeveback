package com.moeveapplication.moeveapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.moeveapplication.moeveapp.model.Contract;
@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByContractNumberContainingIgnoreCase(String contractNumber);
}