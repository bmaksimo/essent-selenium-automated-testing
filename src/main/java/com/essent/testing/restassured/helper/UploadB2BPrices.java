package com.essent.testing.restassured.helper;

import java.io.File;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.restassured.constants.Constants;
import com.jcraft.jsch.*;

public class UploadB2BPrices {

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
			File directory = new File(Constants.PATH_TO_PRICES);

			File[] files = directory.listFiles();
			
			// for each file in the directory
			for (int i = 0; i < files.length; i++) {
				String localfilelocation = Constants.PATH_TO_PRICES + "//" + files[i].getName();
				sftp.put(localfilelocation, remoteFileLocation);
			}

			int sleepTime = 150000;
			
			// Waiting for "+sleepTime+" ms so that uploaded prices are picked up
			Thread.sleep(sleepTime);
			
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
