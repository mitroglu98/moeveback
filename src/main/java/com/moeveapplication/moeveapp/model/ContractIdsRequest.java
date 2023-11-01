package com.moeveapplication.moeveapp.model;

import java.util.List;

public class ContractIdsRequest {
	  private List<Long> contractIds;

	public ContractIdsRequest() {
		super();
	}

	public ContractIdsRequest(List<Long> contractIds) {
		super();
		this.contractIds = contractIds;
	}

	public List<Long> getContractIds() {
		return contractIds;
	}

	public void setContractIds(List<Long> contractIds) {
		this.contractIds = contractIds;
	}
	  
	  
	  
}
