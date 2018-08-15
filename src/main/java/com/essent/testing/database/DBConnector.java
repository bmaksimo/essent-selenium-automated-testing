package com.essent.testing.database;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.jcraft.jsch.JSchException;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector extends SSHTunnel {

	public DBConnector() {
		super(ConfigKey.TUNNEL_BILLING_DB);
	}

	public Connection getBillingConnection() throws JSchException {
		final String db = ConfigProvider.getProperty(ConfigKey.BILLING_DB);
        final String password = ConfigProvider.getProperty(ConfigKey.BILLING_DB_PASSWORD);
        final String userId = ConfigProvider.getProperty(ConfigKey.BILLING_DB_USER);

		return createConnection(db, userId, password);
	}

	public Connection getEdielConnection() throws JSchException {
		final String db = ConfigProvider.getProperty(ConfigKey.EDIEL_DB);
		final String password = ConfigProvider.getProperty(ConfigKey.EDIEL_DB_PASSWORD);
		final String userId = ConfigProvider.getProperty(ConfigKey.EDIEL_DB_USER);

		return createConnection(db, userId, password);
	}

    public Connection getBalanceConnection() throws JSchException {
        final String db = ConfigProvider.getProperty(ConfigKey.ENERGY_BALANCE_DB_DB);
        final String password = ConfigProvider.getProperty(ConfigKey.ENERGY_BALANCE_DB_PASSWORD);
        final String userId = ConfigProvider.getProperty(ConfigKey.ENERGY_BALANCE_DB_USER);

        return createConnection(db, userId, password);
    }

    private Connection createConnection(String db, String userId, String password) throws JSchException {
        makeBillingTunnel();
        // Compose jdbc connection string
        final String jdbcUrl = "jdbc:postgresql:" + "//localhost:" + getLocalPort() + "/" + db;
        Connection c;
        try {
            Class.forName("org.postgresql.Driver");
            Connection master = DriverManager.getConnection(jdbcUrl, userId, password);

            // Create a proxy around the connection, so on close we can also close the tunnel (if it exists)
            c = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
                    new Class[] { Connection.class }, (proxy, method, args) -> {
                        try {
                            return method.invoke(master, args);
                        } finally {
                            if (method.getName().equalsIgnoreCase("close")) {
                                cleanUpTunnel();
                            }
                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException("Connection to " + jdbcUrl + " failed", e);
        }
        return c;

    }
}
