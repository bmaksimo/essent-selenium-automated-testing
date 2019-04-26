package com.billinghouse.test_automation.util.ssh;

import com.billinghouse.exception.ExtendedCucumberException;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.jcraft.jsch.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.*;

public class JSchUtil {
    private static final Logger logger = Logger.getLogger(JSchUtil.class);
    private static final JSchUtil instance = new JSchUtil();

    public static final JSchUtil get() {
        return instance;
    }

    private Session session;
    private JSch jSchell;
    private File localFile;

    private Session getSession(String username, String host) throws JSchException {
        Session session;
        final String path = ConfigProvider.getProperty(ConfigKey.SSH_KEYPATH);
        jSchell.addIdentity(path);
        logger.info("STEP:");
        logger.info(" - ACTION: SFTP_UPLOAD");
        session = jSchell.getSession(username, host);
        session.connect(1000000);
        logger.debug("-ACTION: JSch session has been established with " + host);
        return session;
    }

    private JSchUtil() {
        this.jSchell = new JSch();
        JSch.setConfig("StrictHostKeyChecking", "no");
    }

    public void sftpPut(final String sftpHost, final String remoteDir, final String localFilePath) throws JSchException {
        this.session = getSession(ConfigProvider.getProperty(ConfigKey.SSH_USER),
            sftpHost);
        try {
            channelSftpPut(remoteDir, localFilePath);
            this.session.disconnect();
        } catch (JSchException e) {
            this.session.disconnect();
            this.session = null;
            throw new ExtendedCucumberException(e);
        }
    }

    private void channelSftpPut(final String remoteDir, final String localFilePath) throws JSchException {
        Channel chan = this.session.openChannel("sftp");
        logger.debug(" - SFTP_UPLOAD: Sftp Channel established.");
        ChannelSftp chanSftp = (ChannelSftp) chan;
        chanSftp.connect();
        logger.debug(" - SFTP_UPLOAD: Sftp Channel connected.");
        try {
            chanSftp.cd(remoteDir);
            SftpProgressMonitor callback = createSftpProgressMonitor();
            logger.debug(" - SFTP_UPLOAD: Switched to sftp directory: " + chanSftp.pwd());
            try (InputStream is = createInputStream(localFilePath)) {
                chanSftp.put(is, localFile.getName(), callback);
            }
            chanSftp.exit();
            chanSftp.disconnect();

        } catch (SftpException | IOException e) {
            logger.error(" - SFTP_UPLOAD: sftp transfer aborted;");
            chanSftp.disconnect();
            this.session.disconnect();
            throw new ExtendedCucumberException(e);
        }
    }

    private InputStream createInputStream(String localFilePath) throws FileNotFoundException {
        this.localFile = new File(localFilePath);
        if (!localFile.exists() || localFile.isDirectory()) {
            throw new ExtendedCucumberException("Cannot sftp transfer file at path " + localFilePath + ", eiither it doesn't exist or it is directory");
        }
        return new FileInputStream(localFile);
    }

    private SftpProgressMonitor createSftpProgressMonitor() {
        return new SftpProgressMonitor() {
            private String dest;

            @Override
            public void init(int i, String src, String dest, long l) {
                this.dest = dest;
            }

            @Override
            public boolean count(long l) {
                return false;
            }

            @Override
            public void end() {
                logger.info(String.format(" - SFTP_UPLOAD: Complete. Local file has been uploaded to uploaded to %s ", dest));
                FileUtils.deleteQuietly(localFile);
            }
        };
    }

}
