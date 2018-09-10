package com.essent.testing.idresolvers;

import com.essent.testing.database.DBUtility;
import stepdefinitions.transformers.DateMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServiceIdResolver {
	
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
		String params[] = args.split(",");
		if( params.length != 2 ) {
			throw new IllegalArgumentException("Expected 2 parameters for service, got '" + args +"'");
		}
		String deliveryPointId = params[0].trim();
		Date startDate = new DateMapper().transform( params[1].trim() );

		String dbFieldName = fieldMapping.get(fieldName.toLowerCase());
		if( dbFieldName == null ) {
			throw new IllegalArgumentException("Cannot translate " + fieldName + " into an invoice field name, conversion not defined");
		}

		try {
			return DBUtility.getServiceField(dbFieldName, deliveryPointId, new java.sql.Date(startDate.getTime()));
		} catch(Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
