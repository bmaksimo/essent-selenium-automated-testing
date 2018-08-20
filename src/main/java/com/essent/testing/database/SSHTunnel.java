package com.essent.testing.database;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

public class SSHTunnel {

    private  final static Logger logger = Logger.getLogger(SSHTunnel.class);

    protected int port = 5432;
    protected Session session;
    private final ConfigKey useTunnelPropertyKey;

    public SSHTunnel(ConfigKey useTunnelPropertyKey) {
    	this.useTunnelPropertyKey = useTunnelPropertyKey;
    }

    protected void pushHost(String newHostName) {
    	// Default do nothing.
    }

    protected void popHost() {
    	// default do nothing.
    }

    protected boolean useTunnel() {
        String useTunnel = ConfigProvider.getProperty(useTunnelPropertyKey).trim();
        return "true".equalsIgnoreCase(useTunnel) || "yes".equalsIgnoreCase(useTunnel);
    }

    /**
     * Warning: A tunnel proved to be very slow on the REG02 environment, restoring a dump
     * took a minute against <5s without tunnel. THe code is still here, but it is very tricky.
     *
     * @param sshHostname
     * @param sshRemoteport
     * @param sshDbHostname
     * @throws JSchException
     */
    public void makeTunnel(String sshHostname, int sshRemoteport, String sshDbHostname) throws JSchException {
        if (useTunnel()) {
            // real tunnel
            if (StringUtils.isBlank(sshDbHostname)) {
                sshDbHostname = "localhost";
            }
            JSch.setConfig("StrictHostKeyChecking", "no");

            final String path = ConfigProvider.getProperty(ConfigKey.SSH_KEYPATH);
            final String sshUser = ConfigProvider.getProperty(ConfigKey.SSH_USER);
            final int localPort = findFreePort();

            int nrFailedTries = 0;
            boolean tunnelCreated = false;
            while (!tunnelCreated) {
                try {
                    // We create a new factory for each try, just to be on the
                    // safe side.
                    JSch jsch = new JSch();
                    jsch.addIdentity(path);

                    session = jsch.getSession(sshUser, sshHostname);
                    session.connect(1000000);

                    this.port = session.setPortForwardingL(localPort, sshDbHostname, sshRemoteport);
                    // got here, it worked :)
                    logger.info("Tunnel created: dbhost = " + sshDbHostname + ", localport = " + localPort
                            + ", remoteport = " + sshRemoteport);
                    tunnelCreated = true;
                    // We should now connect to localhost instead of the given sshDbHostname in PSQLUtility
                    pushHost("localhost");
                } catch (JSchException e) {
                    try {
                        if (session != null) {
                            session.disconnect();
                            session = null;
                        }
                    } catch (Exception ignore) {
                        // Ignore.
                    }

                    nrFailedTries++;
                    if (nrFailedTries > 3) {
                        // We are done with it, too many failures
                        throw e;
                    }

                    // Wait a bit before trying again. Most likely cause is port
                    // not yet free.
                    try {
                        // We use a back-off algorithm with 10, 20 or 30
                        // seconds.
                        Thread.sleep(nrFailedTries * 10 * 1000);
                    } catch (InterruptedException ignore) {
                        // ignore and continue with next try
                    }
                }
            }
        }
    }

    /**
     * Set up a tunnel. If setting up fails, we try a number of times.
     *
     * @param sshHostnameConfigKey
     * @param sshRemoteportConfigKey
     * @param sshDbHostnameConfigKey
     * @throws JSchException
     */
    protected void makeTunnel(ConfigKey sshHostnameConfigKey, ConfigKey sshRemoteportConfigKey,
            ConfigKey sshDbHostnameConfigKey) throws JSchException {
        makeTunnel(ConfigProvider.getProperty(sshHostnameConfigKey),
                Integer.parseInt(ConfigProvider.getProperty(sshRemoteportConfigKey)),
                ConfigProvider.getProperty(sshDbHostnameConfigKey));
    }

    protected void makeBillingTunnel() throws JSchException {
        this.makeTunnel(ConfigKey.SSH_BILLING_HOSTNAME, ConfigKey.SSH_BILLING_REMOTE_PORT, ConfigKey.BILLING_DB_HOST); }

    public void cleanUpTunnel() throws JSchException {
        if (useTunnel()) {
            session.delPortForwardingL(port);
            session.disconnect();
            session = null;
            popHost();
        }
    }

    /**
     * Returns a free port number on localhost.
     * <p>
     * Heavily inspired from org.eclipse.jdt.launching.SocketUtil (to avoid a
     * dependency to JDT just because of this). Slightly improved with close()
     * missing in JDT. And throws exception instead of returning -1.
     *
     * @return a free port number on localhost
     * @throws IllegalStateException
     *             if unable to find a free port
     */
    private static int findFreePort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            try {
                socket.close();
            } catch (IOException e) {
                // Ignore IOException on close()
            }
            return port;
        } catch (IOException e) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        throw new IllegalStateException("Could not find a free TCP/IP port to create a tunnel on");
    }

    public int getLocalPort() {
        return port;
    }

    public Session getSession() {
        return session;
    }
}
