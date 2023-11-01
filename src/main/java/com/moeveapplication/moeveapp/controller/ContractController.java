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

import com.moeveapplication.moeveapp.service.ContractService;
import com.moeveapplication.moeveapp.model.Contract;
@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping
    public List<Contract> getAllContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/{id}")
    public Contract getContractById(@PathVariable Long id) {
        return contractService.getContractById(id);
    }

    @PostMapping
    public Contract createContract(@RequestBody Contract contract) {
        return contractService.saveContract(contract);
    }

    @DeleteMapping("/{id}")
    public void deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
    }
    
    @PutMapping("/{id}")
    public Contract updateContract(@PathVariable Long id, @RequestBody Contract contractDetails) {
        Contract contract = contractService.getContractById(id);
        if (contract == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found");
        }

        contract.setContractNumber(contractDetails.getContractNumber());
        contract.setCustomerName(contractDetails.getCustomerName());
        contract.setDeliveryAddress(contractDetails.getDeliveryAddress());
        return contractService.saveContract(contract);
    }
    
    @GetMapping("/search")
    public List<Contract> searchContractsByContractNumber(@RequestParam String contractNumber) {
        return contractService.searchContractsByContractNumber(contractNumber);
    }

}
