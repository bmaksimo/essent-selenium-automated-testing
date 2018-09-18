package com.essent.testing.restassured.create_b2b_contract.helper;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.restassured.create_b2b_contract.constants.ConstantsContractB2B;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class UploadB2BContractPrices {
	
	private static final Logger logger = Logger.getLogger(UploadB2BContractPrices.class);

	private static final String ftpDomain = ConfigProvider.getProperty(ConfigKey.ENVIRONMENT) + "-sftp.nova.essent.be";
	
	private static final int ftpPort = 22;
	private static final String remoteFileLocation = "/home/ESSENT/sa_sftpcrm_smx/data/generic";
	private static final String privateKeyPassword = ConfigProvider.getProperty(ConfigKey.SSH_PASSPHRASE);
	private static final String locationOfPrivateKey = ConfigProvider.getProperty(ConfigKey.SSH_KEYPATH);
	private static final String ftpUserName = ConfigProvider.getProperty(ConfigKey.SSH_USER);
    
    //String ftpUserName = "dj.kovacevic";
	//String locationOfPrivateKey = "C:\\Users\\dj.kovacevic\\.ssh\\id_rsa";
	//String privateKeyPassword = "";

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
			File directory = new File(ConstantsContractB2B.PATH_TO_PRICES);

			File[] files = directory.listFiles();
			
			// for each file in the directory
			for (int i = 0; i < files.length; i++) {
				String localfilelocation = ConstantsContractB2B.PATH_TO_PRICES + "//" + files[i].getName();
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
