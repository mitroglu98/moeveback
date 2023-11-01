package com.moeveapplication.moeveapp;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.moeveapplication.moeveapp.model.Contract;
import com.moeveapplication.moeveapp.repository.ContractRepository;
import com.moeveapplication.moeveapp.service.ContractService;

@SpringBootTest
public class ContractServiceTest {

    @InjectMocks
    private ContractService contractService;

    @Mock
    private ContractRepository contractRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllContracts() {
        List<Contract> contracts = new ArrayList<>();

        when(contractRepository.findAll()).thenReturn(contracts);

        List<Contract> result = contractService.getAllContracts();

        assertEquals(contracts, result);
    }

    @Test
    public void testGetContractById() {
        Long id = 1L;
        Contract contract = new Contract();
        when(contractRepository.findById(id)).thenReturn(Optional.of(contract));

        Contract result = contractService.getContractById(id);

        assertEquals(contract, result);
    }

    @Test
    public void testGetContractById_NotFound() {
        Long id = 1L;
        when(contractRepository.findById(id)).thenReturn(Optional.empty());

        Contract result = contractService.getContractById(id);

        assertNull(result);
    }

    @Test
    public void testSaveContract() {
        Contract contract = new Contract();

        when(contractRepository.save(contract)).thenReturn(contract);

        Contract result = contractService.saveContract(contract);

        assertEquals(contract, result);
    }
    
    @Test
    public void testSearchContractsByContractNumber() {
        String contractNumber = "ABC123";
        List<Contract> matchingContracts = new ArrayList<>();

        when(contractRepository.findByContractNumberContainingIgnoreCase(contractNumber)).thenReturn(matchingContracts);

        List<Contract> result = contractService.searchContractsByContractNumber(contractNumber);

        assertEquals(matchingContracts, result);
    }

    @Test
    public void testDeleteContract() {
        Long id = 1L;
        
        contractService.deleteContract(id);

        verify(contractRepository, times(1)).deleteById(id);
    }
}
