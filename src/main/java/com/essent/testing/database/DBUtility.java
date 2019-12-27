package com.essent.testing.database;

import com.essent.testing.util.resource.ResourceUtil;
import com.jcraft.jsch.JSchException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import stepdefinitions.dwp.tables.plus.SwitchState;

/** Helper class to do checks on data in databases. */
public class DBUtility {

  private static final Logger logger = Logger.getLogger(DBUtility.class);

  private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

  private static DBConnector dbConnector = new DBConnector();

  public static List<String> distinctEventsOnNovaAuditLog(String tableName)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      return distinctEventsOnNovaAuditLog(conn, tableName);
    }
  }

  public static void testJBillingDatabaseConnection() throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      logger.debug("Database connected successfully.");
    }
  }

  private static List<String> distinctEventsOnNovaAuditLog(Connection conn, String tableName)
      throws SQLException {
    String sql = "select distinct operation from nova_audit where entity_name = ? ";
    List<String> result = new ArrayList<>();
    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      stmt.setString(1, tableName);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          result.add(rs.getString(1));
        }
      }
    }
    return result;
  }

  public static void addJsonAuditLogOnBillingTable(String tableName)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      addJsonAuditLogOnBillingTable(conn, tableName);
    }
  }

  public static void addAsyncAuditLogOnBillingTable(String tableName)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      addAsyncAuditLogOnBillingTable(conn, tableName);
    }
  }

  public static void addTraceOnBillingTable(String tableName) throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql =
          " CREATE TRIGGER trace_trigger BEFORE INSERT OR UPDATE OR DELETE ON  "
              + tableName
              + " FOR EACH ROW EXECUTE PROCEDURE trace_trigger()";

      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.execute();
      }
    }
  }

  private static void addJsonAuditLogOnBillingTable(Connection conn, String tableName)
      throws SQLException {
    String sql =
        " CREATE TRIGGER audit_trigger BEFORE INSERT OR UPDATE OR DELETE ON  "
            + tableName
            + " FOR EACH ROW EXECUTE PROCEDURE audit_trigger('INSERT_JSON')  ";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.execute();
    }
  }

  private static void addAsyncAuditLogOnBillingTable(Connection conn, String tableName)
      throws SQLException {
    String sql =
        " CREATE TRIGGER audit_trigger BEFORE INSERT OR UPDATE OR DELETE ON  "
            + tableName
            + " FOR EACH ROW EXECUTE PROCEDURE audit_trigger('INSERT_ASYNC')  ";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.execute();
    }
  }

  public static void checkBillingRow(
      String tableName, String keyName, long id, Map<String, String> fields)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      checkDBRow(conn, tableName, keyName, id, fields);
    }
  }

  public static void checkBillingRow(String tableName, long id, Map<String, String> fields)
      throws SQLException, JSchException {
    // This is more interesting, we need to open a connection to the database and
    // actually query
    // the database to see if the result is ok. The result is not returned in the
    // request, as it
    // is not needed.
    try (Connection conn = new DBConnector().getBillingConnection()) {
      checkDBRow(conn, tableName, "id", id, fields);
    }
  }

  public static void checkEdielRow(String tableName, long id, Map<String, String> fields)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getEdielConnection()) {
      checkDBRow(conn, tableName, "id", id, fields);
    }
  }

  public static List<String> getActiveConnections() throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getEdielConnection()) {
      return retrieveActiveConnections(conn);
    }
  }

  public static List<String> getUnbilledGridfeeInvoices(String deliveryPoint, Date untilDate)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getEdielConnection()) {
      return retrieveUnbilledGridfeeInvoices(conn, deliveryPoint, untilDate);
    }
  }

  public static Date getMaxDateBilledGridfeeInvoices(String deliveryPoint)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getEdielConnection()) {
      return retrieveMexDateBilleddGridfeeInvoices(conn, deliveryPoint);
    }
  }

  public static Map<String, BigDecimal> getSumConsumptionPerTimeframe(
      String deliveryPoint, Date fromDate, Date untilDate, Boolean rectified)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getEdielConnection()) {
      return retrieveSumConsumptionPerTimeframe(
          conn, deliveryPoint, fromDate, untilDate, rectified);
    }
  }

  public static Date getNextBillableDateForRecurringOrderOfSettlement(
      String deliverypoint, String contractlineid, Date startdate, String nextBillableDate)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      return retrieveNextBillableDateForRecurringOrderOfSettlement(
          conn, deliverypoint, contractlineid, startdate, nextBillableDate);
    }
  }

  private static Date retrieveNextBillableDateForRecurringOrderOfSettlement(
      Connection conn,
      String deliverypoint,
      String contractlineid,
      Date startdate,
      String nextBillableDate)
      throws SQLException {
    String sql =
        "select b.next_billable_day from settlement a, purchase_order b  where "
            + "a.origen_order_id = b.id and "
            + "a.consumption_key = ? and a.contract_line_id = ? and a.start_date = ? and status = 1";

    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      stmt.setString(1, deliverypoint);
      stmt.setString(2, contractlineid);
      stmt.setDate(3, startdate);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          return rs.getDate(1);
        }
      }
    }

    return null;
  }

  private static Map<String, BigDecimal> retrieveSumConsumptionPerTimeframe(
      Connection conn, String deliveryPoint, Date fromDate, Date untilDate, Boolean rectified)
      throws SQLException {
    String sql =
        "select  b.timeframe , sum (value)  from consumption a, calculated_register b "
            + "where a.calculated_register = b.id and a.from_date >= ? and a.to_date <= ? and a.deliverypointid = ? and a.rectified = ? "
            + " group by b.timeframe ";

    Map<String, BigDecimal> cons = new HashMap<String, BigDecimal>();
    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      stmt.setDate(1, fromDate);
      stmt.setDate(2, untilDate);
      stmt.setString(3, deliveryPoint);
      stmt.setBoolean(4, rectified);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          String key = rs.getString(1);
          BigDecimal value = rs.getBigDecimal(2);

          cons.put(key, value);
        }
      }
    }

    return cons;
  }

  private static List<String> retrieveActiveConnections(Connection conn) throws SQLException {
    String sql = "select deliverypointid from service where valid_to = '9999-12-31'";

    List<String> points = new ArrayList<String>();
    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          points.add(rs.getString(1));
        }
      }
    }

    return points;
  }

  private static List<String> retrieveUnbilledGridfeeInvoices(
      Connection conn, String deliveryPoint, Date untilDate) throws SQLException {
    String sql =
        "select invoice_number from gridfee_invoice where status = 0 and deliverypointid = ? and valid_from < ?";

    List<String> invoices = new ArrayList<String>();
    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      stmt.setString(1, deliveryPoint);
      stmt.setDate(2, untilDate);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          invoices.add(rs.getString(1));
        }
      }
    }

    return invoices;
  }

  private static Date retrieveMexDateBilleddGridfeeInvoices(Connection conn, String deliveryPoint)
      throws SQLException {

    String sql =
        "select max(valid_to) from gridfee_invoice  where status = 1 and deliverypointid = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      stmt.setString(1, deliveryPoint);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {

          return rs.getDate(1);
        }
      }
    }

    return null;
  }

  private static void checkDBRow(
      Connection conn, String tableName, String keyName, long id, Map<String, String> fields)
      throws SQLException {
    // Build the statement to check if expected data is set.
    String sql = buildSelectSql(tableName, fields, keyName + "=?");

    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      stmt.setLong(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          Assert.fail("No row found for table " + tableName + " and " + keyName + "=" + id);
        }
        ResultSetMetaData rsmd = rs.getMetaData();
        for (Map.Entry<String, String> entry : fields.entrySet()) {

          boolean handled = false;

          for (int ix = 1; ix <= rsmd.getColumnCount(); ix++) {
            String columnName = rsmd.getColumnName(ix);
            if (columnName.equals(entry.getKey())) {
              handled = true;
              checkField(rs, rsmd, entry.getKey(), entry.getValue(), ix);
              break; // exit loop to find field.
            }
          }

          if (!handled) {
            // very unlikely (most likely coding error), but better safe than sorry.
            throw new IllegalArgumentException(
                "Could not find " + entry.getKey() + " column in table " + tableName);
          }
        }
      }
    }
  }

  /**
   * Check the field to see if value is as expected.
   *
   * @param rs
   * @param rsmd
   * @param key
   * @param expected
   * @param ix index of key in rsmd (and rs)
   * @throws SQLException
   */
  private static void checkField(
      ResultSet rs, ResultSetMetaData rsmd, String key, String expected, int ix)
      throws SQLException {
    int type = rsmd.getColumnType(ix);
    switch (type) {
      case Types.DATE:
        if (expected.isEmpty()) {
          Assert.assertNull("Field " + key + " is not null", rs.getDate(ix));
        } else {
          LocalDate date = formatter.parseLocalDate(expected);
          Date sqlDate = new Date(date.toDate().getTime());
          Assert.assertEquals("Field " + key + " not as expected", sqlDate, rs.getDate(ix));
        }
        break;

      case Types.TIMESTAMP:
        // Timestamps are tricky. They have a hour part, but we do not want to
        // include that always. When we get in a date without time, we assume we
        // only want to test the date part.
        if (expected.isEmpty()) {
          Assert.assertNull("Field " + key + " is not null", rs.getTimestamp(ix));
        } else {
          if (expected.contains(":")) {
            // with time, so we compare strings.
            Assert.assertEquals("Field " + key + " not as expected", expected, rs.getString(ix));
          } else {
            // We have a simple date specified, so only check the date.
            LocalDate date = formatter.parseLocalDate(expected);
            Timestamp ts = rs.getTimestamp(ix);
            Assert.assertNotNull("Field " + key + "=" + expected + " expected, null found", ts);
            LocalDate foundDate = new LocalDate(ts.getTime());
            Assert.assertEquals("Field " + key + " not as expected", date, foundDate);
          }
        }
        break;

      default:
        if (expected.isEmpty()) {
          String value = rs.getString(ix);
          Assert.assertTrue(
              "Field " + key + " is not null or empty but " + value,
              value == null || value.isEmpty());
        } else {
          Assert.assertEquals("Field " + key + " not as expected", expected, rs.getString(ix));
        }
        break;
    }
  }

  private static String buildSelectSql(
      String tableName, Map<String, String> fields, String whereCondition) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT ");
    fields.keySet().forEach(s -> sql.append(s).append(", "));
    sql.setLength(sql.length() - 2); // remove last ', "
    sql.append(" FROM ").append(tableName).append(" WHERE ").append(whereCondition);
    return sql.toString();
  }

  public static int countBillingRecords(String tableName, String key, long id)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      return countRecords(conn, tableName, key, id);
    }
  }

  private static int countRecords(Connection conn, String tableName, String key, long id)
      throws SQLException {
    String sql = String.format("SELECT count(*) FROM %s WHERE %s=?", tableName, key);
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return DBUtility.asInteger(rs.getObject(1));
        }
        throw new SQLException("Query '" + sql + "' did not return any results");
      }
    }
  }

  public static void awaitLatestValueInTableNotToBe(
      String table, String column, String expectedValue, int timeout) throws Throwable {
    final long startedAt = System.currentTimeMillis();

    String actualValue;
    do {
      actualValue = DBUtility.selectValueOfLatestRecordInTable(table, column);
      Assert.assertNotEquals(expectedValue, actualValue);

      Thread.sleep(1000);
    } while ((System.currentTimeMillis() - startedAt) < timeout * 1000);
  }

  public static String selectValueOfRecordInTable(String tableName, String columnName, long id)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql = "SELECT " + columnName + " FROM " + tableName + " WHERE id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, id);
        try (ResultSet resultset = stmt.executeQuery()) {
          if (resultset.next()) {
            return resultset.getString(1);
          }
          return null;
        }
      }
    }
  }

  public static List<Map<String, String>> selectRecordInTableForeignKey(
      String tableName, String foreignKeyColumnName, long foreignKeyId)
      throws SQLException, JSchException {
    List<Map<String, String>> records = new ArrayList<>();
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql =
          "SELECT * FROM " + tableName + " WHERE " + foreignKeyColumnName + " = ? order by id";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, foreignKeyId);
        try (ResultSet resultset = stmt.executeQuery()) {
          while (resultset.next()) {
            // if (!resultset.next()) {
            // return null;
            // }

            Map<String, String> key2val = new HashMap<>();
            ResultSetMetaData meta = resultset.getMetaData();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
              key2val.put(meta.getColumnName(i), resultset.getString(i));
            }
            records.add(key2val);
          }
        }
      }
    }
    return records;
  }

  public static Map<String, String> selectRecordInTable(String tableName, long id)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, id);
        try (ResultSet resultset = stmt.executeQuery()) {
          if (!resultset.next()) {
            return null;
          }

          Map<String, String> key2val = new HashMap<>();
          ResultSetMetaData meta = resultset.getMetaData();
          for (int i = 1; i <= meta.getColumnCount(); i++) {
            key2val.put(meta.getColumnName(i), resultset.getString(i));
          }

          if (resultset.next()) {
            throw new IllegalStateException("too many records, for id " + id);
          }
          return key2val;
        }
      }
    }
  }

  private static String selectValueOfLatestRecordInTable(String tableName, String columnName)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql =
          "SELECT "
              + columnName
              + " FROM "
              + tableName
              + " WHERE id = "
              + //
              "(SELECT max(id) FROM "
              + tableName
              + ")";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        try (ResultSet resultset = stmt.executeQuery()) {
          if (resultset.next()) {
            return resultset.getString(1);
          }
          return null;
        }
      }
    }
  }

  public static void setDunningAccountLastProcessedDate(String externalId, LocalDate firstDate)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql = "UPDATE dunning_account SET last_processing_date=? WHERE external_id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setDate(1, new Date(firstDate.toDate().getTime()));
        stmt.setString(2, externalId);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException(
              "Update dunning account.last_processed_date failed, hitcount=" + count);
        }
      }
    }
  }

  public static void setDunningInvoiceAgentBlockedFlag(String invoiceNr, boolean agentBlocked)
      throws SQLException, JSchException {
    long invoiceId = DBUtility.getInvoiceId(invoiceNr);

    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql = "UPDATE dunning_invoice SET flag_agent_blocked=? WHERE id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setBoolean(1, agentBlocked);
        stmt.setLong(2, invoiceId);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException("Update dunning_invoice.agent_blocked failed, hitcount=" + count);
        }
      }
    }
  }

  public static void uploadAndApplyDunningBRE(String name)
      throws SQLException, JSchException, IOException {
    String resourcePath = "./data/dunning/" + name + ".json";
    String filePath = ResourceUtil.toPath("/data/dunning/" + name + ".json");
    byte[] raw;

    File file = new File(filePath);
    if (file.exists()) {
      raw = Files.readAllBytes(file.toPath());
    } else {
      InputStream res = DBUtility.class.getClassLoader().getResourceAsStream(resourcePath);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] tmp = new byte[4 * 1024];
      for (int got; (got = res.read(tmp)) != -1; ) {
        baos.write(tmp, 0, got);
      }
      raw = baos.toByteArray();
    }

    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql;

      sql =
          "INSERT INTO dunning_bre (name, description, valid_from, decision_tables_json) VALUES (?, 'yadda yadda', '2000-01-01', ?)";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, name);
        stmt.setString(2, new String(raw, Charset.forName("UTF-8")));
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException("insert dunning_bre failed, hitcount=" + count);
        }
      }

      sql = "UPDATE dunning_account SET bre_id = (SELECT MAX(id) FROM dunning_bre)";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        int count = stmt.executeUpdate();
        if (count == 0) {
          throw new SQLException("sync dunning_bre failed, hitcount=" + count);
        }
      }
    }
  }

  public static void setDunningAccountCreditScore(String externalId, int creditScore)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql = "UPDATE dunning_account SET credit_score=? WHERE external_id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, creditScore);
        stmt.setString(2, externalId);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException("Update dunning account.credit_score failed, hitcount=" + count);
        }
      }
    }
  }

  public static void setDunningAccountAccountType(String externalId, String accountType)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql = "UPDATE dunning_account SET account_type=? WHERE external_id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, accountType);
        stmt.setString(2, externalId);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException("Update dunning account.credit_score failed, hitcount=" + count);
        }
      }
    }
  }

  public static void setDunningAccountOutstandingPayments(
      String externalId, BigDecimal outstandingPayments) throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql = "UPDATE dunning_account SET outstanding_payments=? WHERE external_id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setBigDecimal(1, outstandingPayments);
        stmt.setString(2, externalId);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException(
              "Update dunning account.outstanding_payments failed, hitcount=" + count);
        }
      }
    }
  }

  public static void advanceDunningAccountLastProcessedDateWithDays(String externalId, int nr_days)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      // Note: I tried adding nr_days as a parameter, but it failed with syntax error
      // at or near "$1", pos 81
      String sql =
          "UPDATE dunning_account "
              + //
              "SET last_processing_date=last_processing_date + interval '"
              + nr_days
              + "' day "
              + //
              "WHERE external_id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, externalId);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException(
              "Advancing dunning account.last_processed_date failed, hitcount=" + count);
        }
      }
    }
  }

  /**
   * Return true or false depending on the value. If it starts with t or T we return true, false
   * otherwise.
   *
   * @param value
   * @return
   */
  public static boolean evaluateAsBoolean(String value) {
    if (value == null) {
      return false;
    }
    return value.toLowerCase().startsWith("t");
  }

  public static void setDunningAccountActive(String externalId, String active)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql =
          "UPDATE dunning_account "
              + //
              "SET flag_active=? "
              + //
              "WHERE external_id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setBoolean(1, evaluateAsBoolean(active));
        stmt.setString(2, externalId);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException(
              "Advancing dunning account.last_processed_date failed, hitcount=" + count);
        }
      }
    }
  }

  public static void scheduleDunningInboundCommForInvoice(String invoiceNr, String eventName)
      throws SQLException, JSchException {
    long invoiceId = DBUtility.getInvoiceId(invoiceNr);
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql =
          "INSERT INTO dunning_event "
              + //
              "(account_id, bundle_id, invoice_id, event_type, event_date, message, processed, dryrun_id) "
              + //
              "values ( "
              + //
              "          (select account_id from dunning_invoice where id = ?), "
              + //
              "          (select bundle_id  from dunning_invoice where id = ?), "
              + //
              "           ?, 'INBOUND_COMM', NOW() - interval '1' day, ?, FALSE, NULL"
              + //
              ")";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, invoiceId);
        stmt.setLong(2, invoiceId);
        stmt.setLong(3, invoiceId);
        stmt.setString(4, eventName);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException("Failed to insert DunningEvent for INBOUND_COMM");
        }
      }
    }
  }

  public static void validateDunningEventsBasedOnInvoice(
      long invoice_id, String type, LocalDate date, String message, int nr_records)
      throws SQLException, JSchException {

    check_nr_dunning_records("invoice_id", invoice_id, type, date, message, nr_records);
  }

  public static void validateDunningEventsBasedOnBundle(
      long bundle_id, String type, LocalDate date, String message, int nr_records)
      throws SQLException, JSchException {

    check_nr_dunning_records("bundle_id", bundle_id, type, date, message, nr_records);
  }

  private static void check_nr_dunning_records(
      String id_field, long id_value, String type, LocalDate date, String message, int nr_records)
      throws SQLException, JSchException {
    Date sqlDate = (date == null) ? null : new Date(date.toDate().getTime());
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql =
          "SELECT count(*) "
              + //
              "  FROM dunning_event "
              + //
              "WHERE "
              + id_field
              + "=? AND event_type=? AND message=? "
              + //
              (sqlDate != null ? "AND event_date=? " : "");

      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        // Set parameters
        stmt.setLong(1, id_value);
        stmt.setString(2, type);
        stmt.setString(3, message);
        if (sqlDate != null) {
          stmt.setDate(4, sqlDate);
        }

        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            String msg =
                String.format(
                    "Incorrect number of records for [%s:%d, type:%s, date:%s, message:%s]",
                    id_field, id_value, type, String.valueOf(date), message);
            int count = DBUtility.asInteger(rs.getObject(1));
            Assert.assertEquals(msg, nr_records, count);
          } else {
            throw new SQLException("Query '" + sql + "' did not return any results");
          }
        }
      }
    }
  }

  public static long getUserId(String billingId) throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql = "SELECT id FROM base_user WHERE user_name=?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, billingId);

        try (ResultSet resultset = stmt.executeQuery()) {
          if (resultset.next()) {
            return DBUtility.asLong(resultset.getObject(1));
          }
          return -1;
        }
      }
    }
  }

  private static long getInvoiceId(String invoiceNr) throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql = "SELECT id FROM invoice WHERE public_number=?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, invoiceNr);

        try (ResultSet resultset = stmt.executeQuery()) {
          if (resultset.next()) {
            return DBUtility.asLong(resultset.getObject(1));
          }
          return -1;
        }
      }
    }
  }

  public static long getBundleId(String externalId) throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql = "SELECT id FROM dunning_bundle WHERE external_id=?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, externalId);

        try (ResultSet resultset = stmt.executeQuery()) {
          if (resultset.next()) {
            return DBUtility.asLong(resultset.getObject(1));
          }
          return -1;
        }
      }
    }
  }

  public static void setInvoiceDates(long invoiceId, LocalDate creationDate, LocalDate dueDate)
      throws SQLException, JSchException {
    Date sqlDue = new Date(dueDate.toDate().getTime());
    Date sqlCreate = new Date(creationDate.toDate().getTime());
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql =
          "UPDATE invoice SET create_datetime=?, create_timestamp=?, due_date=? WHERE id=?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setDate(1, sqlCreate);
        stmt.setDate(2, sqlCreate);
        stmt.setDate(3, sqlDue);
        stmt.setLong(4, invoiceId);

        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException("Update invoice " + invoiceId + " failed, hitcount=" + count);
        }
      }

      sql = "UPDATE dunning_invoice SET   create_date=?, issue_date=?, due_date=? WHERE id=?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setDate(1, sqlCreate);
        stmt.setDate(2, sqlCreate);
        stmt.setDate(3, sqlDue);
        stmt.setLong(4, invoiceId);

        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException(
              "Update dunning_invoice " + invoiceId + " failed, hitcount=" + count);
        }
      }
    }
  }

  /**
   * Find the public number for the given invoiceId
   *
   * @param invoiceId
   * @return PublicNumber or null if not found
   * @throws JSchException
   * @throws SQLException
   */
  public static String getPublicNumberForInvoiceId(long invoiceId)
      throws SQLException, JSchException {
    String publicNumber = null;
    try (Connection conn = new DBConnector().getBillingConnection()) {
      String sql = "SELECT public_number FROM invoice WHERE id=?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, invoiceId);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            publicNumber = rs.getString(1);
          }
        }
      }
    }
    return publicNumber;
  }

  /**
   * Check if there are count rows with the given fields and values
   *
   * @param tableName table to check (from billing database)
   * @param count nr of records expected
   * @param fields which fields are present in the where clause
   * @throws JSchException
   * @throws SQLException
   * @throws AssertionError with the SQL as message if count is not correct.
   */
  public static void checkBillingRows(String tableName, int count, Map<String, String> fields)
      throws SQLException, JSchException {

    try (Connection conn = new DBConnector().getBillingConnection()) {
      checkTableRows(conn, count, tableName, fields);
    }
  }

  public static void checkEdielRows(String tableName, int count, Map<String, String> fields)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getEdielConnection()) {
      checkTableRows(conn, count, tableName, fields);
    }
  }

  private static void checkTableRows(
      Connection conn, int count, String tableName, Map<String, String> fields)
      throws SQLException, JSchException {
    ResultSetMetaData md = getMetaDataForTable(conn, tableName);

    StringBuffer sql = new StringBuffer();
    sql.append("SELECT count(*) FROM " + tableName + " WHERE ");
    List<String> values = new ArrayList<>(fields.size());
    List<Integer> fieldType = new ArrayList<>(fields.size());
    // Add where clause, remember type and value for each field in the correct
    // order.
    for (Map.Entry<String, String> entry : fields.entrySet()) {
      // In a where clause you need the 'is null' syntax for null values.
      if (StringUtils.isBlank(entry.getValue())) {
        sql.append(" ").append(entry.getKey()).append(" is NULL AND");
        // this means we have no parameter to set.
      } else {
        // We have a value, make certain it will be set correctly later.
        sql.append(" ").append(entry.getKey()).append("=? AND");
        values.add(entry.getValue());
        fieldType.add(getFieldType(md, entry.getKey()));
      }
    }

    sql.setLength(sql.length() - 4); // remove last " AND" part

    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
      // Use stored information to set correct values
      for (int ix = 0; ix < values.size(); ix++) {
        String val = values.get(ix);
        switch (fieldType.get(ix)) {
          case Types.DATE:
            Date sqlDate = Date.valueOf(val);
            ps.setDate(ix + 1, sqlDate);
            break;

          case Types.BIGINT:
            ps.setLong(ix + 1, Long.parseLong(val));
            break;

          case Types.INTEGER:
          case Types.TINYINT:
          case Types.SMALLINT:
            ps.setInt(ix + 1, Integer.parseInt(val));
            break;

          case Types.VARCHAR:
            ps.setString(ix + 1, val);
            break;

          default:
            throw new SQLException(
                "Unsupported sql type " + fieldType.get(ix) + " with value " + val);
        }
      }

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          // replace all ? in the sql string with their values, so the logline becomes
          // more readable.
          String msg = sql.toString();
          for (int ix = 0; ix < values.size(); ix++) {
            msg = msg.replaceFirst("\\?", "'" + values.get(ix) + "'");
          }
          int found = DBUtility.asInteger(rs.getObject(1));
          Assert.assertEquals(msg, count, found);
        } else {
          Assert.fail(sql.toString() + " did not yield any results");
        }
      }
    }
  }

  private static int getFieldType(ResultSetMetaData md, String fieldName) throws SQLException {
    for (int ix = 1; ix < md.getColumnCount() + 1; ix++) {
      if (fieldName.equals(md.getColumnName(ix))) {
        return md.getColumnType(ix);
      }
    }
    throw new SQLException("Field name " + fieldName + " not found in table " + md.getTableName(1));
  }

  private static ResultSetMetaData getMetaDataForTable(Connection conn, String tableName)
      throws SQLException, JSchException {
    String sql =
        "SELECT * FROM " + tableName + " WHERE 1 = 0"; // fast query to obtain the metadata.
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      try (ResultSet rs = stmt.executeQuery()) {
        ResultSetMetaData result = rs.getMetaData();
        if (result != null) {
          return result;
        }
      }
    }
    throw new SQLException("Obtaining ResultSetMetaData for table " + tableName + " failed");
  }

  /**
   * return a non-empty list of EAN (deliverypointid) for this bundle.
   *
   * @param bundle_id
   * @return non-empty list of EANs
   * @throws Exception on DBMS error or no EAN found.
   */
  public static List<String> getEansForBundle(long bundle_id) throws Exception {
    // Find EAN's for user. Do not search EAN's through invoice|order route as
    // invoices could be missing.
    String sql =
        "SELECT mfv.string_value FROM dunning_bundle db "
            + //
            "INNER JOIN base_user bu ON user_name = left(db.external_id, position('~' in db.external_id)-1) AND bu.deleted=0 "
            + //
            "INNER JOIN purchase_order po ON bu.id = po.user_id "
            + //
            "INNER JOIN order_meta_field_map omfm ON omfm.order_id=po.id "
            + //
            "INNER JOIN meta_field_value mfv ON mfv.id=omfm.meta_field_value_id "
            + //
            "INNER JOIN meta_field_name mfn on mfn.id = mfv.meta_field_name_id and mfn.name='ean' "
            + //
            "WHERE db.id=? "
            + //
            "GROUP BY mfv.string_value";

    List<String> results = new ArrayList<>();
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, bundle_id);
        try (ResultSet rs = stmt.executeQuery()) {
          while (rs.next()) {
            String deliverypointid = rs.getString(1);
            results.add(deliverypointid);
          }
          if (results.isEmpty()) {
            throw new SQLException(
                "Obtaining delivery point id's for bundle " + bundle_id + " failed");
          }
          return results;
        }
      }
    }
  }

  public static void setEdielServiceValidTo(String deliveryPointId, LocalDate effectiveDate)
      throws SQLException, JSchException {
    // Only update the last record, let the database do the difficult stuff
    String sql =
        "UPDATE service SET valid_to=? "
            + //
            "WHERE id IN ("
            + //
            "      SELECT id FROM service WHERE deliverypointid = ? ORDER BY valid_to DESC LIMIT 1"
            + //
            ")";
    try (Connection conn = new DBConnector().getEdielConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setDate(1, new Date(effectiveDate.toDate().getTime()));
        stmt.setString(2, deliveryPointId);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException(
              "Update dunning account.last_processed_date failed, hitcount=" + count);
        }
      }
    }
  }

  public static Long getServiceId(String deliveryPoint) throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getEdielConnection()) {
      return getServiceId(conn, deliveryPoint);
    }
  }

  private static Long getServiceId(Connection conn, String deliveryPoint) throws SQLException {
    String sql = "select id from service serv where serv.deliverypointid = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      stmt.setString(1, deliveryPoint);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return DBUtility.asLong(rs.getObject(1));
        }
      }
    }

    return null;
  }

  public static List<Map<String, Object>> getBilledBalancePeriods(long settlementId)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBalanceConnection()) {
      return getBilledBalancePeriods(conn, settlementId);
    }
  }

  private static List<Map<String, Object>> getBilledBalancePeriods(
      Connection conn, long settlementId) throws SQLException {
    String sql = "select start_date, end_date from billed_period where settlement = ?";
    List<Map<String, Object>> result = new ArrayList<>();

    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      stmt.setLong(1, settlementId);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {

          Map<String, Object> record = new HashMap<>();
          record.put("start_date", rs.getObject(1));
          record.put("end_date", rs.getObject(2));

          result.add(record);
        }
      }
    }

    return result;
  }

  public static List<Map<String, Object>> getUnbilledBalancePeriods(long settlementId)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBalanceConnection()) {
      return getUnbilledBalancePeriods(conn, settlementId);
    }
  }

  private static List<Map<String, Object>> getUnbilledBalancePeriods(
      Connection conn, long settlementId) throws SQLException {
    String sql = "select start_date, end_date, estimated from unbilled_period where settlement = ?";
    List<Map<String, Object>> result = new ArrayList<>();

    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      stmt.setLong(1, settlementId);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {

          Map<String, Object> record = new HashMap<>();
          record.put("start_date", rs.getObject(1));
          record.put("end_date", rs.getObject(2));
          record.put("estimated", rs.getObject(3));

          result.add(record);
        }
      }
    }

    return result;
  }

  private static String getCustomerMetaFieldSql(String metaFieldName, String alias) {
    // @formatter:off
    return String.format(
        ""
            + "left outer join ( "
            + "  select boolean_value, integer_value, string_value, customer_id from meta_field_value mfv  "
            + "  inner join meta_field_name mfn on mfn.id = mfv.meta_field_name_id and mfn.name='%s' "
            + "  inner join customer_meta_field_map cmfm on cmfm.meta_field_value_id=mfv.id  "
            + ") %s on %s.customer_id = cust.id ",
        metaFieldName, alias, alias);
    // @formatter:off
  }

  public static boolean getCustomerBundlingActive(String billingId)
      throws SQLException, JSchException {
    boolean result = false; // default no
    try (Connection conn = new DBConnector().getBillingConnection()) {
      // @formatter:off
      String sql =
          "select boolean_value from meta_field_value mfv "
              + "inner join meta_field_name mfn on mfn.id = mfv.meta_field_name_id and mfn.name='BundleOnAccount' "
              + "inner join customer_meta_field_map cmfm on cmfm.meta_field_value_id=mfv.id "
              + "inner join customer c on c.id=cmfm.customer_id "
              + "inner join base_user bu on bu.id = c.user_id and bu.user_name=?";
      // @formatter:on
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, billingId);
        try (ResultSet rs = stmt.executeQuery()) {
          while (rs.next()) {
            result = rs.getBoolean(1);
          }
        }
      }
    }
    return result;
  }

  public static BigDecimal getBalanceReversedAmount(long settlementId)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBalanceConnection()) {
      return getBalanceReversedAmount(conn, settlementId);
    }
  }

  public static BigDecimal getBalanceTotalAmount(long settlementId)
      throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getBalanceConnection()) {
      return getBalanceTotalAmount(conn, settlementId);
    }
  }

  private static BigDecimal getBalanceTotalAmount(Connection conn, long settlementId)
      throws SQLException {
    String sql = "select sum (amount) from billed_revenue where settlement = ? ";

    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      stmt.setLong(1, settlementId);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getBigDecimal(1);
        }
      }
    }
    return BigDecimal.ZERO;
  }

  private static BigDecimal getBalanceReversedAmount(Connection conn, long settlementId)
      throws SQLException {
    String sql = "select sum (amount) from billed_revenue where settlement = ? and reverse = true";

    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
      stmt.setLong(1, settlementId);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getBigDecimal(1);
        }
      }
    }

    return BigDecimal.ZERO;
  }

  public static void setPreferenceType(String preference, String value) throws Exception {
    String sql =
        ""
            +
            // @formatter:off
            "update preference_type "
            + "set def_value = ? "
            + "where id = (select foreign_id from international_description ides "
            + "where table_id = 50 "
            + "and psudo_column = 'description' "
            + "and language_id = 1 "
            + "and content = ?) ";
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, value);
        stmt.setString(2, preference);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException("Update preference failed, hitcount = " + count);
        }
      }
    }
  }

  public static void setPreference(String preference, String value) throws Exception {
    // @formatter:off
    String sql =
        ""
            + "update preference "
            + "set value = ? "
            + "where type_id = (select foreign_id from international_description ides "
            + "	where table_id = 50 "
            + "	and psudo_column = 'description' "
            + "	and language_id = 1 "
            + "	and content = ?) "
            + "and table_id = 5 "
            + "and foreign_id = 60 ";
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, value);
        stmt.setString(2, preference);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException("Update preference failed, hitcount = " + count);
        }
      }
    }
  }

  public static void addExportInvoiceRow(long invoiceId) throws Exception {
    // @formatter:off
    String sql = "insert into export_invoice_queue(id, invoice_id, queued_at) values(?,?,?)";
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, -999); // id
        stmt.setLong(2, invoiceId);
        stmt.setDate(3, new Date(LocalDate.now().toDate().getTime()));
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException("Insert failed, hitcount = " + count);
        }
      }
    }
  }

  public static int countTraceRecords(String audit, String tableName) throws Exception {
    // @formatter:off
    String sql = "select count(*) from nova_trace where operation=? and entity_name=?";
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, audit);
        stmt.setString(2, tableName);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) return DBUtility.asInteger(rs.getObject(1));
          return -1;
        }
      }
    }
  }

  public static Long getSettlementField(
      String fieldName, String ean_or_contractline, Date startDate, Integer status)
      throws Exception {
    // @formatter:off
    // If there are more settlements with the same key (Yes, it happens in old
    // databases), take the newest one
    String sql =
        "select "
            + fieldName
            + " from settlement "
            + "where (contract_line_id=? or consumption_key=?) and start_date=? and status=? "
            + "order by id desc";
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, ean_or_contractline);
        stmt.setString(2, ean_or_contractline);
        stmt.setDate(3, startDate);
        stmt.setInt(4, status);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) return DBUtility.asLong(rs.getObject(1));
          return null;
        }
      }
    }
  }

  public static Long getInvoiceField(String fieldName, String publicNumber) throws Exception {
    // @formatter:off
    String sql = "select " + fieldName + " from invoice where public_number=?";
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, publicNumber);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) return DBUtility.asLong(rs.getObject(1));
          return null;
        }
      }
    }
  }

  public static List<Long> getInvoiceOrdersField(String fieldName, String publicNumber)
      throws Exception {
    // @formatter:off
    String sql =
        "select op."
            + fieldName
            + " from invoice inv, order_process op where inv.public_number=? and inv.id = op.invoice_id ";
    // @formatter:on
    List<Long> ll = new ArrayList<>();
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, publicNumber);
        try (ResultSet rs = stmt.executeQuery()) {
          while (rs.next()) {
            ll.add(DBUtility.asLong(rs.getObject(1)));
          }
          return ll;
        }
      }
    }
  }

  public static Long getBaseUserField(String fieldName, String userName) throws Exception {
    // @formatter:off
    String sql = "select " + fieldName + " from base_user where user_name=?";
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, userName);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) return DBUtility.asLong(rs.getObject(1));
          return null;
        }
      }
    }
  }

  public static Long getCustomerField(String fieldName, String userName) throws Exception {
    // @formatter:off
    String sql =
        "select c."
            + fieldName
            + " from customer c "
            + "inner join base_user bu on c.user_id=bu.id "
            + "where user_name=?";
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, userName);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) return DBUtility.asLong(rs.getObject(1));
          return null;
        }
      }
    }
  }

  public static Long getDunningBundleField(String fieldName, String externalId) throws Exception {
    // @formatter:off
    String sql = "select " + fieldName + " from dunning_bundle where external_id=?";
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, externalId);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) return DBUtility.asLong(rs.getObject(1));
          return null;
        }
      }
    }
  }

  public static Long getDunningAccountField(String fieldName, String externalId) throws Exception {
    // @formatter:off
    String sql = "select " + fieldName + " from dunning_account where external_id=?";
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, externalId);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) return DBUtility.asLong(rs.getObject(1));
          return null;
        }
      }
    }
  }

  public static Long getServiceField(String fieldName, String deliveryPoint, Date startDate)
      throws Exception {
    // @formatter:off
    String sql = "select " + fieldName + " from service where deliverypointid=? and valid_from=?";
    // @formatter:on
    try (Connection conn = new DBConnector().getEdielConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, deliveryPoint);
        stmt.setDate(2, startDate);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) return DBUtility.asLong(rs.getObject(1));
          return null;
        }
      }
    }
  }

  public static Long getOrderProcessField(String fieldName, String publicNumber) throws Exception {
    // @formatter:off
    String sql =
        "select "
            + fieldName
            + " from order_process op "
            + "inner join invoice i on i.id=op.invoice_id "
            + "where public_number = ?";
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, publicNumber);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) return DBUtility.asLong(rs.getObject(1));
          return null;
        }
      }
    }
  }

  public static void UpdateMeteringContextForConsumption(
      String deliverypoint, String meteringContext) throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getEdielConnection()) {
      UpdateMeteringContextForConsumption(conn, deliverypoint, meteringContext);
    }
  }

  private static void UpdateMeteringContextForConsumption(
      Connection conn, String deliverypoint, String meteringContext) throws SQLException {
    // @formatter:off
    String sql = "update consumption set  meteringcontext = ? " + "where deliverypointid = ?";
    // @formatter:on

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, meteringContext);
      stmt.setString(2, deliverypoint);
      stmt.executeUpdate();
    }
  }

  private static void resetEnergyCommForDeliveryPoint(Connection conn, String deliverypoint)
      throws SQLException {

    List<String> sqls = new ArrayList<>();

    // @formatter:off
    final String adapter1 =
        " delete from ediel.edieltransaction where envid in ( "
            + "select cast(substring(ref, position ('=' in ref)+1,position (',' in ref) -position ('=' in ref) - 1) as int) "
            + "from (  select distinct externalmessageid as ref "
            + "from kernel.message m  "
            + "left join kernel.processmessages pm on m.messageid = pm.messageid "
            + "left join kernel.process p on pm.processid = p.processid "
            + "where m.gsrn  = ? "
            + "and msgdir = 'IN' and counterpart = 'EdielAdapter' "
            + ") as s "
            + "); ";

    sqls.add(adapter1);

    final String adapter2 =
        "  delete from ediel.msgctrl where envid in ( "
            + "select cast(substring(ref, position ('=' in ref)+1,position (',' in ref) -position ('=' in ref) - 1) as int) "
            + "from (  select distinct externalmessageid as ref "
            + "from kernel.message m "
            + "left join kernel.processmessages pm on m.messageid = pm.messageid "
            + "left join kernel.process p on pm.processid = p.processid "
            + "where m.gsrn  = ? "
            + "and msgdir = 'IN' and counterpart = 'EdielAdapter' "
            + ") as s "
            + "); ";
    sqls.add(adapter2);

    final String adapter3 =
        "delete from ediel.msgctrl where refenvid in ( "
            + "select cast(substring(ref, position ('=' in ref)+1,position (',' in ref) -position ('=' in ref) - 1) as int) "
            + "from (  select distinct externalmessageid as ref "
            + "from kernel.message m "
            + "left join kernel.processmessages pm on m.messageid = pm.messageid "
            + "left join kernel.process p on pm.processid = p.processid "
            + "where m.gsrn  = ? "
            + "and msgdir = 'IN' and counterpart = 'EdielAdapter' "
            + ") as s "
            + " ); ";
    sqls.add(adapter3);

    final String adapter4 =
        " delete from ediel.msgenv where envid in ( "
            + "select cast(substring(ref, position ('=' in ref)+1,position (',' in ref) -position ('=' in ref) - 1) as int) "
            + "from (  select distinct externalmessageid as ref "
            + "from kernel.message m "
            + "left join kernel.processmessages pm on m.messageid = pm.messageid "
            + "left join kernel.process p on pm.processid = p.processid "
            + "where m.gsrn  = ? "
            + "and msgdir = 'IN' and counterpart = 'EdielAdapter' "
            + ") as s "
            + "); ";
    sqls.add(adapter4);

    final String adapter5 =
        "delete from ediel.edieltransaction where kernelmessageid in "
            + " ( select distinct m.messageid "
            + " from kernel.message m "
            + " left join kernel.processmessages pm on m.messageid = pm.messageid "
            + " left join kernel.process p on pm.processid = p.processid "
            + " where m.gsrn  = ? "
            + " and msgdir = 'OUT' and counterpart = 'EdielAdapter' "
            + "); ";

    sqls.add(adapter5);

    final String kernel1 =
        " delete from kernel.deliveryperiodbrpjournal where deliveryperiodid in ( "
            + " select deliveryperiodid from kernel.deliveryperiod where deliverypointid in ( "
            + " select deliverypointid from kernel.deliverypoint acp where gsrn = ?)); ";

    sqls.add(kernel1);

    final String kernel2 =
        "delete from kernel.transactioncrossref where processid in ( "
            + " select processid from kernel.process where deliveryperiodid in ( "
            + " select deliveryperiodid from kernel.deliveryperiod where deliverypointid in ( "
            + "  select deliverypointid from kernel.deliverypoint acp where gsrn = ?)));";

    sqls.add(kernel2);

    final String kernel3 =
        " delete from kernel.processmessages where processid in ( "
            + " select processid from kernel.process where deliveryperiodid in ( "
            + " select deliveryperiodid from kernel.deliveryperiod where deliverypointid in ( "
            + "     select deliverypointid from kernel.deliverypoint acp where gsrn = ?))); ";

    sqls.add(kernel3);

    final String kernel4 =
        "delete from kernel.unblockingmsg where expectedmessageid in ( "
            + " select expectedmessageid from kernel.expectedmessages where processid in ( "
            + " select processid from kernel.process where deliveryperiodid in ( "
            + "        select deliveryperiodid from kernel.deliveryperiod where deliverypointid in ( "
            + "              select deliverypointid from kernel.deliverypoint acp where gsrn = ?)))); ";
    sqls.add(kernel4);

    final String kernel5 =
        "delete from kernel.expectedmessages where processid in ( "
            + " select processid from kernel.process where deliveryperiodid in ( "
            + "  select deliveryperiodid from kernel.deliveryperiod where deliverypointid in ( "
            + "       select deliverypointid from kernel.deliverypoint acp where gsrn = ?))); ";
    sqls.add(kernel5);

    final String kernel6 =
        "delete from kernel.process where deliveryperiodid in ( "
            + "  select deliveryperiodid from kernel.deliveryperiod where deliverypointid in ( "
            + "  select deliverypointid from kernel.deliverypoint acp where gsrn = ?)); ";
    sqls.add(kernel6);

    final String kernel7 =
        "delete from kernel.deliveredcustomer where deliveryperiodid in ( "
            + " select deliveryperiodid from kernel.deliveryperiod where deliverypointid in ( "
            + "    select deliverypointid from kernel.deliverypoint acp where gsrn = ?)); ";
    sqls.add(kernel7);

    final String kernel8 =
        "delete from kernel.consumptiondetail where consumptionid in ( "
            + " select consumptionid from kernel.consumption where deliveryperiodid in ( "
            + "  select deliveryperiodid from kernel.deliveryperiod where deliverypointid in ( "
            + "   select deliverypointid from kernel.deliverypoint acp where gsrn = ?))); ";
    sqls.add(kernel8);

    final String kernel9 =
        "delete from kernel.consumption where deliveryperiodid in ( "
            + " select deliveryperiodid from kernel.deliveryperiod where deliverypointid in ("
            + "  select deliverypointid from kernel.deliverypoint acp where gsrn = ?)); ";
    sqls.add(kernel9);

    final String kernel10 =
        "delete from kernel.consumptiondetail where consumptionid in ("
            + "  select consumptionid from kernel.consumption where deliverypointid in ( "
            + "  select deliverypointid from kernel.deliverypoint acp where gsrn = ?)); ";
    sqls.add(kernel10);

    final String kernel11 =
        "delete from kernel.consumption where deliverypointid in ( "
            + " select deliverypointid from kernel.deliverypoint acp where gsrn = ?);";
    sqls.add(kernel11);

    final String kernel12 =
        "delete from kernel.indexdetail where indexid in ( "
            + "select indexid from kernel.index where deliveryperiodid in ( "
            + " select deliveryperiodid from kernel.deliveryperiod where deliverypointid in ( "
            + "      select deliverypointid from kernel.deliverypoint acp where gsrn = ?))); ";
    sqls.add(kernel12);

    final String kernel13 =
        "delete from kernel.index where deliveryperiodid in ( "
            + "select deliveryperiodid from kernel.deliveryperiod where deliverypointid in ( "
            + " select deliverypointid from kernel.deliverypoint acp where gsrn = ?)); ";
    sqls.add(kernel13);

    final String kernel14 =
        "delete from kernel.deliveryperiod where deliverypointid in ( "
            + "select deliverypointid from kernel.deliverypoint acp where gsrn = ?);";
    sqls.add(kernel14);

    final String kernel15 =
        "delete from kernel.physicalregister where meterid in ( "
            + "select meterid from kernel.meter where deliverypointid in ( "
            + "     select deliverypointid from kernel.physicalconfig where deliverypointid in ( "
            + "            select deliverypointid from kernel.deliverypoint acp where gsrn = ?)));";
    sqls.add(kernel15);

    final String kernel16 =
        "delete from kernel.meter where deliverypointid in ( "
            + " select deliverypointid from kernel.physicalconfig where deliverypointid in ( "
            + "       select deliverypointid from kernel.deliverypoint acp where gsrn = ?)); ";
    sqls.add(kernel16);

    final String kernel17 =
        "delete from kernel.physicalconfig where deliverypointid in ( "
            + " select deliverypointid from kernel.deliverypoint acp where gsrn = ?); ";
    sqls.add(kernel17);

    final String kernel18 =
        "delete from kernel.logicalregister where deliverypointid in ( "
            + "select deliverypointid from kernel.logicalconfig where deliverypointid in ( "
            + "      select deliverypointid from kernel.deliverypoint acp where gsrn = ?)); ";
    sqls.add(kernel18);

    final String kernel19 =
        "delete from kernel.logicalconfig where deliverypointid in ( "
            + "select deliverypointid from kernel.deliverypoint acp where gsrn = ?); ";
    sqls.add(kernel19);

    final String kernel20 =
        "delete from kernel.generalconfig where deliverypointid in ( "
            + "select deliverypointid from kernel.deliverypoint acp where gsrn = ?); ";
    sqls.add(kernel20);

    final String kernel21 =
        "delete from kernel.estimatedetail where estimateid in ( "
            + "select estimateid from kernel.estimate where deliverypointid in ( "
            + "       select deliverypointid from kernel.deliverypoint acp where gsrn = ?)); ";
    sqls.add(kernel21);

    final String kernel22 =
        "delete from kernel.estimate where deliverypointid in ( "
            + " select deliverypointid from kernel.deliverypoint acp where gsrn = ?); ";
    sqls.add(kernel22);

    final String kernel23 = "delete from kernel.deliverypoint acp where gsrn = ?; ";
    sqls.add(kernel23);

    final String kernel24 =
        "delete from kernel.processmessages where messageid in (select messageid from kernel.message where  gsrn = ?); ";
    sqls.add(kernel24);

    final String kernel25 =
        "delete from kernel.expectedmessages where messageid in (select messageid from kernel.message where  gsrn = ?); ";
    sqls.add(kernel25);

    final String kernel26 =
        "delete from kernel.messagemetadata where messageid in (select messageid from kernel.message m where gsrn = ?);  ";
    sqls.add(kernel26);

    final String kernel27 = "update kernel.message set relatedmessageid = null where gsrn = ?; ";
    sqls.add(kernel27);

    final String kernel28 =
        "delete from kernel.messagefeedback where messageid in (select messageid from kernel.message where gsrn  = ?);";
    sqls.add(kernel28);

    final String kernel29 = "delete from kernel.message m where gsrn = ?; ";
    sqls.add(kernel29);

    // @formatter:on
    for (String sql : sqls) {
      // System.out.println(sql);
      try {
        conn.setAutoCommit(false);
        ExecuteEnergycomCleanStatement(conn, sql, deliverypoint);
        conn.commit();
      } catch (Exception e) {
        conn.rollback();
      }
    }
  }

  private static void ExecuteEnergycomCleanStatement(
      Connection conn, String sql, String deliverypoint) throws SQLException {
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, deliverypoint);
      stmt.executeUpdate();
    }
  }

  private static Integer asInteger(Object object) {
    if (object instanceof Integer) return (Integer) object;
    if (object instanceof Long) {
      long value = ((Long) object);
      if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
        throw new IllegalStateException("Cannot convert long value " + value + " to an integer!");
      }
      return (int) value; // we know this is safe
    }
    if (object instanceof Number) return ((Number) object).intValue();
    if (object instanceof String) return Integer.parseInt((String) object);
    if (object == null) return null;

    throw new IllegalStateException(
        String.format(
            "Class %s is not convertable to Integer by DBUtility.asLong(Object)",
            (object == null) ? "null" : object.getClass().getName()));
  }

  /**
   * Will return the object given as a Long instance, doing conversions as needed. This works better
   * than ResultSet.getLong() as it returns a long, which means it cannot handle NULL values well.
   *
   * @param object
   * @return
   */
  private static Long asLong(Object object) {
    if (object == null) return null;
    if (object instanceof Integer) return ((Integer) object).longValue();
    if (object instanceof Long) return (Long) object;
    if (object instanceof Number) return ((Number) object).longValue();
    if (object instanceof String) return Long.parseLong((String) object);

    throw new IllegalStateException(
        String.format(
            "Class %s is not convertable to Long by DBUtility.asLong(Object)",
            (object == null) ? "null" : object.getClass().getName()));
  }

  public static void deleteAllProfiles() throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getEdielConnection()) {
      deleteAllProfiles(conn);
    }
  }

  public static void deleteAllCalculatedMeters() throws SQLException, JSchException {
    try (Connection conn = new DBConnector().getEdielConnection()) {
      deleteAllCalculatedMeters(conn);
    }
  }

  private static void deleteAllCalculatedMeters(Connection conn) throws SQLException {

    String sql = "Delete from calculated_meter";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.executeUpdate();
    }
  }

  private static void deleteAllProfiles(Connection conn) throws SQLException {

    String sql = "Delete from load_profile";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.executeUpdate();
    }
  }

  public static void enableDunningForCrmId(String crmCustomerId) throws Exception {
    // @formatter:off
    String sql = "" + "update dunning_account " + "set bre_id = 14 " + "where external_id = ? ";
    logger.debug("STEP:");
    logger.debug(" - ACTION: SQL_UPDATE " + sql);
    // @formatter:on
    try (Connection conn = new DBConnector().getBillingConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, crmCustomerId);
        int count = stmt.executeUpdate();
        if (count != 1) {
          throw new SQLException("Update preference failed, hitcount = " + count);
        }
      }
    }
  }

  public static void switchSuiteCrmStatusExternal(SwitchState statusExternal, int crmCustomerId)
      throws Exception {
    // @formatter:off
    String sql =
        "" + "update accounts " + "set status_external = ? " + "where account_number_c = ? ";
    logger.debug("STEP:");
    logger.debug(" - ACTION: SQL_UPDATE " + sql);

    // @formatter:on
    try (Connection conn = new DBConnector().getSuiteCRMConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, statusExternal.getState());
        stmt.setInt(2, crmCustomerId);
        int count = stmt.executeUpdate();
        if (count != 1) {
          logger.error(" - RESULT: SQL_UPDATE hitcount " + count + " != 1");
          throw new SQLException("Update preference failed, hitcount = " + count);
        }
      }
    }
  }
}
