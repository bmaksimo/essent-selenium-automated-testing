package com.essent.testing.idresolvers;

import com.essent.testing.database.DBUtility;
import stepdefinitions.transformers.DateMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SettlementIdResolver {
	
	private static Map<String,String> fieldMapping = null;
	
	private static void init() {
		if( fieldMapping == null ) {
			fieldMapping = new HashMap<>();
			// keys in lower case !!!!
			fieldMapping.put("originid", "origen_order_id");
			fieldMapping.put("settlementid", "settlement_order_id");
			fieldMapping.put("settlement_order_id", "settlement_order_id");
			fieldMapping.put("id", "id");
			fieldMapping.put("newid", "new_order_id");
			fieldMapping.put("origen_order_id", "origen_order_id");
			fieldMapping.put("new_order_id", "new_order_id");
			fieldMapping.put("settlementorderid", "settlement_order_id");
		}
	}
	
	public static Long resolve(String fieldName, String args) {
		init();
		String params[] = args.split(",");
		if( params.length != 3 ) {
			throw new IllegalArgumentException("Expected 3 parameters for settlement, got '" + args +"'");
		}
		
		String dbFieldName = fieldMapping.get(fieldName.toLowerCase());
		if( dbFieldName == null ) {
			throw new IllegalArgumentException("Cannot translate " + fieldName + " into a settlement field name, conversion not defined");
		}

		String ean_or_contractline = params[0].trim();
		Date startDate = new DateMapper().transform(params[1].trim());
		Integer status = Integer.valueOf(params[2].trim());
		try {
			return DBUtility.getSettlementField(dbFieldName, ean_or_contractline, new java.sql.Date(startDate.getTime()) , status);
		} catch(Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
