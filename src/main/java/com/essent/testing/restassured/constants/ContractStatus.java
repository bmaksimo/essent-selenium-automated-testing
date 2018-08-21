package com.essent.testing.restassured.constants;

public enum ContractStatus {
	
	    ACTIVE("ACTIVE"),
	    TO_BE_ACTIVATED("TO BE ACTIVATED");

	    private String contractStatus;

	    ContractStatus(String contractStatus) {
	        this.contractStatus = contractStatus;
	    }

	    public String getContractStatus() {
	        return contractStatus;
	    }

}
