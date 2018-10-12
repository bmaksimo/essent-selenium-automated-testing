package com.essent.testing.restassured.create_b2b_contract.constants;

public enum SwitchTypes {
	
	SUPPLIER_SWITCH("SUPPLIER SWITCH"),
	CUSTOMER_SWITCH("CUSTOMER SWITCH"),
	COMBINED_CUSTOMER_SWITCH("COMBINED CUSTOMER SWITCH"),
	MOVE_IN("MOVE IN");

	private String switchTypes;

	SwitchTypes(String switchTypes) {
		this.switchTypes = switchTypes;
	}

	public String getSwitchTypes() {
		return switchTypes;
	}

	public static SwitchTypes fromString(String text) {
		for (SwitchTypes switchTypes : SwitchTypes.values()) {
			if (switchTypes.getSwitchTypes().equalsIgnoreCase(text)) {
				return switchTypes;
			}
		}
		return null;
	}
}