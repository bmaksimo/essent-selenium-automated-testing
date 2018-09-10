package com.essent.testing.idresolvers;

import com.essent.testing.database.DBUtility;

import java.util.HashMap;
import java.util.Map;

public class InvoiceIdResolver {
	
	private static Map<String,String> fieldMapping = null;
	
	private static void init() {
		if( fieldMapping == null ) {
			fieldMapping = new HashMap<>();
			// keys in lower case !!!!
			fieldMapping.put("id", "id");
		}
	}
	
	public static Long resolve(String fieldName, String args) {
		init();
		
		String publicNumber = args.trim();
		String dbFieldName = fieldMapping.get(fieldName.toLowerCase());
		if( dbFieldName == null ) {
			throw new IllegalArgumentException("Cannot translate " + fieldName + " into an invoice field name, conversion not defined");
		}

		try {
			return DBUtility.getInvoiceField(dbFieldName, publicNumber);
		} catch(Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
