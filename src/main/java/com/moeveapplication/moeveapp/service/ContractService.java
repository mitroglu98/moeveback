package com.moeveapplication.moeveapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moeveapplication.moeveapp.model.Contract;
import com.moeveapplication.moeveapp.repository.ContractRepository;
import jakarta.transaction.Transactional;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContractById(Long id) {
        return contractRepository.findById(id).orElse(null);
    }
    public List<Contract> searchContractsByContractNumber(String contractNumber) {
        return contractRepository.findByContractNumberContainingIgnoreCase(contractNumber);
    }
    @Transactional
    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }
}