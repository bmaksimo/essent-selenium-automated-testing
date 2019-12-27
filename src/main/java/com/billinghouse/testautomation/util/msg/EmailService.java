package com.billinghouse.testautomation.util.msg;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailService {

  public static void main(String[] args) {
    new EmailService().createTestReport(null);
  }

  private static final Logger logger = Logger.getLogger(EmailService.class);

  private static final String PATH = "/email/";
  private static final String NUAT5019_TEMPLATE = "nuat5019_template.tpl";

  public boolean createTestReport(Map<String, String> input) {
    String messageBody = getMessageFromTemplate(input);
    return sendEmailNow(getJavaMailSender(), messageBody);
  }

  private JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(ConfigProvider.getProperty(ConfigKey.ESSENT_SMTP_HOST));
    int port = Integer.parseInt(ConfigProvider.getProperty(ConfigKey.ESSENT_SMTP_PORT));
    mailSender.setPort(port);

    return mailSender;
  }

  private String getMessageFromTemplate(Map<String, String> data) {
    String messageTemplatePath = ResourceUtil.toPath(PATH + NUAT5019_TEMPLATE);
    File messageTemplateFile = new File(messageTemplatePath);
    try {
      String messageBody =
          FileUtils.readFileToString(messageTemplateFile, Charset.defaultCharset());
      StrSubstitutor substitutor = new StrSubstitutor(data);
      messageBody = substitutor.replace(messageBody);

      return messageBody;
    } catch (IOException e) {
      logger.error("Something went wrong while creating the email message body.");
    }

    return null;
  }

  private boolean sendEmailNow(JavaMailSender emailSender, String messageBody) {
    try {
      if (null != emailSender) {
        MimeMessage message = getEmailMessage(messageBody, emailSender);
        logger.debug("Sending email report to SME...");
        emailSender.send(message);
        logger.debug("Done.");

        return true;
      }
      logger.warn(
          "Could not find the email sender. Please check if email properties are properly set.");
      return false;
    } catch (MessagingException e) {
      logger.error(
          "Something went wrong while sending an email. Please check if your email server is up and running.");
      return false;
    }
  }

  private MimeMessage getEmailMessage(String messageBody, JavaMailSender emailSender)
      throws MessagingException {
    MimeMessage mimeMailMessage = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
    helper.setFrom(ConfigProvider.getProperty(ConfigKey.ESSENT_EMAIL_FROM));
    helper.setTo(ConfigProvider.getProperty(ConfigKey.ESSENT_EMAIL_TO).split(","));
    helper.setSubject("NUAT-5019 Scenario Execution");
    helper.setText(messageBody);

    return mimeMailMessage;
  }
}
