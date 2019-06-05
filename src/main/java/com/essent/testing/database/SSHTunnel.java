package com.essent.testing.database;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import stepdefinitions.dwp.tables.plus.SwitchState;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class SSHTunnel {

    private final static Logger logger = Logger.getLogger(SSHTunnel.class);

    private Integer localPort = null;

    private Session session;

    private final ConfigKey useTunnelPropertyKey;

    private Map<Database, Consumer<Void>> makeTunnel = new HashMap<>();

    SSHTunnel(ConfigKey useTunnelPropertyKey) {
    	this.useTunnelPropertyKey = useTunnelPropertyKey;
        makeTunnel.put(Database.SuiteDb, (any) -> makeSuiteCRMTunnel());
        makeTunnel.put(Database.JBilling, (any) -> makeBillingTunnel());
    }

    private void pushHost(String newHostName) {
    	// Default do nothing.
    }

    private void popHost() {
    	// default do nothing.
    }

    private boolean useTunnel() {
        String useTunnel = ConfigProvider.getProperty(useTunnelPropertyKey).trim();
        return "true".equalsIgnoreCase(useTunnel) || "yes".equalsIgnoreCase(useTunnel);
    }

    int getLocalPort() {
        if(localPort == null) {
            throw new CucumberException("local port has not been initialized.");
        }
        return localPort;
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
    private void makeTunnel(String sshHostname, int sshRemoteport, String sshDbHostname) throws JSchException {
        if (useTunnel()) {
            // real tunnel
            logger.debug("STEP:");
            logger.debug(" - ACTION: MAKE_SSH_TUNNEL");
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
                    this.localPort = session.setPortForwardingL(localPort, sshDbHostname, sshRemoteport);
                    // got here, it worked :)
                    logger.debug("Tunnel created: dbhost = " + sshDbHostname + ", localport = " + localPort
                            + ", remoteport = " + sshRemoteport);
                    tunnelCreated = true;
                    // We should now connect to localhost instead of the given sshDbHostname in PSQLUtility
                    pushHost("localhost");
                    logger.debug(String.format(" - RESULT: Tunnel to %s, ssh_keypath: %s, userid: %s made successfully.", sshDbHostname, path, sshUser));
                } catch (JSchException e) {
                    logger.error(String.format(" - RESULT: Making tunnel to %s failed", sshDbHostname));
                    try {
                        if (session != null) {
                            logger.debug(" - ACTION: DESTROY_SESSION");
                            session.disconnect();
                            logger.debug(" - RESULT: Session has been destroyed.");
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

                    // Wait a bit before trying again. Most likely cause is remotePort
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
    private void makeTunnel(ConfigKey sshHostnameConfigKey, ConfigKey sshRemoteportConfigKey,
                                   ConfigKey sshDbHostnameConfigKey) throws JSchException {
        makeTunnel(ConfigProvider.getProperty(sshHostnameConfigKey),
                Integer.parseInt(ConfigProvider.getProperty(sshRemoteportConfigKey)),
                ConfigProvider.getProperty(sshDbHostnameConfigKey));
    }


    private void makeBillingTunnel()  {
        try {
            this.makeTunnel(ConfigKey.SSH_BILLING_HOSTNAME, ConfigKey.SSH_BILLING_REMOTE_PORT, ConfigKey.BILLING_DB_HOST);
        } catch (JSchException e) {
            throw new CucumberException("Opening jBilling DB tunnel failed.", e);
        }
    }

    private void makeSuiteCRMTunnel()  {
        try{
            makeTunnel(ConfigKey.SSH_SUITE_HOSTNAME,
                ConfigKey.SSH_SUITE_REMOTE_PORT,
                ConfigKey.SUITE_DB_HOST);
        } catch (JSchException e) {
            throw new CucumberException("Opening SuiteCRM DB tunnel failed.", e);
        }
    }

    void switchSshTunnel(Database database, SwitchState state) throws JSchException {
        if(state.isOn()) {
            Consumer<Void> tunnelMaker = makeTunnel.get(database);
            tunnelMaker.accept(null);
        } else if(!state.isUndefined()) {
            cleanUpTunnel();
        }
    }

    private void cleanUpTunnel() throws JSchException {
        if (useTunnel() && session != null) {
            logger.debug("STEP:");
            logger.debug(" - ACTION: DELETE_LOCAL_PORT");
            session.delPortForwardingL(localPort);
            logger.debug(" - RESULT: Port " + localPort + " forwarding has been deleted.");
            logger.debug(" - ACTION: DESTROY_SESSION");
            session.disconnect();
            logger.debug(" - RESULT: Session with host " + session.getHost() + " has been destroyed.");
            session = null;
            popHost();
        }
    }

    /**
     * Returns a free remotePort number on localhost.
     * <p>
     * Heavily inspired from org.eclipse.jdt.launching.SocketUtil (to avoid a
     * dependency to JDT just because of this). Slightly improved with close()
     * missing in JDT. And throws exception instead of returning -1.
     *
     * @return a free remotePort number on localhost
     * @throws IllegalStateException
     *             if unable to find a free remotePort
     */
    private static int findFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            try {
                socket.close();
            } catch (IOException e) {
                // Ignore IOException on close()
            }
            return port;
        } catch (IOException ignored) {
        }
        throw new IllegalStateException("Could not find a free TCP/IP remotePort to create a tunnel on");
    }
}
