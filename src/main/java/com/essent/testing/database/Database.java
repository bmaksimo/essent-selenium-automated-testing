package com.essent.testing.database;

public enum Database {
  JBilling("org.postgresql.Driver", "jdbc:postgresql:"),
  SuiteDb("com.mysql.jdbc.Driver", "jdbc:mysql:");

  private String driverClass;
  private String dbSchema;

  Database(String driverClass, String dbSchema) {
    this.driverClass = driverClass;
    this.dbSchema = dbSchema;
  }

  public String getDriverClass() {
    return driverClass;
  }

  public String getDbSchema() {
    return dbSchema;
  }
}
