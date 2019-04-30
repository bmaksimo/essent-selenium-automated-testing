package com.essent.testing.database;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class LinuxStrategy implements OsStrategy {

  @Override
  public String getFileLocation(URL url) {
    checkURLForEncodedChars(url);
    return url.getPath();
  }

  private void checkURLForEncodedChars(URL url) {
    String decodedURL;
    try {
      decodedURL = URLDecoder.decode(url.getPath(), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException("Cannot decode resource URL " + url.getPath(), e);
    }
    if (!decodedURL.equals(url.getPath())) {
      throw new IllegalArgumentException(
          "No paths supported with encoded chars (spaces, etc..), path is " + url.getPath());
    }
  }

  @Override
  public String getDefaultPostgresLocation() {
    return "/usr/bin/";
  }

  @Override
  public ProcessBuilder getRestoreCmd(
      String host, int port, String userId, String db, String dumpLocation) {
    ProcessBuilder process = new ProcessBuilder();
    process.command(
        "psql", "-w", "-h", host, "-p", "" + port, "-U", userId, "-d", db, "-f", dumpLocation);
    return process;
  }

  @Override
  public ProcessBuilder getDumpCmd(
      String host, int port, String userId, String db, String dumpLocation) {
    ProcessBuilder process = new ProcessBuilder();
    process.command(
        "pg_dump",
        "-w",
        "-h",
        host,
        "-p",
        "" + port,
        "-U",
        userId,
        "-d",
        db,
        "-c",
        "-f",
        dumpLocation);
    return process;
  }

  @Override
  public ProcessBuilder getDropDbCmd(String host, int port, String userId, String db) {
    ProcessBuilder process = new ProcessBuilder();
    process.command("dropdb", "-h", host, "-p", "" + port, "-U", userId, db);
    return process;
  }

  @Override
  public ProcessBuilder getCreateDbCmd(String host, int port, String userId, String db) {
    ProcessBuilder process = new ProcessBuilder();
    process.command("createdb", "-h", host, "-p", "" + port, "-U", userId, db);
    return process;
  }

  @Override
  public ProcessBuilder getQuery(String host, int port, String userId, String db, String query) {
    ProcessBuilder process = new ProcessBuilder();
    process.command("psql", "-w", "-h", host, "-p", "" + port, "-U", userId, "-d", db, "-c", query);
    return process;
  }
}
