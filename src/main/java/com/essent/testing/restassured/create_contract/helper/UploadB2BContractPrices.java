package com.essent.testing.restassured.create_contract.helper;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.restassured.create_contract.constants.ContractConstants;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class UploadB2BContractPrices {

	private static final Logger logger = Logger.getLogger(UploadB2BContractPrices.class);

	private static final String ftpDomain = ConfigProvider.getProperty(ConfigKey.ENVIRONMENT) + "-sftp.nova.essent.be";

	// See how below field values are implemented in jBilling gherklin tests from JBilling team (also for jenkins on REG02 environment)
	private static final int ftpPort = 22;
	private static final String remoteFileLocation = "/home/ESSENT/sa_sftpcrm_smx/data/generic";
	private static final String privateKeyPassword = ConfigProvider.getProperty(ConfigKey.SSH_PASSPHRASE); // example: can be empty string also
	private static final String locationOfPrivateKey = ConfigProvider.getProperty(ConfigKey.SSH_KEYPATH);  // example: ..\id_rsa
	private static final String ftpUserName = ConfigProvider.getProperty(ConfigKey.SSH_USER);              // example: dj.kovacevic

	// Method upload prices to specific location on sftp, which them will be picked by CRM and imported in CRM
	public void uploadB2BPrices() {

		Session session = null;
		Channel channel = null;

		try {
			JSch ssh = new JSch();
			ssh.addIdentity(locationOfPrivateKey, privateKeyPassword);
			session = ssh.getSession(ftpUserName, ftpDomain, ftpPort);
			session.setConfig("StrictHostKeyChecking", "no"); // auto accept secure host
			session.connect();

			channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftp = (ChannelSftp) channel;
			File directory = new File(ContractConstants.PATH_TO_PRICES);

			File[] files = directory.listFiles();

			// for each file in the directory
			for (int i = 0; i < files.length; i++) {
				String localfilelocation = ContractConstants.PATH_TO_PRICES + "//" + files[i].getName();
				sftp.put(localfilelocation, remoteFileLocation);
			}

			int sleepTime = 150000;

			// Waiting for "+sleepTime+" ms so that uploaded prices are picked up
			Thread.sleep(sleepTime);

		} catch (JSchException e) {
			logger.error("JSchException", e);
			Assert.fail("JSchException : " + e.getMessage());
		} catch (SftpException e) {
			logger.error("SftpException", e);
			Assert.fail("SftpException: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Exception", e);
			Assert.fail("Exception: " + e.getMessage());
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
	}

}
