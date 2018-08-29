package com.essent.testing.idresolvers;

import java.util.HashMap;
import java.util.Map;

public abstract class IdResolver {

	
		
	public static String resolveOrOriginal(String value) {
		try {
			Long tmp = resolve(value);
			return tmp.toString();
		} catch ( RuntimeException e) {
			// Ignore, not a valid expression
		}
		// return original
		return value;
	}
	
	
	public static Map<String,String> convertIds(Map<String,String> fields) {
		Map<String,String> result = new HashMap<>();
		fields.forEach( (key,value) -> {
			value = resolveOrOriginal(value);
			result.put(key, value);
		});
		return result;
	}
	

	public static Long resolve(String key) {
		// Parse the key
		Long result = null;
		try {
			int lhIndex = key.indexOf('(');
			int rhIndex = key.indexOf(')');
			int fieldIndex = key.indexOf('.');
			if (lhIndex == -1 || rhIndex == -1 || fieldIndex == -1) {
				throw new IllegalArgumentException("Key not in format <type>(<params>).<fieldname> but " + key);
			}
			String type = key.substring(0, lhIndex).trim().toLowerCase();
			String params = key.substring(lhIndex + 1, rhIndex);
			String fieldName = key.substring(fieldIndex + 1).trim();

			switch (type) {
				case "settlement":
					result = SettlementIdResolver.resolve(fieldName, params);
					break;
					
				case "invoice":
					result = InvoiceIdResolver.resolve(fieldName, params);
					break;
					
				case "dunning_bundle":
					result = DunningBundleIdResolver.resolve(fieldName, params);
					break;
					
				case "dunning_account":
					result = DunningAccountIdResolver.resolve(fieldName, params);
					break;
					
				case "user":
					result = BaseUserIdResolver.resolve(fieldName, params);
					break;
					
				case "customer":
					result = CustomerIdResolver.resolve(fieldName, params);
					break;
					
				case "service":
					result = ServiceIdResolver.resolve(fieldName, params);
					break;

				default:
					throw new IllegalStateException("Cannot determine id for " + key + ", type not defined");
			}
		} catch (Throwable e) {
			throw new RuntimeException("Failed to resolve key " + key, e);
		}
		// Do not show conversions, tests should do that when they fail.
		// System.out.println( "Converted " + key + " to " + result );
		return result;
	}
	
	public static String verbose(String key) {
		int lhIndex = key.indexOf('(');
		int rhIndex = key.indexOf(')');
		int fieldIndex = key.indexOf('.');
		if (lhIndex == -1 || rhIndex == -1 || fieldIndex == -1) {
			throw new IllegalArgumentException("Key not in format <type>(<params>).<fieldname> but " + key);
		}
		String type = key.substring(0, lhIndex).trim().toLowerCase();
		String params = key.substring(lhIndex + 1, rhIndex);
		type = type.substring(0,1).toUpperCase() + type.substring(1);
		return String.format("%s '%s' ", type, params);
	}

}
