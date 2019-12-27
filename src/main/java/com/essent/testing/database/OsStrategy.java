package com.essent.testing.database;

import java.net.URL;

interface OsStrategy {

  /**
   * Assuming the URL is a file location on the system, return the string representing the filename.
   *
   * @param url
   * @return it OS dependent format (depends on strategy implementation)
   */
  public String getFileLocation(URL url);

  public String getDefaultPostgresLocation();

  public ProcessBuilder getRestoreCmd(
      String host, int port, String userId, String db, String dumpLocation);

  public ProcessBuilder getDumpCmd(
      String host, int port, String userId, String db, String dumpLocation);

  public ProcessBuilder getDropDbCmd(String host, int port, String userId, String db);

  public ProcessBuilder getCreateDbCmd(String host, int port, String userId, String db);

  public ProcessBuilder getQuery(String host, int port, String userId, String db, String query);
}
