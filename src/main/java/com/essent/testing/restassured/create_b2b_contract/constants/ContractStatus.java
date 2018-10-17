package com.essent.testing.restassured.create_b2b_contract.constants;

public enum ContractStatus {

	ACTIVE("ACTIVE"),
	TO_BE_ACTIVATED("TO BE ACTIVATED"),
	NOT_ACTIVATED_YET("NOT ACTIVATED YET");

	private String contractStatus;

	ContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public static ContractStatus fromString(String text) {
		for (ContractStatus contractStatus : ContractStatus.values()) {
			if (contractStatus.getContractStatus().equalsIgnoreCase(text)) {
				return contractStatus;
			}
		}
		return null;
	}

}
