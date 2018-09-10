package com.essent.testing.idresolvers;

import com.essent.testing.database.DBUtility;

import java.util.HashMap;
import java.util.Map;

public class DunningAccountIdResolver {
	
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
		
		String externalId = args.trim();
		String dbFieldName = fieldMapping.get(fieldName.toLowerCase());
		if( dbFieldName == null ) {
			throw new IllegalArgumentException("Cannot translate " + fieldName + " into an dunnning_bundle field name, conversion not defined");
		}

		try {
			return DBUtility.getDunningAccountField(dbFieldName, externalId);
		} catch(Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
