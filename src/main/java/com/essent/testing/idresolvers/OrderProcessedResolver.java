package com.essent.testing.idresolvers;

import com.essent.testing.database.DBUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProcessedResolver {
	
	private static Map<String,String> fieldMapping = null;
	
	private static void init() {
		if( fieldMapping == null ) {
			fieldMapping = new HashMap<>();
			// keys in lower case !!!!
			fieldMapping.put("order_id", "order_id");
			fieldMapping.put("invoice_id", "invoice_id");
		}
	}
	
	public static List<Long> resolve(String fieldName, String publicNumber) {
		init();

		String dbFieldName = fieldMapping.get(fieldName.toLowerCase());
		if( dbFieldName == null ) {
			throw new IllegalArgumentException("Cannot translate " + fieldName + " into an invoice field name, conversion not defined");
		}

		try {
			 return DBUtility.getInvoiceOrdersField(dbFieldName, publicNumber.trim());
		} catch(Exception e) {
			throw new IllegalStateException(e);
		}
		
	}
}
