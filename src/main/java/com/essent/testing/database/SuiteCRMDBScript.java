package com.essent.testing.database;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.*;


public class SuiteCRMDBScript extends SSHTunnel {

    public SuiteCRMDBScript() {
        super(ConfigKey.TUNNEL_SUITE);
    }

    protected DataSource datasource;

    private static final Logger LOG =
        LoggerFactory.getLogger(SuiteCRMDBScript.class);

    protected void createDataSource() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();

        try {
            @SuppressWarnings("unchecked")
            Class<? extends Driver> driverClass =
                (Class<? extends Driver>) Class.forName("com.mysql.jdbc.Driver");
            ds.setDriverClass(driverClass);

        } catch (Exception e) {
            LOG.error("Error loading driver class", e);
        }

        String db = ConfigProvider.getProperty(ConfigKey.SUITE_DB);
        // Connection settings
        ds.setUrl("jdbc:mysql://localhost:" + getLocalPort() + "/" + db);
        ds.setUsername(ConfigProvider.getProperty(ConfigKey.SUITE_DB_USER));
        ds.setPassword(ConfigProvider.getProperty(ConfigKey.SUITE_DB_PASSWORD));
        this.datasource = ds;
    }

    public boolean enableExternalStatusForAccount(int crmCustomerId) {
        try {
            makeSuiteCRMTunnel();
            this.createDataSource();
            String sql = "" + "update accounts " + "set status_external = 1 " + "where account_number_c = ? ";
            boolean result = executeUpdate(sql, crmCustomerId);
            datasource = null;
            return result;
        } catch (Exception e) {
            LOG.info("Error executing suite script ", e);
        }
        return false;
    }

    public boolean executeUpdate(String update, int updateValue) throws SQLException {
        Connection conn = datasource.getConnection();
        PreparedStatement statement = conn.prepareStatement(update);
        statement.setString(1, "" + updateValue);
        int count = statement.executeUpdate();
        if (count != 1) {
            throw new SQLException("Update preference failed, hitcount = " + count);
        } else {
            return true;
        }
    }
}
