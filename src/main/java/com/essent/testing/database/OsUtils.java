package com.essent.testing.database;

import java.net.URL;

public class OsUtils {
	private final static OsStrategy strategy;
	private final static boolean isWindows;
	static {
	      String OS = System.getProperty("os.name");
	      if( OS.startsWith("Windows") ) {
	    	  strategy = new WindowsStrategy();
	    	  isWindows = true;
	      }
	      else {
	    	  strategy = new LinuxStrategy();
	    	  isWindows = false;
	      }
	}
	
	public static boolean isWindows() {
		return isWindows;
	}
	
	public static String getFileLocation(URL url) {
		return strategy.getFileLocation(url);
	}

	public static String getDefaultPostgresLocation() {
		return strategy.getDefaultPostgresLocation();
	}

	public static ProcessBuilder getRestoreCmd(String host, int port, String userId, String db, String dumpLocation) {
		return strategy.getRestoreCmd(host, port, userId, db, dumpLocation);
	}

	public static ProcessBuilder getDumpCmd(String host, int port, String userId, String db, String dumpLocation) {
		return strategy.getDumpCmd(host, port, userId, db, dumpLocation);
	}

	public static ProcessBuilder getDropDbCmd(String host, int port, String userId, String db) {
		return strategy.getDropDbCmd(host, port, userId, db);
	}

	public static ProcessBuilder getCreateDbCmd(String host, int port, String userId, String db) {
		return strategy.getCreateDbCmd(host, port, userId, db);
	}
	
	public static ProcessBuilder getQuery(String host, int port, String userId, String db, String query) {
		return strategy.getQuery(host, port, userId, db, query);
	}
}
