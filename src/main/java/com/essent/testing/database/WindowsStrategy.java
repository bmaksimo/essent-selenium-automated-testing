package com.essent.testing.database;

import java.net.URL;

public class WindowsStrategy implements OsStrategy {

	@Override
	public String getFileLocation(URL url) {
		String filelLocation = url.getPath();
		// On windows the path is returned as /C:/..., so we need to remove the leading '/'
		filelLocation = filelLocation.substring(1);
		return filelLocation;
	}

	@Override
	public String getDefaultPostgresLocation() {
		return "C://Program Files//PostgreSQL//9.3//bin//";
	}

	@Override
	public ProcessBuilder getRestoreCmd(String host, int port, String userId, String db, String dumpLocation) {
		ProcessBuilder process = new ProcessBuilder();
		process.command("cmd.exe", "/c", "psql.exe", "-w", "-h" + host, "-p" + port, "-U" + userId, "-d" + db, "-f",
		        dumpLocation);
		return process;
	}

	@Override
	public ProcessBuilder getDumpCmd(String host, int port, String userId, String db, String dumpLocation) {
		ProcessBuilder process = new ProcessBuilder();
		process.command("cmd.exe", "/c", "pg_dump.exe", "-w", "-h" + host, "-p" + port, "-U" + userId, "-d" + db, "-c",
		        "-f", dumpLocation);
		return process;
	}

	@Override
	public ProcessBuilder getDropDbCmd(String host, int port, String userId, String db) {
		ProcessBuilder process = new ProcessBuilder();
		process.command("cmd.exe", "/c", "dropdb", "-h" + host, "-p" + port, "-U" + userId, db);
		return process;
	}

	@Override
	public ProcessBuilder getCreateDbCmd(String host, int port, String userId, String db) {
		ProcessBuilder process = new ProcessBuilder();
		process.command("cmd.exe", "/c", "createdb", "-h" + host, "-p" + port, "-U" + userId, db);
		return process;
	}

	@Override
	public ProcessBuilder getQuery(String host, int port, String userId, String db, String query) {
		ProcessBuilder process = new ProcessBuilder();
		process.command("cmd.exe", "/c", "psql.exe", "-w", "-h" + host, "-p" + port, "-U" + userId, "-d" + db, "-c", query);
		return process;
	}


}
